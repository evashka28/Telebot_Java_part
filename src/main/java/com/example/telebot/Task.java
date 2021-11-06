package com.example.telebot;
import javax.persistence.*;
@Entity
@Table (name = "tasks")
public class Task {
    @Id
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
    @Column(name = "favourite")
    private boolean favourite;


    public Task(String id, String description, String content, boolean favourite){
        this.id = id;
        this.description = description;
        this.content = content;
        this.favourite = favourite;
    }

    public Task(){}

    public String getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public String getContent(){
        return content;
    }

    public boolean getFavourite() { return favourite; }

    @Override
    public String toString()
    {
        return "{id:" + id + ", name:" + description + ", url:" + content + "}";
    }
}

