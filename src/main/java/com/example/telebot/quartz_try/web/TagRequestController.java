package com.example.telebot.quartz_try.web;

import com.example.telebot.Tag;
import com.example.telebot.quartz_try.TagJob;
import com.example.telebot.quartz_try.payload.TagRequest;
import com.example.telebot.quartz_try.payload.TagResponse;
import com.example.telebot.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.telebot.services.UserService;
import com.example.telebot.services.TagRequestService;
import com.example.telebot.services.TagService;

import javax.validation.Valid;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@RestController
public class TagRequestController {


    private final Scheduler scheduler;

    private final UserService userService;

    private final TagRequestService tagRequestService;

    private final TagService tagService;

    @Autowired
    public TagRequestController(Scheduler scheduler, UserService userService, TagRequestService tagRequestService, TagService tagService) {
        this.scheduler = scheduler;
        this.userService = userService;
        this.tagRequestService = tagRequestService;
        this.tagService = tagService;
    }

    @PostMapping("/schedule/tag")
    public TagRequest scheduleTag(@Valid @RequestBody TagRequest tagRequest,   @RequestHeader long tagId, @RequestHeader long userId) throws SchedulerException {

        String zone = userService.getZone(userId);
        tagRequest=tagRequestService.create(tagRequest, tagId, userId);
        LocalTime time = tagRequest.getDateTime();
        String cronstr = "0 " + Integer.toString(time.getMinute()) + " " + Integer.toString(time.getHour()) + " ? * ";
        String days = tagRequest.getDaysOfWeek();
        String cronDay = cronstr + days;
        System.out.println(cronDay);
        JobDetail jobDetail = buildJobDetail(tagRequest, tagId, userId);
        Trigger trigger = buildTrigger(jobDetail, cronDay, zone);
        scheduler.scheduleJob(jobDetail, trigger);

        return tagRequest;

    }

    @DeleteMapping(value = "/schedule/{id}")
    void delete(@PathVariable String id, @RequestHeader long tagId) throws SchedulerException {

        scheduler.deleteJob(JobKey.jobKey(id, "tag-jobs"));
        tagRequestService.delete(id, tagId);

    }


    @GetMapping("/get")
    public ResponseEntity<String> getApiTest() {
        return ResponseEntity.ok("Api- pass");
    }

    private JobDetail buildJobDetail(TagRequest scheduleEmailRequest, long tagId, long userId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("userId", userId);
        jobDataMap.put("tagId", tagId);
        jobDataMap.put("daysOfWeek", scheduleEmailRequest.getDaysOfWeek());


        return JobBuilder.newJob(TagJob.class)
                .withIdentity(scheduleEmailRequest.getId(), "tag-jobs")
                .withDescription("Send tag")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

    }


    private Trigger buildTrigger(JobDetail jobDetail, String cronDay, String zone) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)

                .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                .withDescription("Send tag trigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronDay)
                        .inTimeZone(TimeZone.getTimeZone(zone)))
                .build();
    }

    @GetMapping(value = "/schedules", produces = "application/json")
    List<TagRequest> all(@RequestHeader long tagId) {
        return tagRequestService.all(tagId);
    }

    @GetMapping(value = "/schedule/{id}", produces = "application/json")
    TagRequest get(@PathVariable String id, @RequestHeader long tagId) {
        return tagRequestService.get(id, tagId);
    }

    @PutMapping(value = "/schedule/{id}", produces = "application/json", consumes = "application/json")
    TagRequest update(@RequestBody TagRequest newTag, @RequestHeader long tagId, long userId) {
        return tagRequestService.update(newTag, tagId, userId);
    }
}