package com.example.telebot;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {
    public static String mapToString(Map<String, Object> params) {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
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
        if (object.containsKey("item")) {
            JSONObject task = (JSONObject) object.get("item");

            Task outputTask = taskFromJSONObject(task);
            return outputTask;
        }
        return null;
    }

    public static List<Task> parseMultipleTasksJSON(String JSONString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(JSONString);
        if (object.containsKey("items")) {
            List<Task> outputList = new ArrayList<Task>();
            JSONArray array = (JSONArray) object.get("items");
            for (int i = 0; i < array.size(); i++) {
                outputList.add(taskFromJSONObject((JSONObject) array.get(i)));
            }
            return outputList;
        }
        return null;
    }

    public static List<Project> parseMultipleProjectsJSON(String JSONString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(JSONString);
        if (object.containsKey("projects")) {
            List<Project> outputList = new ArrayList<Project>();
            JSONArray array = (JSONArray) object.get("projects");
            for (int i = 0; i < array.size(); i++) {
                outputList.add(projectFromJSONObject((JSONObject) array.get(i)));
            }
            return outputList;
        }
        return null;
    }

    public static Project parseProjectJSON(String JSONString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(JSONString);

        if (object.containsKey("project")) {
            JSONObject project = (JSONObject) object.get("project");

            Project outputProject = projectFromJSONObject(project);
            return outputProject;
        }
        return null;
    }

    public static Task taskFromJSONObject(JSONObject task) {
        if (task.containsKey("id") && task.containsKey("description") && task.containsKey("content")) {
            Task output = new Task();
            output.setTodoistId((long) task.get("id"));
            output.setContent(task.get("content").toString());
            output.setDescription(task.get("description").toString());
            return output;
        }
        return null;
    }

    public static Project projectFromJSONObject(JSONObject project) {
        if (project.containsKey("id") && project.containsKey("name")) {
            Project output = new Project();
            output.setTodoistId((long) project.get("id"));
            output.setName(project.get("name").toString());
            return output;
        }
        return null;
    }

    public static MutablePair<Long, String> parseProjectOrTaskCreation(String input, String tempId) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(input);
        MutablePair<Long, String> output = new MutablePair<Long, String>();
        if (object.containsKey("temp_id_mapping")) {
            output.setLeft(Long.parseLong(((JSONObject) object.get("temp_id_mapping")).get(tempId).toString()));
        }
        if (object.containsKey("sync_token")) {
            output.setRight(object.get("sync_token").toString());
        }
        return output;
    }
}
