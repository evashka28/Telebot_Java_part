package com.example.telebot;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "tasks")
public class Task implements Serializable {
    @Id
    private String id;
    @Column(name = "project_id")
    private String projectId;

    private String description;

    private String content;
    @Column(name = "favourite")
    private boolean favourite;
    @Column(name = "creation_datetime")
    private String creationDatetime;
    @Column(name = "last_access_datetime")
    private String lastAccessDatetime;


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

