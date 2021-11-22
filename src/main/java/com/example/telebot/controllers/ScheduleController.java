package com.example.telebot.controllers;


import com.example.telebot.Schedule;
import com.example.telebot.services.SyncService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ScheduleController {
    private final SyncService scheduleService;

    @Autowired
    public ScheduleController(SyncService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/schedule/{id}")
    Schedule getScheduleById(@RequestHeader long userId, @RequestParam long scheduleId) throws IOException, ParseException {
        scheduleService.sync(userId);
        return null;
    }

    @GetMapping(value = "/schedules")
    List<Schedule> allSchedules(@RequestHeader long userId) { return null; }

    @PostMapping(value = "/schedule")
    Schedule newSchedule(@RequestBody Schedule schedule, @RequestHeader long userId) { return null; }

    @PutMapping
    Schedule updateSchedule(@RequestBody Schedule schedule, @RequestHeader long userId) { return null; }

    @DeleteMapping
    void deleteSchedule(@RequestHeader long userId, @RequestParam long scheduleId) {}

}
