package com.example.telebot.dao;

import com.example.telebot.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDAO extends AbstractDAO<Project>{
    public ProjectDAO()
    {
        setClazz(Project.class);
    }
}
