package com.example.telebot;

//import org.json.simple.JSONOblect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

//класс содержит методы с запросами к todoist
public class TodoistConnector {

    //input и output запросов в формате json
    // get-запроc возвращает проекты пользователя
    public void getUsersProjects(String token) throws IOException {
        URL url = new URL("https://api.todoist.com/rest/v1/projects");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
    }

    //post-запрос создаёт новый проект
    public void createProject(String token, String projectName) throws IOException    {
        URL url = new URL("https://api.todoist.com/rest/v1/projects");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setDoOutput(true);

        String jsonInput = "{\"name\": \"" + projectName + "\"}";

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
    }

    //get-запрос получает задание по id
    public Task getTask(String token, long taskId) throws IOException, ParseException {
        URL url = new URL("https://api.todoist.com/rest/v1/tasks/" + taskId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        return parseTaskJSON(response.toString());
    }

    //get-запрос получает несколько задач по id
    public List<Task> getAllTasks(String token, List<Long> ids) throws IOException, ParseException {
        StringBuffer idsString = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            idsString.append(ids.get(i));
            if(i < ids.size() - 1)
                idsString.append(",");
        }

        URL url = new URL("https://api.todoist.com/rest/v1/tasks?ids=" + idsString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        return parseAllTasksJSON(response.toString());
    }

    //post-запрос создаёт новое задание в проекте
    public void createTask(String token, String taskName, String description , long projectId) throws IOException {
        URL url = new URL("https://api.todoist.com/rest/v1/tasks");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setDoOutput(true);

        String jsonInput = "{\"content\": \"" + taskName + "\" , \"description\": \"" + description + "\" , \"project_id\": " + projectId + "}";

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
    }

    //post-запрос обновляет информацию о задаче по id
    public void updateTask(String token, String taskName, String description , long id) throws IOException {
        URL url = new URL("https://api.todoist.com/rest/v1/tasks/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setDoOutput(true);

        String jsonInput = "{\"content\": \"" + taskName + "\" , \"description\": \"" + description  + "\"}";

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
    }

    //post-запрос завершает указанное задание
    public void completeTask(String token, long taskId) throws IOException {
        URL url = new URL("https://api.todoist.com/rest/v1/tasks/" + taskId + "/close");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
    }

    //delete-запрос удаляет указанное задание
    public void deleteTask(String token, long taskId) throws IOException {
        URL url = new URL("https://api.todoist.com/rest/v1/tasks/" + taskId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);
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

    public static Task taskFromJSONObject(JSONObject object){
        if(object.containsKey("id") && object.containsKey("description") && object.containsKey("content"))
            return new Task(object.get("id").toString(),object.get("description").toString(),object.get("content").toString());
        return null;
    }
}
