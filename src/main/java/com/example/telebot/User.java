package com.example.telebot;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;


@Entity
@Table (name = "users")
public class User implements Serializable {
    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "token")
    private String token;

    @Column(name = "zone")
    private String zone;

    @JsonIgnore
    @Column(name = "sync_token")
    private String syncToken;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Project> projects;


    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Tag> tags;


    public User(long id, String name, String token, String zone){
        this.id = id;
        this.token = token;
        this.name = name;
        this.syncToken = "*";
        this.zone=zone;
    }

    public User() {
        this.syncToken = "*";
    }

    public long getId() { return id; }

    public String getToken() {return token;}

    public String getZone() {return zone;}

    public String getName(){ return name; }

    public String getSyncToken() {
        return syncToken;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(String zone) {
        this.zone = zone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSyncToken(String syncToken) {
        this.syncToken = syncToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return "User{" + "id='" + this.id + "', name='" + this.name + "', projectId='"  +
                "', token='" + this.token + "'}";
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
