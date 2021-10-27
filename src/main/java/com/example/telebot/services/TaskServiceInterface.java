package com.example.telebot.services;

import com.example.telebot.Task;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public interface TaskServiceInterface {
    Task create(Task task, String userId) throws IOException;

    List<Task> all(String userId);

    Task get(String userId);

    Task update(long id, Task task, String userId) throws IOException;

    void delete(long id, String userId) throws IOException;

    void complete(long id, String userId) throws IOException;
}
