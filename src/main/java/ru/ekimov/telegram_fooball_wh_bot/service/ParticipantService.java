package ru.ekimov.telegram_fooball_wh_bot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public static long gameChatId = -1001327211441L;
    public static ArrayList<Long> adminIds = new ArrayList<>();

    static {
        adminIds.add((long) 221474066);
    }

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    public boolean userIsRegistered(Participant p) {
        return participantRepository.existsById(p.getId());
    }

    public void addNewParticipant(Participant p) {
        participantRepository.save(p);
    }

    public void selfRemoveParticipant(Participant p) {
        participantRepository.delete(p);
    }

    public void kickParticipant(Participant p, Long id) {
        participantRepository.deleteById(id);
    }

    public void clearParticipantList() {
        participantRepository.deleteAll();
    }
}
