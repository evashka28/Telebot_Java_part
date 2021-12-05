package com.example.telebot.services;

import com.example.telebot.Project;
import com.example.telebot.Tag;
import com.example.telebot.Task;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    Task create(Task task, long userId, List<Long> tagIds) throws IOException, ParseException;

    List<Task> all(long userId) throws IOException, ParseException;

    List<Task> allFavourite(long userId) throws IOException, ParseException;

    List<Task> allByTag(long userId, long tagId) throws IOException, ParseException;

    Task taskFromTodoist(long userId, long taskId) throws IOException, ParseException;

    Task get(long userId) throws IOException, ParseException;

    Task getByTag(long userId, long tagId) throws IOException, ParseException;

    Task update(long id, Task task, long userId, List<Long> tagIds) throws IOException, ParseException;

    void delete(long id, long userId) throws IOException;

    void complete(long id, long userId) throws IOException;

    void addTasksToDB(String input, Project project) throws ParseException;


    void addTaskToDB(JSONObject input, Project project);

    void deleteTaskFromBD(long id);

    List<Long> getAllTodoistIds(long userId);

    Task getByTodoistId(long taskTodoistId, long userId);

    Tag addTagToTask(long userId, long id, long tagId);

    void syncCreate();

    Task updateFavouriteState(long userID);
}
