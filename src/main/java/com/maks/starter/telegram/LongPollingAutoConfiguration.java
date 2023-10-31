package com.maks.starter.telegram;

import com.maks.telegram.bot.DefaultTelegramLongPollingBot;
import com.maks.telegram.command.Command;
import com.maks.telegram.command.factory.CommandFactory;
import com.maks.telegram.command.factory.DefaultCommandFactory;
import com.maks.telegram.command.factory.DefaultParamsFactory;
import com.maks.telegram.command.factory.ParamsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;


@Configuration
@EnableConfigurationProperties(BotProperties.class)
@RequiredArgsConstructor
public class LongPollingAutoConfiguration {
    private final BotProperties botProperties;

    @Bean
    @ConditionalOnMissingBean
    public CommandFactory commandFactory(@Autowired List<Command> commandList) {
        return new DefaultCommandFactory(commandList);
    }

    @Bean
    @ConditionalOnMissingBean
    public ParamsFactory paramsFactory() {
        return new DefaultParamsFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "telegram.bot", name = {"username", "token"})
    public LongPollingBot telegramBot(@Autowired CommandFactory commandFactory, @Autowired ParamsFactory paramsFactory) {
        return new DefaultTelegramLongPollingBot(botProperties.getUsername(), botProperties.getToken(),
                commandFactory, paramsFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({LongPollingBot.class})
    public TelegramBotsApi telegramBotsApi(@Autowired LongPollingBot telegramBot) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
