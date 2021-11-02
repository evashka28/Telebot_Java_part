package com.example.telebot.services;


import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService implements UserServiceInterface{

    private final TodoistConnector connector;

    @Autowired
    public UserService(TodoistConnector connector) {
        this.connector = connector;
    }

    @Override
    public User create(User user) throws IOException {
        System.out.println("User (id=" + user.getId() + ") was added");
        connector.createProject(user.getToken(), "TeleBotProject");
        connector.createProject(user.getToken(), "TeleBotFavouritesProject");
        return null;
    }

    @Override
    public User update(User user, String userId) {
        return null;
    }

    @Override
    public String getToken(String id) {
        return "0193f9ca236affc47cc58ea0868e25bc494da9fe";
    }

    @Override
    public long getProjectId(String id) {
        return 2276294580L;
    }

    @Override
    public long getProjectFavouritesId(String id) {
        return 2276294580L;
    }


}
