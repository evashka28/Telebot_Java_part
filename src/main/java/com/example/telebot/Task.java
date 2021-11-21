package com.example.telebot;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "todoist_id")
    private long todoistId;
    @Transient
    private String description;
    @Transient
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "favourite")
    private boolean favourite;
    @Column(name = "creation_datetime")
    @JsonIgnore
    private Timestamp creationDatetime;
    @Column(name = "last_access_datetime")
    @JsonIgnore
    private Timestamp lastAccessDatetime;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "task_tag",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @JsonIgnore
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString()
    {
        return "{id:" + id + ", name:" + description + ", url:" + content + "}";
    }
}

