package com.example.telebot.services;

import com.example.telebot.Converter;
import com.example.telebot.Project;
import com.example.telebot.Task;
import com.example.telebot.TodoistConnector;
import com.example.telebot.dao.TaskDAO;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TodoistConnector connector;

    private final UserService userService;

    private final ProjectService projectService;

    private final TaskDAO taskDAO;

    @Autowired
    public TaskServiceImpl(TodoistConnector connector, @Lazy UserService userService, @Lazy ProjectService projectService, TaskDAO taskDAO){
        this.connector = connector;
        this.userService = userService;
        this.projectService = projectService;
        this.taskDAO = taskDAO;
    }

    //создаёт задачу и добавляет в БД
    @Override
    public Task create(Task task, long userId) throws IOException, ParseException {
        String tempId = UUID.randomUUID().toString();

        Project project = (task.getFavourite() ? projectService.getUserFavouriteProject(userId) : projectService.getUserProject(userId));
        String response =  connector.createTask(userService.getToken(userId), task.getContent(), task.getDescription(), project.getTodoistId(), tempId);
        Pair<Long, String> idAndSyncToken = Converter.parseProjectOrTaskCreation(response, tempId);
        task.setProjectId(project.getId());
        task.setTodoistId(idAndSyncToken.getLeft());
        task.setCreationDatetime(Timestamp.from(Instant.now()));
        task.setLastAccessDatetime(Timestamp.from(Instant.now()));
        return taskDAO.save(task);


    }

    //возвращает все задачи пользователя
    @Override
    public List<Task> all(long userId) throws IOException, ParseException {
        //String requestResponse =
        return null;
        //return connector.getAllTasks(service.getToken(userId), Arrays.asList(5278795244L, 5278654858L));
    }

    //возвращает все задачи из проекта, находящееся в БД
    public List<Task> allFromProject(long userId, long projectId) throws IOException, ParseException {
        List<Task> output = taskDAO.getAllByProjectId(projectId);
        output = mergeTasks(output, userId);
        return output;
    }

    //возвращает задачу, которая есть в БД
    @Override
    public Task get(long userId, long id) throws IOException, ParseException {
        return Converter.parseTaskJSON(connector.getTask(userService.getToken(userId), id));
    }

    //обновление задачи в БД и todoist
    @Override
    public Task update(long id, Task task, long userId) throws IOException {
        taskDAO.update(task);
        connector.updateTask(userService.getToken(userId),  task.getContent(), task.getDescription(), id);
        return task;
    }

    //удаление задачи из БД и todoist
    @Override
    public void delete(long id, long userId) throws IOException {
        Task task = taskDAO.findById(id);
        taskDAO.delete(task);
        connector.deleteTask(userService.getToken(userId), task.getTodoistId());
    }

    //завершение задачи и удаление из БД
    @Override
    public void complete(long id, long userId) throws IOException {
        Task task = taskDAO.findById(id);
        taskDAO.delete(task);
        connector.completeTask(userService.getToken(userId), task.getTodoistId());
    }

    //вызывается в select в ProjectService добавляет задачи в БД из JSON
    public void addTasksToDB(String input, long projectId) throws ParseException {
        List<Task> tasks = Converter.parseAllTasksJSON(input);
        for(Task task: tasks){
            task.setProjectId(projectId);
            task.setCreationDatetime(Timestamp.from(Instant.now()));
            task.setLastAccessDatetime(Timestamp.from(Instant.now()));
            taskDAO.save(task);
        }
    }

    @Override
    public void deleteTasksFromDBByProjectId(long projectId) {
        List<Task> tasks = taskDAO.getAllByProjectId(projectId);
        for(Task task: tasks){
            taskDAO.delete(task);
        }
    }

    //получение задачи из БД и добавление информации из todoist
    public Task mergeTask(Task task, long userId) throws IOException, ParseException {
        Task compareTask = get(task.getTodoistId(), userId);
        if(compareTask == null)
            return null;
        task.setDescription(compareTask.getDescription());
        task.setContent(compareTask.getContent());
        return task;
    }

    public List<Task> mergeTasks(List<Task> tasks, long userId) throws IOException, ParseException {
        Iterator<Task> i = tasks.iterator();
        while(i.hasNext()) {
            Task task = i.next();
            task = mergeTask(task, userId);
            if(task == null)
                i.remove();
        }
        return tasks;
    }
}
