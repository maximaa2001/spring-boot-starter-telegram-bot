# Spring-boot-starter-telegram-bot

This is a spring boot starter for [simple-telegram-bot-library](https://github.com/maximaa2001/simple-telegram-bot)

## Webhooks vs Long Polling

Support only long pulling method at now

## Configuration

The following properties must be configured in application.yml or application.properties

| property              | description          |
|-----------------------|----------------------|
| telegram.bot.username | username of your bot |
| telegram.bot.token    | token of your bot    |

## Usage

Just add next dependency

```xml
<dependency>
    <groupId>com.maks.starter</groupId>
    <artifactId>spring-boot-starter-telegram-bot</artifactId>
    <version>1.0.0</version>
</dependency>
```

In the next step you should implement `com.maks.telegram.command.Command` and define them as spring beans
