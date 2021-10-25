package com.example.telebot;

public class User {
    private final String id;
    private final String projectId;
    private final String token;

    public User(String id, String projectId, String token){
        this.id = id;
        this.projectId = projectId;
        this.token = token;
    }

    public String getId() { return id; }

    public String getProjectId() {
        return projectId;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString(){
        return "User{" + "id='" + this.id + "', projectId='" + this.projectId +
                "', token='" + this.token + "'}";
    }
}
