package ru.ekimov.telegram_fooball_wh_bot.botapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ekimov.telegram_fooball_wh_bot.service.ParticipantService;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;

@Component
public class CallBackDataHandler {

    private final ParticipantService participantService;

    @Autowired
    public CallBackDataHandler (ParticipantService participantService) {
        this.participantService = participantService;
    }

    public SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(callbackQuery.getMessage().getChatId());
        long chatId = callbackQuery.getMessage().getChatId();
        long adminId = callbackQuery.getFrom().getId();
        long targetId = Long.parseLong(callbackQuery.getData());
        String targetUser = callbackQuery.getFrom().getFirstName() + " " + callbackQuery.getFrom().getLastName();

        Participant p = new Participant(targetId);

            if (ParticipantService.adminIds.contains(adminId)
                && participantService.userIsRegistered(p)) {
                participantService.kickParticipant(p, p.getId());
                replyMessage.setText("<i> Игрок " + targetUser + " был удален админом из списка на игру </i>");
            }
            else replyMessage.setText("<i> Только админ может кикнуть игрока. </i>");


        return replyMessage;
    }
}
