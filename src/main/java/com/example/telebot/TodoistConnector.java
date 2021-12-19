package com.example.telebot;

//import org.json.simple.JSONOblect;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.logging.Logger;

//класс содержит методы с запросами к todoist
@Component
@Slf4j
public class TodoistConnector {

    public String POSTRequest(String URLString, Map<String, Object> params, String token) throws IOException {
        String paramsString = Converter.mapToString(params);
        URL url = new URL(URLString + paramsString);
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

        log.info(String.valueOf(response));


        return response.toString();
    }

    //public void POSTRequest() {}

    //input и output запросов в формате json
    // get-запроc возвращает проекты пользователя
    public String getUsersProjects(String token, String syncToken) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sync_token", syncToken);
        params.put("resource_types", "[\"projects\"]");
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }

    //get-запрос получает проект по id
    public String getProject(String token, long projectTodoistId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("project_id", projectTodoistId);
        String response = POSTRequest("https://api.todoist.com/sync/v8/projects/get?", params, token);
        return response;
    }

    //get-запрос получает проект по id
    public String getProjectAndTasks(String token, long projectTodoistId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("project_id", projectTodoistId);
        String response = POSTRequest("https://api.todoist.com/sync/v8/projects/get_data?", params, token);
        return response;
    }

    //post-запрос создаёт новый проект
    public String createProject(String token, String projectName, String tempId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //params.put("sync_token", "gC00ZIYB51_eCo1_mmhTT4x9RexLm5jQG2GamTqL5Au_tcVhD5C4af8B5Ikq63rC3vpZZoEFzkJ2VaxXdowwiL8BbhefU02ftQQ5dO_KzL2J1Q");
        params.put("commands", String.format("[{\"type\":\"project_add\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"name\":\"%s\"}}]", tempId,
                UUID.randomUUID(), projectName));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }

    //post-запрос обновляет название проекта
    public String updateProject(String token, String projectName) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        //params.put("sync_token", "gC00ZIYB51_eCo1_mmhTT4x9RexLm5jQG2GamTqL5Au_tcVhD5C4af8B5Ikq63rC3vpZZoEFzkJ2VaxXdowwiL8BbhefU02ftQQ5dO_KzL2J1Q");
        params.put("commands", String.format("[{\"type\":\"project_update\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"name\":\"%s\"}}]", UUID.randomUUID(),
                UUID.randomUUID(), projectName));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }

    //get-запрос получает задание по id
    public String getTask(String token, long taskTodoistId) throws IOException, ParseException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("item_id", taskTodoistId);
        params.put("all_data", false);
        String response = POSTRequest("https://api.todoist.com/sync/v8/items/get?", params, token);
        return response;
    }

    //get-запрос получает несколько задач по id
    public String getAllTasks(String token, List<Long> ids) throws IOException, ParseException {
        return null;
    }

    //post-запрос создаёт новое задание в проекте
    public String createTask(String token, String taskName, String description, long projectId, String tempId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_add\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"project_id\":\"%d\",\"content\":\"%s\",\"description\":\"%s\"}}]",
                tempId, UUID.randomUUID(), projectId, taskName, description));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }

    //post-запрос обновляет информацию о задаче по id
    public String updateTask(String token, String taskName, String description, long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_update\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\",\"content\":\"%s\",\"description\":\"%s\"}}]",
                UUID.randomUUID(), UUID.randomUUID(), taskId, taskName, description));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }

    //post-запрос завершает указанное задание
    public void completeTask(String token, long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_complete\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\"}}]",
                UUID.randomUUID(), UUID.randomUUID(), taskId));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    //delete-запрос удаляет указанное задание
    public void deleteTask(String token, long taskId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"item_delete\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\"}}]",
                UUID.randomUUID(), UUID.randomUUID(), taskId));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    public void deleteProject(String token, long projectId) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("commands", String.format("[{\"type\":\"project_delete\",\"temp_id\":\"%s\"," +
                        "\"uuid\":\"%s\",\"args\":{\"id\":\"%d\"}}]",
                UUID.randomUUID(), UUID.randomUUID(), projectId));
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
    }

    public String partialSyncProjectsAndTasks(String token, String syncToken) throws IOException {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sync_token", syncToken);
        params.put("resource_types", "[\"projects\",\"items\"]");
        String response = POSTRequest("https://api.todoist.com/sync/v8/sync?", params, token);
        return response;
    }


}
