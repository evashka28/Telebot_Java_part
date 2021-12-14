package com.example.telebot;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import com.example.telebot.quartz_try.payload.TagRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<TagRequest> schedule;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @PreRemove
    private void removeTags() {
        for(Task task: tasks) {
            task.removeTag(this);
        }
    }

    public Set<TagRequest> getSchedule() {
        return schedule;
    }

    public void setTags(Set<TagRequest> schedule) {
        this.schedule = schedule;
    }

}
