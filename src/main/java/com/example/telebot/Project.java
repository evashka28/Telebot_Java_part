package com.example.telebot;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class Project implements Serializable {

    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "todoist_id")
    private String todoistId;
    @Column(name = "favourite")
    private boolean favourite;
}
