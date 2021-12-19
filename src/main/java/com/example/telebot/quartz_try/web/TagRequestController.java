package com.example.telebot.quartz_try.web;

import com.example.telebot.Tag;
import com.example.telebot.quartz_try.TagJob;
import com.example.telebot.quartz_try.payload.TagRequest;
import com.example.telebot.quartz_try.payload.TagResponse;
import com.example.telebot.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.telebot.services.UserService;
import com.example.telebot.services.TagRequestService;
import com.example.telebot.services.TagService;

import javax.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@RestController
public class TagRequestController {

    private final TagRequestService tagRequestService;


    @Autowired
    public TagRequestController(TagRequestService tagRequestService) {
        this.tagRequestService = tagRequestService;
    }

    @PostMapping("/schedule/tag")
    public TagRequest scheduleTag(@Valid @RequestBody TagRequest tagRequest, @RequestHeader long tagId, @RequestHeader long userId) throws SchedulerException {
        return tagRequestService.scheduleTag(tagRequest, tagId, userId);
    }

    @DeleteMapping(value = "/schedule/{id}")
    void delete(@PathVariable String id) throws SchedulerException {
        tagRequestService.delete(id);
    }


    @GetMapping("/get")
    public ResponseEntity<String> getApiTest() {
        return ResponseEntity.ok("Api- pass");
    }


    @GetMapping(value = "/schedules", produces = "application/json")
    List<TagRequest> all(@RequestHeader long userId) {
        return tagRequestService.all(userId);
    }

    @GetMapping(value = "/schedule/{id}", produces = "application/json")
    TagRequest get(@PathVariable String id, @RequestHeader long tagId) {
        return tagRequestService.get(id);
    }

    @PutMapping(value = "/schedule/{id}", produces = "application/json", consumes = "application/json")
    TagRequest update(@RequestBody TagRequest newTag, @RequestHeader long tagId, long userId) {
        return tagRequestService.update(newTag, tagId, userId);
    }
}