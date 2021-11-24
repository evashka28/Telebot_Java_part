package com.example.telebot.services;

import com.example.telebot.Project;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

public interface ProjectService {
    Project create(Project project, long userId) throws IOException, ParseException;

    Project update(Project project, long userId) throws IOException, ParseException;

    Project get(long projectId, long userId) throws IOException, ParseException;

    List<Project> all(long userId) throws IOException, ParseException;

    List<Project> allSelected(long userId) throws IOException, ParseException;

    Project select(long projectId, long userId) throws IOException, ParseException;

    void deselect(long projectId);

    void delete(long projectId, long userId) throws IOException;

    Project getUserProject(long userId);

    Project getUserFavouriteProject(long userId);

    List<Long> getAllTodoistIds(long userId);

    Project getByTodoistId(long projectTodoistId, long userId);
}
