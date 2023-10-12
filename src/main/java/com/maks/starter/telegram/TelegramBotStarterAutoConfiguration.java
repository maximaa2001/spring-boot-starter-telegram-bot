package com.maks.starter.telegram;

import com.maks.telegram.bot.DefaultTelegramLongPollingBot;
import com.maks.telegram.command.Command;
import com.maks.telegram.command.factory.CommandFactory;
import com.maks.telegram.command.factory.ParamsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;


@Configuration
@EnableConfigurationProperties(BotProperties.class)
@RequiredArgsConstructor
public class TelegramBotStarterAutoConfiguration {
    private final BotProperties botProperties;

    @Bean
    @ConditionalOnMissingBean
    public ParamsFactory paramsFactory() {
        return new ParamsFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandFactory commandFactory(@Autowired List<Command> commandList) {
        return new CommandFactory(commandList);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "bot", name = {"username", "token"})
    public TelegramBot telegramBot(@Autowired CommandFactory commandFactory, @Autowired ParamsFactory paramsFactory) {
        return new DefaultTelegramLongPollingBot(botProperties.getUsername(), botProperties.getToken(),
                commandFactory, paramsFactory);
    }
}
