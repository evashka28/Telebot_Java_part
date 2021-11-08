package com.example.telebot.controllers;


import com.example.telebot.Project;
import com.example.telebot.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    private final ProjectService service;

    @Autowired
    public ProjectController(ProjectService service){
        this.service = service;
    }

    //возвращает все проекты пользователя в todoist
    @GetMapping(value = "/projects")
    public List<Project> allProjects(@RequestHeader String userId){
        return null;
    }

    //возвращает проекты пользователя, хранящиеся в БД
    @GetMapping(value = "projects/selected")
    public List<Project> allSelectedProjects(){
        return null;
    }

    @PostMapping(value = "project")
    public void createProject(@RequestBody Project newProject, @RequestHeader String userId){

    }

    @DeleteMapping(value = "project/{id}")
    public void deleteProject(@PathVariable long id, @RequestHeader String userId){

    }

    @PostMapping(value = "project/selected/{id}")
    public void selectProject(@PathVariable long id, @RequestHeader String userId){

    }
    @DeleteMapping(value = "project/selected/{id}")
    public void deselectProject(@PathVariable long id){

    }
}
