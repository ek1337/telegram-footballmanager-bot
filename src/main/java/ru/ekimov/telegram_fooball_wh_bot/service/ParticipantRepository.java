package ru.ekimov.telegram_fooball_wh_bot.service;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.ekimov.telegram_fooball_wh_bot.model.Participant;



@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
