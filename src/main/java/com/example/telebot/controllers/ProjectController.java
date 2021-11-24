package com.example.telebot.controllers;


import com.example.telebot.Project;
import com.example.telebot.services.ProjectService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public List<Project> allProjects(@RequestHeader long userId) throws IOException, ParseException {
        return service.all(userId);
    }

    //возвращает проекты пользователя, хранящиеся в БД
    @GetMapping(value = "projects/selected")
    public List<Project> allSelectedProjects(@RequestHeader long userId) throws IOException, ParseException {
        return service.allSelected(userId);
    }

    @PostMapping(value = "project")
    public Project createProject(@RequestBody Project newProject, @RequestHeader long userId) throws IOException, ParseException {
        return service.create(newProject, userId);
    }

    @DeleteMapping(value = "project/{id}")
    public void deleteProject(@PathVariable long id, @RequestHeader long userId) throws IOException {
        service.delete(id, userId);
    }

    @PostMapping(value = "project/selected/{id}")
    public Project selectProject(@PathVariable long id, @RequestHeader long userId) throws IOException, ParseException {
        return service.select(id, userId);
    }
    @DeleteMapping(value = "project/selected/{id}")
    public void deselectProject(@PathVariable long id){
        service.deselect(id);
    }
}
