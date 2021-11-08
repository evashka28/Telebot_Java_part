package com.example.telebot.services;

import com.example.telebot.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project project);

    Project update(Project project);

    List<Project> all(String token);

    List<Project> allSelected(long userId);

    Project select(long projectId, String userId);

    void deleteProjectAndTasksFromDB();
}
