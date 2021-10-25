package com.example.telebot;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @GetMapping(value = "/tasks", produces = "application/json")
    List<Task> allTasks(){

    }

    @PostMapping(value = "/tasks", consumes = "application/json", produces = "application/json")
    Task newTasks(){

    }

    @PutMapping(value = "/tasks/{id}", consumes = "application/json", produces = "application/json")
    Task updateTask(@PathVariable String id){

    }

    @DeleteMapping(value = "/tasks/{id}")
    void deleteTask(@PathVariable String id) {

    }
}
