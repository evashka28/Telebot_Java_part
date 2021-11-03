package com.example.telebot.controllers;

import com.example.telebot.Task;
import com.example.telebot.TaskType;
import com.example.telebot.services.TaskServiceInterface;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
public class TaskController {
    private final TaskServiceInterface service;

    @Autowired
    public TaskController(TaskServiceInterface service){
        this.service = service;
    }

    @PostMapping(value="test")
    TaskType test(@RequestBody TaskType type)
    {
        return type;
    }


    @GetMapping(value = "/tasks", produces = "application/json")
    List<Task> allTasks(@RequestHeader String userId) throws IOException, ParseException {
        return service.all(userId);
    }

    @GetMapping(value = "/task/{id}", produces = "application/json")
    Task task(@PathVariable long id, @RequestHeader String userId) throws IOException, ParseException {
        return service.get(userId, id);
    }

    @PostMapping(value = "/task", consumes = "application/json", produces = "application/json")
    Task newTasks(@RequestBody Task newTask, @RequestHeader String userId) throws IOException {
        return service.create(newTask, userId);
    }

    @PutMapping(value = "/task/{id}", consumes = "application/json", produces = "application/json")
    Task updateTask(@PathVariable long id, @RequestBody Task updatedTask, @RequestHeader String userId) throws IOException {
        return service.update(id, updatedTask, userId);
    }

    @DeleteMapping(value = "/task/{id}")
    void deleteTask(@PathVariable long id, @RequestHeader String userId) throws IOException {
        service.delete(id, userId);
    }

    @PutMapping(value = "/task/{id}/complete")
    void completeTask(@PathVariable long id, @RequestHeader String userId) throws IOException {
        service.complete(id, userId);
    }
}
