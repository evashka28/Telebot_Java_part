package com.example.telebot.quartz_try.payload;

import com.example.telebot.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import com.example.telebot.services.UserService;
import com.example.telebot.services.UserServiceImpl;


import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
public class TagRequest {

    private long userId;
    private long tagId;
    @NonNull
    private LocalDateTime dateTime;


}
