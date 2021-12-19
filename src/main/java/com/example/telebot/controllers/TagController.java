package com.example.telebot.controllers;

import com.example.telebot.Tag;
import com.example.telebot.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/tags", produces = "application/json")
    List<Tag> all(@RequestHeader long userId) {
        return tagService.all(userId);
    }

    @GetMapping(value = "/tag/{id}", produces = "application/json")
    Tag get(@PathVariable long id, @RequestHeader long userId) {
        return tagService.get(id, userId);
    }

    @PostMapping(value = "/tag", produces = "application/json", consumes = "application/json")
    Tag create(@RequestBody Tag newTag, @RequestHeader long userId) {
        return tagService.create(newTag, userId);
    }

    @PutMapping(value = "/tag/{id}", produces = "application/json", consumes = "application/json")
    Tag update(@RequestBody Tag newTag, @RequestHeader long userId) {
        return tagService.update(newTag, userId);
    }

    @DeleteMapping(value = "/tag/{id}")
    void delete(@PathVariable long id, @RequestHeader long userId) {
        tagService.delete(id, userId);
    }
}
