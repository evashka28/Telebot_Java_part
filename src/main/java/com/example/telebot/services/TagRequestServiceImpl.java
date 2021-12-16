package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.dao.TagRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.telebot.quartz_try.payload.TagRequest;

import java.util.List;

@Service
public class TagRequestServiceImpl implements TagRequestService{
    private final TagRequestDAO tagRequestDAO;

    private final TagService tagService;

    @Autowired
    public TagRequestServiceImpl(TagRequestDAO tagRequestDAO, TagService tagService) {
        this.tagRequestDAO = tagRequestDAO;
        this.tagService = tagService;
    }


    @Override
    public TagRequest create(TagRequest tagRequest, long tagId, long userId) {
        Tag tag = tagService.get(tagId, userId);
        tagRequest.setTag(tag);
        return tagRequestDAO.save(tagRequest);
    }

    @Override
    public TagRequest update(TagRequest tagRequest, long tagId, long userId) {
        tagRequest.setTag(tagService.get(userId,tagId));
        return tagRequestDAO.update(tagRequest);
    }

    @Override
    public void delete(String id) {
        TagRequest tagRequest = tagRequestDAO.get(id);
        tagRequestDAO.delete(tagRequest);
    }

    @Override
    public TagRequest get(String id) {
        return tagRequestDAO.get(id);
    }

    @Override
    public List<TagRequest> all(long userId) {
        return tagRequestDAO.getAllByUserId(userId);
    }

}
