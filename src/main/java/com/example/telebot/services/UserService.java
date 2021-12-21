package com.example.telebot.services;

import com.example.telebot.entities.User;

import java.io.IOException;

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
