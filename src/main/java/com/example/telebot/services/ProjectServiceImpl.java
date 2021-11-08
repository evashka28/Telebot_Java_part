package com.example.telebot.services;

import com.example.telebot.Project;
import com.example.telebot.TodoistConnector;
import com.example.telebot.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final TodoistConnector connector;

    private final ProjectDAO projectDAO;

    @Autowired
    public ProjectServiceImpl(TodoistConnector connector, ProjectDAO projectDAO) {
        this.connector = connector;
        this.projectDAO = projectDAO;
    }

    @Override
    public Project create(Project project) {
        return null;
    }

    @Override
    public Project update(Project project) {
        return null;
    }

    @Override
    public List<Project> all(String token) {
        return null;
    }

    @Override
    public List<Project> allSelected(long userId) {
        return projectDAO.getAllByUserId(userId);
    }

    @Override
    public Project select(long projectId, String userId) {
        //connector.getProject();
        return null;
    }

    @Override
    public void deleteProjectAndTasksFromDB() {

    }
}
