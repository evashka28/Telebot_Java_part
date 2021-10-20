package com.example.telebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TelebotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelebotApplication.class, args);
        TodoistConnector con = new TodoistConnector();
        try {
            con.getUsersProjects("");
            con.createTask("", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void SendUserInfoToDB(String address, String token){

    }

}
