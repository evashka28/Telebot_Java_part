package com.example.telebot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {
    public static String mapToString(Map<String, Object> params){
        StringBuffer result = new StringBuffer();
        boolean first = true;
        for(Map.Entry<String, Object> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        return result.toString();
    }

    public static Task parseTaskJSON(String JSONString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(JSONString);
        Task outputTask = taskFromJSONObject(object);
        return outputTask;
    }

    public static List<Task> parseAllTasksJSON(String JSONString) throws ParseException {
        JSONParser parser = new JSONParser();
        List<Task> outputList = new ArrayList<Task>();
        JSONArray array = (JSONArray) parser.parse(JSONString);
        for (int i = 0; i < array.size(); i++){
            outputList.add(taskFromJSONObject((JSONObject) array.get(i)));
        }
        return outputList;
    }

    public static List<Project> parseAllProjectsJSON(String JSONString){
        return null;
    }

    public static Task taskFromJSONObject(JSONObject object){
        if(object.containsKey("id") && object.containsKey("description") && object.containsKey("content"))
            return new Task(object.get("id").toString(),object.get("description").toString(),object.get("content").toString(), false);
        return null;
    }

    public static Project projectFromJSONObject(JSONObject object){
        return null;
    }
}
