package com.example.telebot.controllers;

import com.example.telebot.Tag;
import com.example.telebot.Task;
import com.example.telebot.services.TaskService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service){
        this.service = service;
    }

    @GetMapping(value = "/tasks", produces = "application/json")
    List<Task> allTasks(@RequestHeader long userId) throws IOException, ParseException {
        return service.all(userId);
    }

    @GetMapping(value = "/tasks/favourite", produces = "application/json")
    List<Task> allFavouriteTasks(@RequestHeader long userId) throws IOException, ParseException {
        return service.allFavourite(userId);
    }

    @GetMapping(value = "/task/{id}", produces = "application/json")
    Task getTaskById(@PathVariable long id, @RequestHeader long userId) throws IOException, ParseException {
        return service.taskFromTodoist(userId, id);
    }

    @GetMapping(value = "/task", produces = "application/json")
    Task getTask(@RequestHeader long userId) throws IOException, ParseException {
        return service.get(userId);
    }

    @GetMapping(value = "/tasks/tag/{tagId}", produces = "application/json")
    List<Task> getAllTasksByTagId(@PathVariable long tagId, @RequestHeader long userId) throws IOException, ParseException {
        return service.allByTag(userId, tagId);
    }

    @GetMapping(value = "/task/tag/{tagId}", produces = "application/json")
    Task getTaskByTagId(@PathVariable long tagId, @RequestHeader long userId) throws IOException, ParseException {
        return service.getByTag(userId, tagId);
    }

    @PostMapping(value = "/task", consumes = "application/json", produces = "application/json")
    Task newTask(@RequestBody Task newTask, @RequestParam(defaultValue = "") List<Long> tagsIds, @RequestHeader long userId) throws IOException, ParseException {
        return service.create(newTask, userId, tagsIds);
    }

    @PutMapping(value = "/task/{id}", consumes = "application/json", produces = "application/json")
    Task updateTask(@PathVariable long id, @RequestBody Task updatedTask, @RequestParam(defaultValue = "") List<Long> tagsIds, @RequestHeader long userId) throws IOException, ParseException {
        return service.update(id, updatedTask, userId, tagsIds);
    }

    @PutMapping(value = "/task/favourite/{id}")
    Task changeTasksFavourite(@PathVariable long id, @RequestHeader long userId) throws IOException {
        return null;
    }

    @DeleteMapping(value = "/task/{id}")
    void deleteTask(@PathVariable long id, @RequestHeader long userId) throws IOException {
        service.delete(id, userId);
    }

    @PutMapping(value = "/task/{id}/complete")
    void completeTask(@PathVariable long id, @RequestHeader long userId) throws IOException {
        service.complete(id, userId);
    }

    @PutMapping(value = "/task/{id}/tag/{tagId}")
    boolean addTagToTask(@PathVariable long id, @PathVariable long tagId, @RequestHeader long userId) {
        return service.addTagToTask(userId, id, tagId);
    }
}
