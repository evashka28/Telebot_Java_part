package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDAO<User>{
    public UserDAO(){
        setClazz(User.class);
    }
}
