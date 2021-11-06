package com.example.telebot;

public class Task {
    private String id;
    private String description;
    private String content;
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

    public String toTodoistJSON(String projectId, String sectionId)
    {
        return "{ \"content\":\"" + content + "\", \"description\":\"" + description + "\"}";
    }
}

