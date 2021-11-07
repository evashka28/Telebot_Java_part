package com.example.telebot.services;


import  com.example.telebot.Task;
import  com.example.telebot.dao.TaskDAOImpl;


public class TaskServiceBD {

    private TaskDAOImpl taskDao = new TaskDAOImpl();

    public TaskServiceBD() {
    }

    public Task findUser(int id) {
        return taskDao.findById(id);
    }

    public Task saveUser(Task task) {
        return taskDao.save(task);
    }

    public void deleteUser(Task task) {
        taskDao.delete(task);
    }

    public Task updateUser(Task task) {
        return taskDao.update(task);
    }




}
