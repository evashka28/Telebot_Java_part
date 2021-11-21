package com.example.telebot;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "todoist_id")
    private long todoistId;
    @Column(name = "favourite")
    private boolean favourite;
    @Transient
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Project(){}

    public Project(long id, long todoistId, boolean favourite) {
        this.id = id;
        this.todoistId = todoistId;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public long getTodoistId() {
        return todoistId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
