package com.example.telebot;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "users")
public class User implements Serializable {
    @Id
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "token")
    private String token;
    @Column(name = "sync_token")
    private String syncToken;



    public User(long id, String name, String token){
        this.id = id;
        this.token = token;
        this.name = name;
        this.syncToken = "*";
    }

    public User() {
        this.syncToken = "*";
    }

    public long getId() { return id; }

    public String getToken() {
        return token;
    }

    public String getName(){ return name; }


    @Override
    public String toString(){
        return "User{" + "id='" + this.id + "', name='" + this.name + "', projectId='"  +
                "', token='" + this.token + "'}";
    }
}
