package com.example.telebot.services;

import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SyncServiceImpl implements SyncService{
    private final UserService userService;

    private final TaskService taskService;

    private final ProjectService projectService;

    private final TodoistConnector connector;

    public SyncServiceImpl(@Lazy UserService userService, @Lazy TaskService taskService, @Lazy ProjectService projectService, TodoistConnector connector) {
        this.userService = userService;
        this.taskService = taskService;
        this.projectService = projectService;
        this.connector = connector;
    }

    @Override
    public void sync(long userId) throws IOException, ParseException {
        String syncToken = userService.getSyncTokenByUserId(userId);
        String token = userService.getToken(userId);
        String syncInfo = connector.partialSyncProjectsAndTasks(token, syncToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(syncInfo);

        List<Long> taskTodoistIds = taskService.getAllTodoistIds(userId);
        List<Long> projectTodoistIds = projectService.getAllTodoistIds(userId);

        syncToken = (String) object.get("sync_token");
        User user = userService.getUser(userId);
        user.setSyncToken(syncToken);
        userService.update(user, userId);
    }
}
