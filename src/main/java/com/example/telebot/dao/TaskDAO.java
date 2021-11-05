package com.example.telebot.dao;

import com.example.telebot.utils.HibernateSessionFactoryUtil;
import  com.example.telebot.Task;


public interface TaskDAO {
    public Task findById(int id);
    public void save(Task task);
    public void update(Task task);
    public void delete(Task task);


}
