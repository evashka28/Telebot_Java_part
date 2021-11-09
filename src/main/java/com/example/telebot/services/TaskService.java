package com.example.telebot.services;

import com.example.telebot.Task;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    Task create(Task task, long userId) throws IOException;

    List<Task> all(long userId) throws IOException, ParseException;

    Task get(long userId, long taskId) throws IOException, ParseException;

    Task update(long id, Task task, long userId) throws IOException;

    void delete(long id, long userId) throws IOException;

    void complete(long id, long userId) throws IOException;
}
