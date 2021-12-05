package com.example.telebot.services;

import com.example.telebot.*;
import com.example.telebot.dao.TaskDAO;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TodoistConnector connector;

    private final UserService userService;

    private final ProjectService projectService;

    private final TagService tagService;

    private final TaskDAO taskDAO;

    private final SyncService syncService;

    @Autowired
    public TaskServiceImpl(TodoistConnector connector, @Lazy UserService userService, @Lazy ProjectService projectService, @Lazy TagService tagService, TaskDAO taskDAO, @Lazy SyncService syncService){
        this.connector = connector;
        this.userService = userService;
        this.projectService = projectService;
        this.tagService = tagService;
        this.taskDAO = taskDAO;
        this.syncService = syncService;
    }

    //создаёт задачу и добавляет в БД
    @Override
    public Task create(Task task, long userId, List<Long> tagIds) throws IOException, ParseException {
        String tempId = UUID.randomUUID().toString();

        Project project = (task.getFavourite() ? projectService.getUserFavouriteProject(userId) : projectService.getUserProject(userId));
        String response =  connector.createTask(userService.getToken(userId), task.getContent(), task.getDescription(), project.getTodoistId(), tempId);
        Pair<Long, String> idAndSyncToken = Converter.parseProjectOrTaskCreation(response, tempId);
        task.setProject(project);
        task.setTodoistId(idAndSyncToken.getLeft());
        task.setCreationDatetime(Timestamp.from(Instant.now()));
        task.setLastAccessDatetime(Timestamp.from(Instant.now()));
        task.setTags(Set.copyOf(tagService.getMultipleByIds(tagIds)));
        return taskDAO.save(task);


    }

    //возвращает все неизбранные задачи пользователя
    @Override
    public List<Task> all(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        List<Task> output = taskDAO.getAll(userId);
        output = mergeTasks(output, userId);
        return output;
    }

    //возвращает все избранные задачи пользователя
    @Override
    public List<Task> allFavourite(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        List<Task> output = taskDAO.getAllFavourites(userId);
        output = mergeTasks(output, userId);
        return output;
    }

    //возвращает все неизбранные задачи пользователя с выбранным тегом
    @Override
    public List<Task> allByTag(long userId, long tagId) throws IOException, ParseException {
        syncService.sync(userId);
        List<Task> output = taskDAO.getAllByTagId(tagId, userId, false);
        output = mergeTasks(output, userId);
        return output;
    }

    //возвращает объект Task с информацией о задаче из todoist
    @Override
    public Task taskFromTodoist(long userId, long id) throws IOException, ParseException {
        return Converter.parseTaskJSON(connector.getTask(userService.getToken(userId), taskDAO.findById(id).getTodoistId()));
    }

    //возвращает задачу с минимальными датой и временем последнего обращения
    @Override
    public Task get(long userId) throws IOException, ParseException {
        syncService.sync(userId);
        Task task = taskDAO.getWithOldestLastAccess(userId);
        task.setLastAccessDatetime(Timestamp.from(Instant.now()));
        taskDAO.update(task);
        task = mergeTask(task, userId);
        return task;
    }

    //возвращает задачу с минимальными датой и временем последнего обращения по тегу
    @Override
    public Task getByTag(long userId, long tagId) throws IOException, ParseException {
        syncService.sync(userId);
        Task task = taskDAO.getWithOldestLastAccessByTagId(tagId, userId, false);
        task.setLastAccessDatetime(Timestamp.from(Instant.now()));
        taskDAO.update(task);
        task = mergeTask(task, userId);
        return task;
    }

    //обновляет задачу в БД и todoist
    @Override
    public Task update(long id, Task task, long userId, List<Long> tagIds) throws IOException, ParseException {
        syncService.sync(userId);
        task.setTags(Set.copyOf(tagService.getMultipleByIds(tagIds)));
        taskDAO.update(task);
        connector.updateTask(userService.getToken(userId),  task.getContent(), task.getDescription(), task.getTodoistId());
        return task;
    }

    //удаляет задачу из БД и todoist
    @Override
    public void delete(long id, long userId) throws IOException {
        Task task = taskDAO.findById(id);
        taskDAO.delete(task);
        connector.deleteTask(userService.getToken(userId), task.getTodoistId());
    }

    //завершает задачу и удаляет из БД
    @Override
    public void complete(long id, long userId) throws IOException {
        Task task = taskDAO.findById(id);
        taskDAO.delete(task);
        connector.completeTask(userService.getToken(userId), task.getTodoistId());
    }

    //вызывается в select в ProjectService добавляет задачи в БД из JSON
    @Override
    public void addTasksToDB(String input, Project project) throws ParseException {
        List<Task> tasks = Converter.parseMultipleTasksJSON(input);
        for(Task task: tasks){
            task.setProject(project);
            task.setCreationDatetime(Timestamp.from(Instant.now()));
            task.setLastAccessDatetime(Timestamp.from(Instant.now()));
            taskDAO.save(task);
        }
    }

    @Override
    public void addTaskToDB(JSONObject input, Project project) {
        Task task = Converter.taskFromJSONObject(input);
        task.setProject(project);
        task.setCreationDatetime(Timestamp.from(Instant.now()));
        task.setLastAccessDatetime(Timestamp.from(Instant.now()));
        taskDAO.save(task);
    }


    @Override
    public void deleteTaskFromBD(long id) {
        taskDAO.delete(taskDAO.findById(id));
    }

    //получение задачи из БД и добавление информации из todoist
    public Task mergeTask(Task task, long userId) throws IOException, ParseException {
        Task compareTask = taskFromTodoist(userId, task.getId());
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

    //Возвращает todoistIds всех задач пользователя
    @Override
    public List<Long> getAllTodoistIds(long userId) {
        return taskDAO.getAllTodoistIdsByUserId(userId);
    }

    //Возвращает задачу по todoistId и id пользователя
    @Override
    public Task getByTodoistId(long taskTodoistId, long userId) {
        return taskDAO.getByTodoistIdAndUserId(userId, taskTodoistId);
    }

    @Override
    public boolean addTagToTask(long userId, long id, long tagId){
        Task task = taskDAO.get(userId, id);
        Tag tag = tagService.get(tagId, userId);
        if (task == null || tag == null)
            return false;
        task.addTag(tag);
        taskDAO.save(task);
        return true;
    }

    @Override
    public void syncCreate() {

    }

    //добавляет задачу в избранное или удаляет оттуда
    @Override
    public Task updateFavouriteState(long userId) {
        return null;
    }
}
