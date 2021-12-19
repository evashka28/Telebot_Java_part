package com.example.telebot.quartz_try;

import com.example.telebot.BotConnector;
import com.example.telebot.Task;
import com.example.telebot.services.TaskService;
import com.example.telebot.BotConnector;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


import javax.persistence.NoResultException;
import java.io.IOException;

@Slf4j
public class TagJob extends QuartzJobBean {

    private final TaskService taskService;
    private final BotConnector botConnector;

    @Autowired
    public TagJob(TaskService taskService, BotConnector botConnector) {
        this.taskService = taskService;
        this.botConnector = botConnector;
    }


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        long userId = jobDataMap.getLong("userId");
        long tagId = jobDataMap.getLong("tagId");
        try {
            sendTag(userId, tagId);
        } catch (IOException | ParseException e) {
             log.error(e.getMessage() + " " + e.getStackTrace().toString());
        }

    }

    private void sendTag(long UserId, long TagId) throws IOException, ParseException, NoResultException {
        // TaskService taskService = new TaskService();
        Task outputTask = taskService.getByTag(UserId, TagId);
        botConnector.sendTask(outputTask, UserId);
    }
}
