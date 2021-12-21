package com.example.telebot.services;

import com.example.telebot.entities.TagRequest;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;

public interface TagRequestService {

    Trigger buildTrigger(JobDetail jobDetail, String cronDay, String zone);

    TagRequest create(TagRequest schedule, long tagId, long userId);

    TagRequest update(TagRequest schedule, long tagId, long userId);

    void delete(String shId) throws SchedulerException;

    TagRequest get(String id);

    List<TagRequest> all(long tagId);


    TagRequest scheduleTag(TagRequest tagRequest, long tagId, long userId) throws SchedulerException;
}
