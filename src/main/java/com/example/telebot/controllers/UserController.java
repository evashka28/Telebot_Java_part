package com.example.telebot.controllers;


import com.example.telebot.User;
import com.example.telebot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User newUser) throws IOException {
        return service.create(newUser);
    }

    @PutMapping(value = "/user/{id}", consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User updatedUser, @PathVariable long id){
        return service.update(updatedUser, id);
    }
}
