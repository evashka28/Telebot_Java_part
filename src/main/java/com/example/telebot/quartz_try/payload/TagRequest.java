package com.example.telebot.quartz_try.payload;

import com.example.telebot.Schedule;
import com.example.telebot.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import com.example.telebot.services.UserService;
import com.example.telebot.services.UserServiceImpl;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;




@Getter
@Setter
public class   TagRequest {

    private long userId;
    private long tagId;
    List<Integer> daysOfWeek;
    @NonNull
    private LocalTime dateTime;


}
