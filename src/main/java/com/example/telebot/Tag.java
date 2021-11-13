package com.example.telebot;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    @JsonIgnore
    private long userId;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks;
}
