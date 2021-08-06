package ru.ekimov.telegram_fooball_wh_bot.botapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ekimov.telegram_fooball_wh_bot.service.ParticipantService;
import ru.ekimov.telegram_fooball_wh_bot.service.ParticipantsKeyboard;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;

import java.util.List;

@Component
public class IncomingMessageHandler {
    private final ParticipantService participantService;
    private final ParticipantsKeyboard participantsKeyboard;

    @Autowired
    public IncomingMessageHandler(ParticipantService participantService, ParticipantsKeyboard participantsKeyboard) {
        this.participantService = participantService;
        this.participantsKeyboard = participantsKeyboard;
    }

    public SendMessage handleNewMessage(Message incomingMessage) {

        SendMessage replyMessage = new SendMessage();
        String text = incomingMessage.getText();
        long chatId = incomingMessage.getChatId();
        long userId = incomingMessage.getFrom().getId();
        String userFirstName = incomingMessage.getFrom().getFirstName();
        String userLastName = incomingMessage.getFrom().getLastName();
        String userNickName = incomingMessage.getFrom().getUserName();

        Participant participant = new Participant(userId, userFirstName, userLastName, userNickName);

        replyMessage.setChatId(chatId);
        replyMessage.enableHtml(true);

        if ("/help".equals(text) || "/help@football_wh_bot".equals(text)) {

            replyMessage.setText("<i>Список команд для бота:\n" +
                    "/reg - записаться на игру; \n" +
                    "/unreg - убрать себя из списка; \n" +
                    "/check - проверить список участников; \n" +
                    "/clear - очистить список участников (только адм.); \n" +
                    "/kick - удалить игрока из списка (только адм.) </i>");

        } else if ("/clear".equals(text) || "/clear@football_wh_bot".equals(text)) {
            if (ParticipantService.adminIds.contains(userId)) {
                participantService.clearParticipantList();
                replyMessage.setText("<i>Список участников очищен.</i>");
            } else replyMessage.setText("<i>Эта команда доступна только администратору.</i>");

        } else if ("/check".equals(text) || "/check@football_wh_bot".equals(text)) {
            List<Participant> participants;
            participants = participantService.getParticipants();
            String beautifulList = ParticipantListBuilder.beautifulList(participants);
            replyMessage.setText("<i> Список на игру: \n" + beautifulList + "</i>");

        } else if ("/unreg".equals(text) || "/unreg@football_wh_bot".equals(text)) {
            if (!participantService.userIsRegistered(participant)) {
                replyMessage.setText("<i>" + userFirstName + " " + userLastName + " , Вы еще не записаны на игру! </i>");
            }
            else {
                participantService.selfRemoveParticipant(participant);
                replyMessage.setText("<i>" + userFirstName + " " + userLastName + " отменил регистрацию на игру </i>") /
            }

        } else if ("/reg".equals(text) || "/reg@football_wh_bot".equals(text)) {
            if (participantService.userIsRegistered(participant)) {
                replyMessage.setText("<i>" + userFirstName + " " + userLastName + " , Вы уже записаны на игру! </i>");
            } else {
                participantService.addNewParticipant(participant);
                replyMessage.setText("<i>"  + userFirstName + " " + userLastName + " записался на игру! </i>");
            }

        } else if (("/kick".equals(text) || "/kick@football_wh_bot".equals(text))
                && ParticipantService.adminIds.contains(userId)) {
            // keyboard
            replyMessage = participantsKeyboard.setKickButtons();
            replyMessage.setChatId(chatId);
            replyMessage.enableHtml(true);
            replyMessage.setText("<i> Выберите игрока для удаления из списка: </i>");

        }
//        else if (!incomingMessage.hasText()) {
//            replyMessage.setText("");
//        }
        else replyMessage.setText("<i> Нет такой команды (или нет прав) </i>");
        return replyMessage;
    }
}
