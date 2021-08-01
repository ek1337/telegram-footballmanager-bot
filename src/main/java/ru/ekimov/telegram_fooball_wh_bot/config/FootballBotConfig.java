package ru.ekimov.telegram_fooball_wh_bot.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.ekimov.telegram_fooball_wh_bot.MyFootballBot;


import ru.ekimov.telegram_fooball_wh_bot.botapi.CallBackDataHandler;
import ru.ekimov.telegram_fooball_wh_bot.botapi.IncomingMessageHandler;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class FootballBotConfig {
    private String webHookPath;
    private String botUserName;
    private String botToken;
    private final IncomingMessageHandler incomingMessageHandler;
    private final CallBackDataHandler callBackDataHandler;

    @Autowired
    public FootballBotConfig(IncomingMessageHandler incomingMessageHandler, CallBackDataHandler callBackDataHandler) {
        this.incomingMessageHandler = incomingMessageHandler;
        this.callBackDataHandler = callBackDataHandler;
    }


    @Bean
    public MyFootballBot myFootballBot() {

        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);
        MyFootballBot myFootballBot = new MyFootballBot(incomingMessageHandler, callBackDataHandler);
        myFootballBot.setBotUserName(botUserName);
        myFootballBot.setBotToken(botToken);
        myFootballBot.setWebHookPath(webHookPath);

        return myFootballBot;
    }


}
