package com.example.telebot.dao;

import com.example.telebot.utils.HibernateSessionFactoryUtil;
import  com.example.telebot.User;


public interface UserDAO {
    public User findById(int id);
    public void save(User user);
    public void update(User user);
    public void delete(User user);


}