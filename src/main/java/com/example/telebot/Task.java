package com.example.telebot;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table (name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "todoist_id")
    private long todoistId;
    @Column(name = "project_id")
    private long projectId;
    @Transient
    private String description;
    @Transient
    private String content;

    @Column(name = "favourite")
    private boolean favourite;
    @Column(name = "creation_datetime")
    private Timestamp creationDatetime;
    @Column(name = "last_access_datetime")
    private Timestamp lastAccessDatetime;


    public Task(long id, String description, String content, boolean favourite){
        this.id = id;
        this.description = description;
        this.content = content;
        this.favourite = favourite;
    }

    public Task(){}

    public long getId(){
        return id;
    }

    public long getTodoistId() {
        return todoistId;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getDescription(){
        return description;
    }

    public String getContent(){
        return content;
    }

    public boolean getFavourite() { return favourite; }

    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public Timestamp getLastAccessDatetime() {
        return lastAccessDatetime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public void setTodoistId(long todoistId) {
        this.todoistId = todoistId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDatetime(Timestamp creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastAccessDatetime(Timestamp lastAccessDatetime) {
        this.lastAccessDatetime = lastAccessDatetime;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString()
    {
        return "{id:" + id + ", name:" + description + ", url:" + content + "}";
    }
}

