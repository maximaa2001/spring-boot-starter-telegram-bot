package com.maks.starter.telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("bot")
public class BotProperties {
    private String username;
    private String token;
}
