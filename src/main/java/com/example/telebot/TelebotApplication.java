package com.example.telebot;

import com.example.telebot.services.TaskService;
import com.example.telebot.services.TaskServiceInterface;
import com.example.telebot.services.UserService;
import com.example.telebot.services.UserServiceInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import  com.example.telebot.User;
import  com.example.telebot.services.UserServiceBD;


import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
public class TelebotApplication {

    public static void main(String[] args) {
        UserServiceBD userServiceBD = new UserServiceBD();
        User user = new User("678", "Mina", "ghjy35");
        userServiceBD.saveUser(user);
        SpringApplication.run(TelebotApplication.class, args);

    }
}
   /*
    @Bean TodoistConnector todoistConnector(){
        return new TodoistConnector();
    }

    @Bean
    public Docket swaggerSettings() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
*/