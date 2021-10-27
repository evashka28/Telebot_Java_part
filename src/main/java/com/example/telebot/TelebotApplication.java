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
            con.completeTask("0193f9ca236affc47cc58ea0868e25bc494da9fe", 5254480855L);
            con.deleteTask("0193f9ca236affc47cc58ea0868e25bc494da9fe", 5267922027L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void SendUserInfoToDB(String address, String token){

    }

}
