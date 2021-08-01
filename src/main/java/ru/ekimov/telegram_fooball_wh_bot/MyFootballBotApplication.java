package ru.ekimov.telegram_fooball_wh_bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyFootballBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyFootballBotApplication.class, args);

    }
}
