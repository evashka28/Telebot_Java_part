package com.example.telebot.services;

import com.example.telebot.Project;
import com.example.telebot.Task;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    Task create(Task task, long userId, List<Long> tagIds) throws IOException, ParseException;

    List<Task> all(long userId) throws IOException, ParseException;

    List<Task> allByTag(long userId, long tagId) throws IOException, ParseException;

    Task get(long userId, long taskId) throws IOException, ParseException;

    Task get(long userId) throws IOException, ParseException;

    Task getByTag(long userId, long tagId) throws IOException, ParseException;

    Task update(long id, Task task, long userId, List<Long> tagIds) throws IOException;

    void delete(long id, long userId) throws IOException;

    void complete(long id, long userId) throws IOException;

    void addTasksToDB(String input, Project project) throws ParseException;

    void deleteTasksFromDBByProjectId(long projectId);

    void deleteTaskFromBD(long id);

    List<Long> getAllTodoistIds(long userId);

    Task getByTodoistId(long taskTodoistId, long userId);

    void syncCreate();
}
