package com.example.telebot;
import javax.persistence.*;
@Entity
@Table (name = "users")
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private final String id;
    @Column(name = "name")
    private final String name;
    @Column(name = "token")
    private final String token;

    //private final String projectId;
    //private final String projectFavouriteId;



    public User(String id, String name, String token){
        this.id = id;
        //this.projectId = projectId;
       // this.projectFavouriteId = projectFavouriteId;
        this.token = token;
        this.name = name;
    }

    public String getId() { return id; }

   // public String getProjectId() {
    //    return projectId;
    //}

    public String getToken() {
        return token;
    }

    public String getName(){ return name; }

   // public String getProjectFavouriteId() { return projectFavouriteId; }

    @Override
    public String toString(){
        return "User{" + "id='" + this.id + "', name='" + this.name + "', projectId='"  +
                "', token='" + this.token + "'}";
    }
}
