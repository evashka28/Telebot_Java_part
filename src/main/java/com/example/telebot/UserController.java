package com.example.telebot;


import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @PostMapping(value = "/user", consumes = "application/json", produces = "application/json")
    public User user(@RequestBody User newUser){
        System.out.print(newUser.toString());
        return newUser;
    }
}
