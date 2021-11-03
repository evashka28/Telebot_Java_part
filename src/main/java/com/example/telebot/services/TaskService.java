package com.example.telebot.services;

import com.example.telebot.Task;
import com.example.telebot.TodoistConnector;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
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
        long projectId = (task.getFavourite() ? service.getProjectFavouritesId(userId) : service.getProjectId(userId));
        connector.createTask(service.getToken(userId), task.getContent(), task.getDescription(), projectId);
        return task;
    }

    @Override
    public List<Task> all(String userId) throws IOException, ParseException {
        return connector.getAllTasks(service.getToken(userId), Arrays.asList(5278795244L, 5278654858L));
    }

    @Override
    public Task get(String userId, long taskId) throws IOException, ParseException {
        return connector.getTask(service.getToken(userId), taskId);
    }

    @Override
    public Task update(long id, Task task, String userId) throws IOException {
        connector.updateTask(service.getToken(userId),  task.getContent(), task.getDescription(), id);
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

//    public boolean CheckURL(String url)
//    {
//        return true;
//    }
}
