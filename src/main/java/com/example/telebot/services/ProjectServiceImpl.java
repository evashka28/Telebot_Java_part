package com.example.telebot.services;

import com.example.telebot.Converter;
import com.example.telebot.Project;
import com.example.telebot.TodoistConnector;
import com.example.telebot.dao.ProjectDAO;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final TodoistConnector connector;

    private final ProjectDAO projectDAO;

    private final UserService userService;

    private final TaskService taskService;

    @Autowired
    public ProjectServiceImpl(TodoistConnector connector, ProjectDAO projectDAO, UserService userService, TaskService taskService) {
        this.connector = connector;
        this.projectDAO = projectDAO;
        this.userService = userService;
        this.taskService = taskService;
    }

    //создаёт проект в todoist и добавляет его в БД
    @Override
    public Project create(Project project, long userId) throws IOException, ParseException {
        Project createdProject = Converter.parseProjectJSON(
                connector.createProject(userService.getToken(userId), project.getName()));
        projectDAO.save(createdProject);
        return createdProject;
    }

    //обновляет проект в БД и todoist
    @Override
    public Project update(Project project, long userId) throws IOException, ParseException {
        Project updatedProject = Converter.parseProjectJSON(
                connector.updateProject(userService.getToken(userId), project.getName()));
        return projectDAO.update(updatedProject);
    }

    //возвращает проект, находящийся в БД
    @Override
    public Project get(long projectId, long userId) throws IOException, ParseException {
        return Converter.parseProjectJSON(connector.getProject(userService.getToken(userId), projectId));
    }

    //возвращает все проекты пользователя из todoist
    @Override
    public List<Project> all(long userId) throws IOException, ParseException {
        return Converter.parseAllProjectsJSON(connector.getUsersProjects(userService.getToken(userId), "*"));
    }

    //возвращает проекты пользователя из БД
    @Override
    public List<Project> allSelected(long userId) throws IOException, ParseException {
        List<Project> output = projectDAO.getAllByUserId(userId);
        output = mergeProjects(output,userId);
        return output;
    }

    //выбор проекта из проектов юзера в todoist и добавление его с задачами в бд
    @Override
    public Project select(long projectTodoistId, long userId) throws IOException, ParseException {
        String input = connector.getProjectAndTasks(userService.getToken(userId), projectTodoistId);
        Project selectedProject = Converter.parseProjectJSON(input);
        selectedProject.setUserId(userId);
        selectedProject.setFavourite(false);
        selectedProject = projectDAO.save(selectedProject);
        taskService.addTasksToDB(input, selectedProject.getId());
        return selectedProject;
    }

    //удаление проекта и всех задач из БД

    public void deselect(long projectId, long userId){

    }

    //удаление проекта и всех задач из БД и todoist
    @Override
    public void delete() {

    }

    //получение проекта из БД и добавление информации из todoist
    public Project mergeProject(Project project, long userId) throws IOException, ParseException {
        Project compareProject = get(project.getTodoistId(), userId);
        if(compareProject == null)
            return null;
        project.setName(compareProject.getName());
        return project;
    }

    public List<Project> mergeProjects(List<Project> projects, long userId) throws IOException, ParseException {
        Iterator<Project> i = projects.iterator();
        while(i.hasNext()) {
            Project project = i.next();
            project = mergeProject(project, userId);
            if(project == null)
                i.remove();
        }
        return projects;
    }
}
