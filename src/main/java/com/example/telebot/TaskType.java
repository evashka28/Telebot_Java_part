package com.example.telebot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskType {
    UNKNOWN("t1"),
    READ("t2"),
    WATCH("t3"),
    LISTEN("t4");

    private String type;

    private TaskType(String type){
        this.type = type;
    }

//    @JsonCreator()
//    public static TaskType fromText(String text){
//        for(TaskType type : TaskType.values()){
//            System.out.println(type.toString() + " " + text);
//            if(type.toString().equals(text))
//                return type;
//        }
//        return TaskType.UNKNOWN;
//    }

    //@JsonValue()
    public String getType(){
        return type;
    }
}
