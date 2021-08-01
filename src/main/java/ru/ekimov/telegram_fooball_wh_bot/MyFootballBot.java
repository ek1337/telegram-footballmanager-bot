package ru.ekimov.telegram_fooball_wh_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.ekimov.telegram_fooball_wh_bot.botapi.CallBackDataHandler;
import ru.ekimov.telegram_fooball_wh_bot.botapi.IncomingMessageHandler;


public class MyFootballBot extends TelegramWebhookBot {
    private String webHookPath;
    private String botUserName;
    private String botToken;
    private final IncomingMessageHandler incomingMessageHandler;
    private final CallBackDataHandler callBackDataHandler;

    final static Logger logger = LoggerFactory.getLogger(MyFootballBot.class);

    @Autowired
    public MyFootballBot(IncomingMessageHandler incomingMessageHandler, CallBackDataHandler callBackDataHandler) {
        this.incomingMessageHandler = incomingMessageHandler;
        this.callBackDataHandler = callBackDataHandler;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()) {
            SendMessage replyMessage = incomingMessageHandler.handleNewMessage(message);
            try {
                execute(replyMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            logger.info(("New callback query from User:{}, userId: {},  with data: {}"),
                    callbackQuery.getFrom().getUserName(), callbackQuery.getFrom().getId(), callbackQuery.getData());
            SendMessage replyMessage = callBackDataHandler.processCallbackQuery(callbackQuery);
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
            deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
            replyMessage.enableHtml(true);
            try {
                execute(replyMessage);
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
