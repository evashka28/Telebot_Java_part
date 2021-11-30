package com.example.telebot.services;


import com.example.telebot.Task;
import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import com.example.telebot.dao.AbstractDAO;
import com.example.telebot.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;

@Service
public class UserServiceImpl implements UserService {

    private static UserDAO userDAO;
    private final TodoistConnector connector;
    //private final  UserDAO  userDAO;

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
    public void deleteUser(long userId){
        User user = userDAO.findById(userId);
        userDAO.delete(user);
    }

    @Override
    public User update(User user, long userId) {
        return userDAO.update(user);
    }

    @Override
    public User getUser(long userId) {
        return userDAO.findById(userId);
    }



    @Override
    public String getToken(long id) {
        return userDAO.findById(id).getToken();
    }

    @Override
    public String getZone(long id) {
        return userDAO.findById(id).getZone();
    }

    @Override
    public User getById(long id) {
        return userDAO.findById(id);
    }

    @Override
    public String getSyncTokenByUserId(long id) {
        return userDAO.findById(id).getSyncToken();
    }


}
