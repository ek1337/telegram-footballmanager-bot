package ru.ekimov.telegram_fooball_wh_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipantsKeyboard {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantsKeyboard (ParticipantService participantService) {
        this.participantService = participantService;
    }

    public SendMessage setKickButtons() {
        SendMessage replyMessage = new SendMessage();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<Participant> playerList = participantService.getParticipants();

        for (Participant participant : playerList) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            Long regUserId = participant.getId();
            String regUserInfo = participant.getFirstName()
                    + " " + participant.getLastName() + " aka " + participant.getNickName();
            button.setCallbackData(regUserId.toString());
            button.setText(regUserInfo);
            row.add(button);
            keyboard.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(keyboard);

        replyMessage.setReplyMarkup(inlineKeyboardMarkup);
        return replyMessage;
    }
}
