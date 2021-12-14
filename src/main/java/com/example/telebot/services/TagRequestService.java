package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.quartz_try.payload.TagRequest;

import java.util.List;

public interface TagRequestService {

    TagRequest create(TagRequest schedule, long tagId, long userId);

    TagRequest update(TagRequest schedule, long tagId, long userId);

    void delete(String shId, long tagId);

    TagRequest get(String id, long tagId);

    List<TagRequest> all(long tagId);


}
