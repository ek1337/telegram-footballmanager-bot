package ru.ekimov.telegram_fooball_wh_bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PingTask {
    @Value("${pingtask.url}")
    private String url;

    @Scheduled(fixedRateString = "1200000")
    public void pingMe() {
        try {
            URL url = new URL("https://www.google.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
