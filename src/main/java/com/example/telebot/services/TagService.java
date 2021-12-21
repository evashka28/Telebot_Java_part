package com.example.telebot.services;

import com.example.telebot.entities.Tag;

import java.util.List;

public interface TagService {

    Tag create(Tag tag, long userId);

    Tag update(Tag tag, long userId);

    void delete(long tagId, long userId);

    Tag get(long id, long userId);

    List<Tag> all(long userId);

    List<Tag> getMultipleByIds(List<Long> ids);
}
