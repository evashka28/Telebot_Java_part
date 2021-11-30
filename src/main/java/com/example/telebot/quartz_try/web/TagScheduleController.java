package com.example.telebot.quartz_try.web;

import com.example.telebot.quartz_try.TagJob;
import com.example.telebot.quartz_try.payload.TagRequest;
import com.example.telebot.quartz_try.payload.TagResponse;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.telebot.services.UserService;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
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

        ZonedDateTime dateTime= ZonedDateTime.of(tagRequest.getDateTime(), ZoneId.of(userService.getZone(tagRequest.getUserId())));
        JobDetail jobDetail = buildJobDetail(tagRequest);
        Trigger trigger = buildTrigger(jobDetail, dateTime);
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

        return JobBuilder.newJob(TagJob.class)
                .withIdentity(UUID.randomUUID().toString(), "tag-jobs")
                .withDescription("Send tag")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                .withDescription("Send tag trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

    /*private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt){
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                    .withDescription("Send tag trigger")
                    .withSchedule(cronSchedule("0 30 10-13 ? * WED,FRI")) //at 10:30, 11:30, 12:30, and 13:30, on every Wednesday and Friday.
                    .inTimeZone(TimeZone.getTimeZone(userService.getZone(tagRequest.getUserId())))
                    .forJob(myJobKey)
                    .build();
        }*/
    }
}
