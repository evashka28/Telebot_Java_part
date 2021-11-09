package com.example.telebot;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "todoist_id")
    private long todoistId;
    @Column(name = "favourite")
    private boolean favourite;
    @Transient
    private String name;

    public Project(){}

    public Project(long id, long userId, long todoistId, boolean favourite) {
        this.id = id;
        this.userId = userId;
        this.todoistId = todoistId;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public long getTodoistId() {
        return todoistId;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTodoistId(long todoistId) {
        this.todoistId = todoistId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
