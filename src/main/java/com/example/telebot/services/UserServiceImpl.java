package com.example.telebot.services;


import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private final TodoistConnector connector;
    private final UserServiceDB database;

    @Autowired
    public UserServiceImpl(TodoistConnector connector, UserServiceDB database) {
        this.connector = connector;
        this.database = database;
    }

    @Override
    public User create(User user) throws IOException {
        System.out.println("User (id=" + user.getId() + ") was added");
        database.saveUser(user);
        return null;
    }

    @Override
    public User update(User user, String userId) {
        //database.updateUser();
        return null;
    }

    @Override
    public String getToken(String id) {
        //database.findUser(id).getToken();
        return "0193f9ca236affc47cc58ea0868e25bc494da9fe";
    }

    @Override
    public long getProjectId(String id) {
        return 2277542643L;
    }

    @Override
    public long getProjectFavouritesId(String id) {
        return 2277542644L;
    }


}
