package com.example.telebot;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    @GetMapping(value = "/tasks", produces = "application/json")
    List<Task> allTasks(){
        return new ArrayList<Task>();
    }

    @PostMapping(value = "/tasks", consumes = "application/json", produces = "application/json")
    Task newTasks(@RequestBody Task newTask){
        return newTask;
    }

    @PutMapping(value = "/tasks/{id}", consumes = "application/json", produces = "application/json")
    Task updateTask(@PathVariable String id, @RequestBody Task updatedTask){
        return updatedTask;
    }

    @DeleteMapping(value = "/tasks/{id}")
    void deleteTask(@PathVariable String id) {

    }
}
