package com.example.telebot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
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

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_tag",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @JsonIgnore
    private Set<Tag> tags;

    public Task(long id, String description, String content, boolean favourite) {
        this.id = id;
        this.description = description;
        this.content = content;
        this.favourite = favourite;
    }

    public Task() {
    }

    public long getId() {
        return id;
    }

    public long getTodoistId() {
        return todoistId;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public boolean getFavourite() {
        return favourite;
    }

    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public Timestamp getLastAccessDatetime() {
        return lastAccessDatetime;
    }

    public Set<Tag> getTags() {
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

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void addTag(Tag tag) {
        if (tags == null)
            tags = new HashSet<Tag>();
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"todoistId\":%d, \"description\":\"%s\", \"content\":\"%s\", \"favourite\":%s}",
                id, todoistId, description, content, favourite);
    }

    @PreRemove
    private void removeTags() {
        tags.clear();
    }
}

