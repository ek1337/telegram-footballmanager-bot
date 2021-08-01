package ru.ekimov.telegram_fooball_wh_bot.botapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;

import java.util.List;

@Component
public class ParticipantListBuilder {

    public static String beautifulList (List<Participant> list) {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;
        for (Participant p: list) {
            counter++;
            stringBuilder.append("\n").append(counter).append(". ")
                    .append(p.getFirstName()).append(" ")
                    .append(p.getLastName())
                    .append(" @").append(p.getNickName());
        }
        return stringBuilder.toString();
    }
}
