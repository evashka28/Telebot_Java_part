package com.example.telebot.services;

import com.example.telebot.Task;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    public Task create(Task task, long userId) throws IOException, ParseException;

    public List<Task> all(long userId) throws IOException, ParseException;

    public Task get(long userId, long taskId) throws IOException, ParseException;

    public Task update(long id, Task task, long userId) throws IOException;

    public void delete(long id, long userId) throws IOException;

    public void complete(long id, long userId) throws IOException;

    public void addTasksToDB(String input, long projectId) throws ParseException;

    public void deleteTasksFromDBByProjectId(long projectId);
}
