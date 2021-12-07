package com.example.telebot.quartz_try.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import java.time.LocalTime;
import java.util.List;




@Getter
@Setter
public class   TagRequest {

    private long userId;
    private long tagId;
    String daysOfWeek;
    @NonNull
    private LocalTime dateTime;


}
