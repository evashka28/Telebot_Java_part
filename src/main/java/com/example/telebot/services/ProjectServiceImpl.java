package com.example.telebot.services;

import com.example.telebot.Converter;
import com.example.telebot.entities.Project;
import com.example.telebot.connectors.TodoistConnector;
import com.example.telebot.dao.ProjectDAO;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final TodoistConnector connector;

    private final ProjectDAO projectDAO;

    private final UserService userService;

    private final TaskService taskService;

    private final SyncService syncService;

    @Autowired
    public ProjectServiceImpl(TodoistConnector connector, ProjectDAO projectDAO, @Lazy UserService userService, @Lazy TaskService taskService, @Lazy SyncService syncService) {
        this.connector = connector;
        this.projectDAO = projectDAO;
        this.userService = userService;
        this.taskService = taskService;
        this.syncService = syncService;
    }

    //создаёт проект в todoist и добавляет его в БД
    @Override
    public Project create(Project project, long userId) throws IOException, ParseException {
        String tempId = UUID.randomUUID().toString();

        String response = connector.createProject(userService.getToken(userId), project.getName(), tempId);
        Pair<Long, String> idAndSyncToken = Converter.parseProjectOrTaskCreation(response, tempId);
        project.setTodoistId(idAndSyncToken.getLeft());
        project.setUser(userService.getById(userId));
        return projectDAO.save(project);
    }

    //обновляет проект в БД и todoist
    @Override
    public Project update(Project project, long userId) throws IOException, ParseException {
        syncService.sync(userId);
        Project updatedProject = Converter.parseProjectJSON(
                connector.updateProject(userService.getToken(userId), project.getName()));
        return projectDAO.update(updatedProject);
    }

    //возвращает проект, находящийся в БД
    @Override
    public Project get(long projectId, long userId) throws IOException, ParseException {
        syncService.sync(userId);
        return Converter.parseProjectJSON(connector.getProject(userService.getToken(userId), projectId));
    }

    //возвращает все проекты пользователя из todoist
    @Override
    public List<Project> all(long userId) throws IOException, ParseException {
        return Converter.parseMultipleProjectsJSON(connector.getUsersProjects(userService.getToken(userId), "*"));
    }

    //возвращает проекты пользователя из БД
    @Override
    public List<Project> allSelected(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        List<Project> output = projectDAO.getAllByUserId(userId);
        output = mergeProjects(output, userId);
        return output;
    }

    //выбирает проект из проектов юзера в todoist и добавление его с задачами в бд
    @Override
    public Project select(long projectTodoistId, long userId) throws IOException, ParseException {
        String input = connector.getProjectAndTasks(userService.getToken(userId), projectTodoistId);
        Project selectedProject = Converter.parseProjectJSON(input);
        selectedProject.setUser(userService.getById(userId));
        selectedProject.setFavourite(false);
        selectedProject = projectDAO.save(selectedProject);
        taskService.addTasksToDB(input, selectedProject);
        return selectedProject;
    }

    //удаляет проект и все задачи из БД
    @Override
    public void deselect(long projectId) {
        Project project = projectDAO.findById(projectId);
        projectDAO.delete(project);
    }

    //удаляет проект и всех задачи из БД и todoist
    @Override
    public void delete(long projectId, long userId) throws IOException {
        connector.deleteProject(userService.getToken(userId), projectDAO.findById(projectId).getTodoistId());
        deselect(projectId);
    }

    //Возвращает проект из БД с добавлением информации из todoist
    public Project mergeProject(Project project, long userId) throws IOException, ParseException {
        Project compareProject = get(project.getTodoistId(), userId);
        if (compareProject == null)
            return null;
        project.setName(compareProject.getName());
        return project;
    }

    public List<Project> mergeProjects(List<Project> projects, long userId) throws IOException, ParseException {
        Iterator<Project> i = projects.iterator();
        while (i.hasNext()) {
            Project project = i.next();
            project = mergeProject(project, userId);
            if (project == null)
                i.remove();
        }
        return projects;
    }

    //Возвращает неизбранные проект пользователя
    public Project getUserProject(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        Project output = projectDAO.getProjectByUserId(userId, false);
        return output;
    }

    //Возвращает избранный проект пользователя
    public Project getUserFavouriteProject(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        Project output = projectDAO.getProjectByUserId(userId, true);
        if (output == null) {
            output = projectDAO.getProjectByUserId(userId, false);
        }
        return output;
    }

    //Возвращает todoistIds всех проектов пользователя
    @Override
    public List<Long> getAllTodoistIds(long userId) {
        return projectDAO.getAllTodoistIdsByUserId(userId);
    }

    //Возвращает проект по todoistId и id пользователя
    @Override
    public Project getByTodoistId(long projectTodoistId, long userId) {
        return projectDAO.getByTodoistIdAndUserId(userId, projectTodoistId);
    }
}
