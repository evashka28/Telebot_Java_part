package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    @Override
    public Tag create(Tag tag, long userId) {
        tag.setUserId(userId);
        return tagDAO.save(tag);
    }

    @Override
    public Tag update(Tag tag, long userId) {
        tag.setId(userId);
        return tagDAO.update(tag);
    }

    @Override
    public void delete(Tag tag, long userId) {
        tag.setId(userId);
        tagDAO.delete(tag);
    }

    @Override
    public Tag get(long id, long userId) {
        return tagDAO.findById(id);
    }

    @Override
    public List<Tag> all(long userId) {
        return null;
    }
}
