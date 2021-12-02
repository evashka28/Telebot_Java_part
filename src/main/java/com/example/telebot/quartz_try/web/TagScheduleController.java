package com.example.telebot.quartz_try.web;

import com.example.telebot.quartz_try.TagJob;
import com.example.telebot.quartz_try.payload.TagRequest;
import com.example.telebot.quartz_try.payload.TagResponse;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.telebot.services.UserService;

import javax.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@RestController
public class TagScheduleController {


    private final Scheduler scheduler;

    private final UserService userService;

    @Autowired
    public TagScheduleController(Scheduler scheduler, UserService userService) {
        this.scheduler = scheduler;
        this.userService = userService;
    }

    @PostMapping("/schedule/tag")
    public ResponseEntity<TagResponse> scheduleTag(@Valid @RequestBody TagRequest tagRequest) throws SchedulerException {

        String zone= userService.getZone(tagRequest.getUserId());
        LocalTime time = tagRequest.getDateTime();
        String cronstr="0 "+Integer.toString(time.getMinute())+" "+Integer.toString(time.getHour())+" ? * ";
        String days1 = tagRequest.getDaysOfWeek().toString();
        days1 = days1.substring(1, days1.length()-1);
        String days = days1.replaceAll(" ", "");
        String cronDay= cronstr + days;
        System.out.println(cronDay);
        JobDetail jobDetail = buildJobDetail(tagRequest);
        Trigger trigger = buildTrigger(jobDetail, cronDay, zone);
        scheduler.scheduleJob(jobDetail, trigger);

        TagResponse tagResponse = new TagResponse(true, jobDetail.getKey().getName(),jobDetail.getKey().getGroup());
        return ResponseEntity.ok(tagResponse);

    }

    @GetMapping("/get")
    public ResponseEntity<String> getApiTest(){
        return ResponseEntity.ok("Api- pass");
    }

    private JobDetail buildJobDetail(TagRequest scheduleEmailRequest){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId",scheduleEmailRequest.getUserId());
        jobDataMap.put("tagId",scheduleEmailRequest.getTagId());
        jobDataMap.put("daysOfWeek",scheduleEmailRequest.getDaysOfWeek());



        return JobBuilder.newJob(TagJob.class)
                .withIdentity(UUID.randomUUID().toString(), "tag-jobs")
                .withDescription("Send tag")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

    }

    /*private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                .withDescription("Send tag trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();*/

    private Trigger buildTrigger(JobDetail jobDetail, String cronDay, String zone){
            return TriggerBuilder.newTrigger()
            //Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                    .withDescription("Send tag trigger")
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cronDay) //at 10:30, 11:30, 12:30, and 13:30, on every Wednesday and Friday.
                            .inTimeZone(TimeZone.getTimeZone(zone)))
                    .build();
        }
    }

