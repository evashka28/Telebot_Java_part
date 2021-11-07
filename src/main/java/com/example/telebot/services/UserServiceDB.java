package com.example.telebot.services;

import  com.example.telebot.User;
import  com.example.telebot.dao.UserDAOImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDB {

    private UserDAOImpl usersDao = new UserDAOImpl();

    public UserServiceDB() {
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }




}