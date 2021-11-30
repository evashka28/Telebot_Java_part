package com.example.telebot.quartz_try;

import com.example.telebot.services.TaskService;
import org.json.simple.parser.ParseException;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;

public class TagJob extends QuartzJobBean {

    private final TaskService taskService;

    @Autowired
    public TagJob(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        long UserId=jobDataMap.getLong("userId");
        long TagId = jobDataMap.getLong("tagId");
        try {
            sendTag(UserId, TagId);
        } catch (IOException e) {
           // e.printStackTrace();
        } catch (ParseException e) {
            //e.printStackTrace();
        }

    }
    private void sendTag( long UserId, long TagId) throws IOException, ParseException {
        // TaskService taskService = new TaskService();
        long i=taskService.getByTag(UserId, TagId).getId();
        System.out.printf("wow %d", i);

    }
}
