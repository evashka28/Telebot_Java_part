package com.example.telebot.services;

import com.example.telebot.TodoistConnector;
import com.example.telebot.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SyncServiceImpl implements SyncService {
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

        if (object.containsKey("projects")) {
            List<Long> projectTodoistIds = projectService.getAllTodoistIds(userId);
            syncProjects((JSONArray) object.get("projects"), projectTodoistIds, userId);
        }

        if (object.containsKey("items")) {
            List<Long> projectTodoistIds = projectService.getAllTodoistIds(userId);
            List<Long> taskTodoistIds = taskService.getAllTodoistIds(userId);
            syncTasks((JSONArray) object.get("items"), taskTodoistIds, projectTodoistIds, userId);
        }

        if (object.containsKey("sync_token")) {
            syncToken = (String) object.get("sync_token");
            User user = userService.getUser(userId);
            user.setSyncToken(syncToken);
            userService.update(user, userId);
        }
    }

    private void syncProjects(JSONArray projectsSyncInfo, List<Long> projectTodoistIds, long userId) {
        for (int i = 0; i < projectsSyncInfo.size(); i++) {
            JSONObject projectObject = (JSONObject) projectsSyncInfo.get(i);
            if (projectObject.containsKey("id")) {
                Long todoistId = (Long) projectObject.get("id");
                if (projectTodoistIds.contains(todoistId)
                        && projectObject.containsKey("is_deleted")
                        && (Long) projectObject.get("is_deleted") == 1) {
                    //delete project
                    projectService.deselect(projectService.getByTodoistId(todoistId, userId).getId());
                }
            }
        }
    }

    private void syncTasks(JSONArray tasksSyncInfo, List<Long> taskTodoistIds, List<Long> projectTodoistIds, long userId) {
        for (int i = 0; i < tasksSyncInfo.size(); i++) {
            JSONObject taskObject = (JSONObject) tasksSyncInfo.get(i);
            if (taskObject.containsKey("id")) {
                Long todoistId = (Long) taskObject.get("id");
                if ((taskObject.containsKey("is_deleted")
                        && (Long) taskObject.get("is_deleted") == 1) ||
                        (taskObject.containsKey("checked")
                                && (Long) taskObject.get("checked") == 1)) {
                    if (taskTodoistIds.contains(todoistId)) {
                        //delete task
                        System.out.println("syncDelete");

                        taskService.deleteTaskFromBD(taskService.getByTodoistId(todoistId, userId).getId());
                    }
                    continue;

                }

                if (!taskTodoistIds.contains(todoistId) && taskObject.containsKey("project_id")) {
                    Long projectTodoistId = (Long) taskObject.get("project_id");
                    if (projectTodoistIds.contains(projectTodoistId)) {
                        System.out.println("syncAdd");
                        taskService.addTaskToDB(taskObject, projectService.getByTodoistId(projectTodoistId, userId));
                    }
                }
            }
        }
    }

    private void deleteProject() {
    }

    private void deleteTask() {
    }

    private void createTask() {
    }
}
