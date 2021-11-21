package com.example.telebot.services;

import com.example.telebot.User;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public interface UserService {
    User create(User user) throws IOException;

    User update(User user, long userId);

    User getUser(long userId);

    String getToken(long id);

    void deleteUser(long id);

    User getById(long id);

    long getProjectId(long id);

    long getProjectFavouritesId(long id);
}
