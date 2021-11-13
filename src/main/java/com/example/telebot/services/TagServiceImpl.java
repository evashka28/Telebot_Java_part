package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }


    @Override
    public Tag create() {
        return null;
    }

    @Override
    public Tag update() {
        return null;
    }

    @Override
    public Tag delete() {
        return null;
    }

    @Override
    public Tag get() {
        return null;
    }

    @Override
    public Tag all() {
        return null;
    }
}
