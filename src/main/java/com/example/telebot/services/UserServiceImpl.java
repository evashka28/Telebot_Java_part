package com.example.telebot.services;


import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import com.example.telebot.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private final TodoistConnector connector;
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(TodoistConnector connector, UserDAO userDAO) {
        this.connector = connector;
        this.userDAO = userDAO;
    }

    @Override
    public User create(User user) throws IOException {
        return userDAO.save(user);
    }

    @Override
    public User update(User user, long userId) {
        return userDAO.update(user);
    }

    @Override
    public String getToken(long id) {
        return userDAO.findById(id).getToken();
    }

    @Override
    public long getProjectId(long id) {
        return 2277542643L;
    }

    @Override
    public long getProjectFavouritesId(long id) {
        return 2277542644L;
    }


}
