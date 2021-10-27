package com.example.telebot.services;

import com.example.telebot.Task;
import com.example.telebot.TodoistConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService implements TaskServiceInterface{
    private final TodoistConnector connector;

    private final UserService service;

    @Autowired
    public TaskService(TodoistConnector connector, UserService service){
        this.connector = connector;
        this.service = service;
    }

    @Override
    public Task create(Task task, String userId) throws IOException {
        connector.createTask(service.getToken(userId), task.getName(), task.getUrl(), service.getProjectId(userId));
        return task;
    }

    @Override
    public List<Task> all(String userId) {
        return null;
    }

    @Override
    public Task get(String userId) {
        return null;
    }

    @Override
    public Task update(long id, Task task, String userId) throws IOException {
        connector.updateTask(service.getToken(userId), task.getName(), task.getUrl(), id);
        return task;
    }

    @Override
    public void delete(long id, String userId) throws IOException {
        connector.deleteTask(service.getToken(userId), id);
    }

    @Override
    public void complete(long id, String userId) throws IOException {
        connector.completeTask(service.getToken(userId), id);
    }
}
