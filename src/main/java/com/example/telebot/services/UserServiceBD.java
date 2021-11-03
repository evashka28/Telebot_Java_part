package com.example.telebot.services;


import  com.example.telebot.User;
import  com.example.telebot.dao.UserDAOImpl;


public class UserServiceBD {

    private UserDAOImpl usersDao = new UserDAOImpl();

    public UserServiceBD() {
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