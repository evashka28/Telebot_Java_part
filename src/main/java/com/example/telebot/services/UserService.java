package com.example.telebot.services;


import com.example.telebot.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface{
    @Override
    public User create(User user) {
        System.out.println("User (id=" + user.getId() + ") was added");
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


}
