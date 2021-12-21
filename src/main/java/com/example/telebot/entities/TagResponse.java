package com.example.telebot.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class TagResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;

    public TagResponse(boolean success) {
        this.success = success;

    }

    public TagResponse(boolean success, String jobId, String jobGroup) {
        this.success = success;
        this.jobId = jobId;
        this.jobGroup = jobGroup;

    }

}
