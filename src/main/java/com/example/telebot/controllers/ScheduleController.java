package com.example.telebot.controllers;


import com.example.telebot.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScheduleController {
    @GetMapping(value = "/schedule/{id}")
    Schedule getScheduleById(@RequestHeader long userId, @RequestParam long scheduleId) { return null; }

    @GetMapping(value = "/schedules")
    List<Schedule> allSchedules(@RequestHeader long userId) { return null; }

    @PostMapping(value = "/schedule")
    Schedule newSchedule(@RequestBody Schedule schedule, @RequestHeader long userId) { return null; }

    @PutMapping
    Schedule updateSchedule(@RequestBody Schedule schedule, @RequestHeader long userId) { return null; }

    @DeleteMapping
    void deleteSchedule(@RequestHeader long userId, @RequestParam long scheduleId) {}

}
