package com.example.telebot.quartz_try.payload;

import com.example.telebot.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class TagRequest implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id")
    private Tag tag;


    @Column(name = "daysOfWeek")
    String daysOfWeek;

    @NonNull
    @Column(name = "dateTime")
    private LocalTime dateTime;


}
