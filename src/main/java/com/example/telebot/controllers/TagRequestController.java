package com.example.telebot.controllers;

import com.example.telebot.entities.TagRequest;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.telebot.services.TagRequestService;

import javax.validation.Valid;

import java.util.List;

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