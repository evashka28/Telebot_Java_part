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
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.*;

//класс содержит методы с запросами к todoist
public class TodoistConnector {

    public String GETRequest(String URLString, Map<String, Object> params, String token) throws IOException {
        String paramsString = Converter.mapToString(params);
        URL url = new URL(URLString + paramsString);
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

        return response.toString();
    }

    //public void POSTRequest() {}

    //input и output запросов в формате json
    // get-запроc возвращает проекты пользователя
    public void getUsersProjects(String token, String syncToken) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sync_token", syncToken);
        params.put("resource_types", "[\"projects\"]");
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //get-запрос получает проект по id
    public Task getProject(String token, long projectId) throws IOException, ParseException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("project_id", projectId);
        String response = GETRequest("https://api.todoist.com/sync/v8/projects/get?", params, token);
        return null;
    }

    //post-запрос создаёт новый проект
    public void createProject(String token, String projectName) throws IOException    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //params.put("sync_token", "gC00ZIYB51_eCo1_mmhTT4x9RexLm5jQG2GamTqL5Au_tcVhD5C4af8B5Ikq63rC3vpZZoEFzkJ2VaxXdowwiL8BbhefU02ftQQ5dO_KzL2J1Q");
        params.put("commands", String.format("[{\"type\":\"project_add\",\"temp_id\":\"%s\"," +
                "\"uuid\":\"%s\",\"args\":{\"name\":\"%s\"}}]", UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), projectName));
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //get-запрос получает задание по id
    public Task getTask(String token, long taskId) throws IOException, ParseException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("item_id", taskId);
        params.put("all_data", false);
        String response = GETRequest("https://api.todoist.com/sync/v8/items/get?", params, token);
        return null;
    }

    //get-запрос получает несколько задач по id
    public List<Task> getAllTasks(String token, List<Long> ids) throws IOException, ParseException {
        return null;
    }

    //post-запрос создаёт новое задание в проекте
    public void createTask(String token, String taskName, String description , long projectId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_add\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"project_id\":\"%d\",\"content\":\"%s\",\"description\":\"%s\"}}]",
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), projectId, taskName, description));
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //post-запрос обновляет информацию о задаче по id
    public void updateTask(String token, String taskName, String description , long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_update\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\",\"content\":\"%s\",\"description\":\"%s\"}}]",
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), taskId, taskName, description));
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //post-запрос завершает указанное задание
    public void completeTask(String token, long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_complete\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\"}}]",
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), taskId));
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //delete-запрос удаляет указанное задание
    public void deleteTask(String token, long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_delete\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\"}}]",
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), taskId));
        String response = GETRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }


}
