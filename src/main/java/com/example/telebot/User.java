package com.example.telebot;

public class User {
    private final String id;
    private final String projectId;
    private final String token;
    private final String name;

    public User(String id, String projectId, String token, String name){
        this.id = id;
        this.projectId = projectId;
        this.token = token;
        this.name = name;
    }

    public String getId() { return id; }

    public String getProjectId() {
        return projectId;
    }

    public String getToken() {
        return token;
    }

    public String getName(){ return name; }

    @Override
    public String toString(){
        return "User{" + "id='" + this.id + "', name='" + this.name + "', projectId='" + this.projectId +
                "', token='" + this.token + "'}";
    }
}
