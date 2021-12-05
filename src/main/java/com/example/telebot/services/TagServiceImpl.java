package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    private final TagDAO tagDAO;

    private final UserService userService;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, UserService userService) {
        this.tagDAO = tagDAO;
        this.userService = userService;
    }


    @Override
    public Tag create(Tag tag, long userId) {
        tag.setUser(userService.getById(userId));
        return tagDAO.save(tag);
    }

    @Override
    public Tag update(Tag tag, long userId) {
        tag.setUser(userService.getById(userId));
        return tagDAO.update(tag);
    }

    @Override
    public void delete(long tagId, long userId) {
        Tag tag = tagDAO.findById(tagId);
        tagDAO.delete(tag);
    }

    @Override
    public Tag get(long id, long userId) {
        return tagDAO.get(userId, id);
    }

    @Override
    public List<Tag> all(long userId) {
        return tagDAO.getAllByUserId(userId);
    }

    @Override
    public List<Tag> getMultipleByIds(List<Long> ids) {
        return tagDAO.getMultipleById(ids);
    }
}
