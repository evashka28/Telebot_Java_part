package com.example.telebot.services;

import com.example.telebot.Converter;
import com.example.telebot.Project;
import com.example.telebot.TodoistConnector;
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
public class ProjectServiceImpl implements ProjectService{

    private final TodoistConnector connector;

    private final ProjectDAO projectDAO;

    private final UserService userService;

    private final TaskService taskService;

    @Autowired
    public ProjectServiceImpl(TodoistConnector connector, ProjectDAO projectDAO, @Lazy UserService userService, @Lazy TaskService taskService) {
        this.connector = connector;
        this.projectDAO = projectDAO;
        this.userService = userService;
        this.taskService = taskService;
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
        selectedProject.setUser(userService.getById(userId));
        selectedProject.setFavourite(false);
        selectedProject = projectDAO.save(selectedProject);
        taskService.addTasksToDB(input, selectedProject);
        return selectedProject;
    }

    //удаление проекта и всех задач из БД
    @Override
    public void deselect(long projectId){
        Project project = projectDAO.findById(projectId);
        projectDAO.delete(project);
    }

    //удаление проекта и всех задач из БД и todoist
    @Override
    public void delete(long projectId, long userId) throws IOException {
        connector.deleteProject(userService.getToken(userId), projectDAO.findById(projectId).getTodoistId());
        deselect(projectId);
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

    public Project getUserProject(long userId){
        Project output = projectDAO.getProjectByUserId(userId, false);
        return output;
    }

    public Project getUserFavouriteProject(long userId){
        Project output = projectDAO.getProjectByUserId(userId, true);
        if(output == null) {
            output = projectDAO.getProjectByUserId(userId, false);
        }
        return output;
    }

    @Override
    public List<Long> getAllTodoistIds(long userId) {
        return projectDAO.getAllTodoistIdsByUserId(userId);
    }

    @Override
    public Project getByTodoistId(long projectTodoistId, long userId) {
        return null;
    }
}
