package com.example.telebot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Tag implements Serializable {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "name")
    private String name;

}
