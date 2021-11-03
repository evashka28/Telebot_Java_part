package com.example.telebot;

public class Task {
    private String id;
    private String description;
    private String content;
    private boolean favourite;
    private TaskType type;


    public Task(String id, String description, String content, boolean favourite, TaskType type){
        this.id = id;
        this.description = description;
        this.content = content;
        this.favourite = favourite;
        this.type = type;
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

    public TaskType getType() { return type; }

    @Override
    public String toString()
    {
        return "{id:" + id + ", name:" + description + ", url:" + content + "}";
    }
}

