package com.example.telebot;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "name")
    private String name;

}
