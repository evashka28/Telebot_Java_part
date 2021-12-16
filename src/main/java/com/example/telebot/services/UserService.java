package com.example.telebot.services;

import com.example.telebot.User;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.ZoneId;

public interface UserService {
    User create(User user) throws IOException;

    User update(User user, long userId);

    User getUser(long userId);

    String getToken(long id);

    String getZone(long id);

    void deleteUser(long id);

    User getById(long id);

    String getSyncTokenByUserId(long id);

    User updateTimezone(long id, String timezone);
}
