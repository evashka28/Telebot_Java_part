package com.example.telebot.services;

import com.example.telebot.Tag;
import com.example.telebot.dao.TagRequestDAO;
import com.example.telebot.quartz_try.TagJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.telebot.quartz_try.payload.TagRequest;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

@Service
public class TagRequestServiceImpl implements TagRequestService{
    private final TagRequestDAO tagRequestDAO;

    private final TagService tagService;

    private final Scheduler scheduler;

    private final UserService userService;



    @Autowired
    public TagRequestServiceImpl(TagRequestDAO tagRequestDAO, TagService tagService, Scheduler scheduler, UserService userService) {
        this.tagRequestDAO = tagRequestDAO;
        this.tagService = tagService;
        this.scheduler = scheduler;
        this.userService = userService;
    }

    @Override
    public Trigger buildTrigger(JobDetail jobDetail, String cronDay, String zone) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)

                .withIdentity(jobDetail.getKey().getName(), "tag-trigger")
                .withDescription("Send tag trigger")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronDay)
                        .inTimeZone(TimeZone.getTimeZone(zone)))
                .build();
    }


    @Override
    public TagRequest create(TagRequest tagRequest, long tagId, long userId) {
        Tag tag = tagService.get(tagId, userId);
        tagRequest.setTag(tag);
        return tagRequestDAO.save(tagRequest);
    }

    @Override
    public TagRequest update(TagRequest tagRequest, long tagId, long userId) {
        tagRequest.setTag(tagService.get(userId,tagId));
        return tagRequestDAO.update(tagRequest);
    }

    @Override
    public void delete(String id) throws SchedulerException {
        scheduler.deleteJob(JobKey.jobKey(id, "tag-jobs"));
        TagRequest tagRequest = tagRequestDAO.get(id);
        tagRequestDAO.delete(tagRequest);
    }

    @Override
    public TagRequest get(String id) {
        return tagRequestDAO.get(id);
    }

    @Override
    public List<TagRequest> all(long userId) {
        return tagRequestDAO.getAllByUserId(userId);
    }

    @Override
    public TagRequest scheduleTag(TagRequest tagRequest, long tagId, long userId) throws SchedulerException {
        String zone = userService.getZone(userId);
        tagRequest = create(tagRequest, tagId, userId);
        LocalTime time = tagRequest.getDateTime();
        String cronstr = "0 " + time.getMinute() + " " + time.getHour() + " ? * ";
        String days = tagRequest.getDaysOfWeek();
        String cronDay = cronstr + days;
        System.out.println(cronDay);
        JobDetail jobDetail = buildJobDetail(tagRequest, tagId, userId);
        Trigger trigger = buildTrigger(jobDetail, cronDay, zone);
        scheduler.scheduleJob(jobDetail, trigger);
        return tagRequest;
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

}
