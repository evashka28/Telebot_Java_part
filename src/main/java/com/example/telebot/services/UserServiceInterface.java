package com.example.telebot.services;

import com.example.telebot.User;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public interface UserServiceInterface {
    User create(User user) throws IOException;

    User update(User user, String userId);

    String getToken(String id);

    long getProjectId(String id);

    long getProjectFavouritesId(String id);
}
