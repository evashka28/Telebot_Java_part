package com.example.telebot.entities;

import com.example.telebot.entities.Tag;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;


@Entity
@Table(name = "tag_requests")
public class TagRequest implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;


    @Column(name = "days_of_week")
    String daysOfWeek;

    @NonNull
    @Column(name = "datetime")
    private LocalTime dateTime;


    public TagRequest() {
    }

    public String getId() {
        return id;
    }

    public Tag getTag() {
        return tag;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateTime(LocalTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
