package com.example.telebot.services;

import com.example.telebot.User;
import org.springframework.context.annotation.Bean;

public interface UserServiceInterface {
    User create(User user);

    User update(User user, String userId);

    String getToken(String id);

    long getProjectId(String id);
}
