package com.example.telebot.dao;

import com.example.telebot.Project;
import com.example.telebot.Task;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDAO extends AbstractDAO<Task>{
    public TaskDAO(){
        setClazz(Task.class);
    }
}
