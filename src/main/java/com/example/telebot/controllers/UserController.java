package com.example.telebot.controllers;


import com.example.telebot.User;
import com.example.telebot.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserServiceInterface service;


    @Autowired
    public UserController(UserServiceInterface service){
        this.service = service;
    }

    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public User user(@RequestBody User newUser){
        return service.create(newUser);
    }

    @PutMapping(value = "/user/{id}", consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User updatedUser, @PathVariable String id){
        return service.update(updatedUser, id);
    }
}
