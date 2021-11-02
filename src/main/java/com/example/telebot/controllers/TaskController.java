package com.example.telebot.controllers;

import com.example.telebot.Task;
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


    @GetMapping(value = "/tasks", produces = "application/json")
    List<Task> allTasks(@RequestHeader String userId) throws IOException, ParseException {
        return service.all(userId, false);
    }

    @GetMapping(value = "/task/{id}", produces = "application/json")
    Task task(@PathVariable long id, @RequestHeader String userId) throws IOException, ParseException {
        return service.get(userId, id, false);
    }

    @PostMapping(value = "/task", consumes = "application/json", produces = "application/json")
    Task newTasks(@RequestBody Task newTask, @RequestHeader String userId) throws IOException {
        return service.create(newTask, userId, false);
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

    //Для избранного

    @GetMapping(value = "/fav/tasks", produces = "application/json")
    List<Task> allFavouriteTasks(@RequestHeader String userId) throws IOException, ParseException {
        return service.all(userId, true);
    }

    @GetMapping(value = "/fav/task/{id}", produces = "application/json")
    Task favouriteTask(@PathVariable long id, @RequestHeader String userId) throws IOException, ParseException {
        return service.get(userId, id, true);
    }

    @PostMapping(value = "/fav/task", consumes = "application/json", produces = "application/json")
    Task newFavouriteTasks(@RequestBody Task newTask, @RequestHeader String userId) throws IOException {
        return service.create(newTask, userId, true);
    }

    @PutMapping(value = "/fav/task/{id}", consumes = "application/json", produces = "application/json")
    Task updateFavouriteTask(@PathVariable long id, @RequestBody Task updatedTask, @RequestHeader String userId) throws IOException {
        return service.update(id, updatedTask, userId);
    }

    @DeleteMapping(value = "/fav/task/{id}")
    void deleteFavouriteTask(@PathVariable long id, @RequestHeader String userId) throws IOException {
        service.delete(id, userId);
    }


}
