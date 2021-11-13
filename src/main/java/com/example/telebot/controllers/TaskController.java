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

    @GetMapping(value = "/task/{id}", produces = "application/json")
    Task getTaskById(@PathVariable long id, @RequestHeader long userId) throws IOException, ParseException {
        return service.get(userId, id);
    }

    @GetMapping(value = "/task", produces = "application/json")
    Task getTask(@RequestHeader long userId) throws IOException, ParseException {
        return service.get(userId);
    }

    @PostMapping(value = "/task", consumes = "application/json", produces = "application/json")
    Task newTask(@RequestBody Task newTask, @RequestBody List<Long> tagsIds, @RequestHeader long userId) throws IOException, ParseException {
        return service.create(newTask, userId);
    }

    @PutMapping(value = "/task/{id}", consumes = "application/json", produces = "application/json")
    Task updateTask(@PathVariable long id, @RequestBody Task updatedTask, @RequestBody List<Long> tagsIds, @RequestHeader long userId) throws IOException {
        return service.update(id, updatedTask, userId);
    }

    @DeleteMapping(value = "/task/{id}")
    void deleteTask(@PathVariable long id, @RequestHeader long userId) throws IOException {
        service.delete(id, userId);
    }

    @PutMapping(value = "/task/{id}/complete")
    void completeTask(@PathVariable long id, @RequestHeader long userId) throws IOException {
        service.complete(id, userId);
    }
}
