package com.example.telebot;


import java.sql.Time;
import java.util.List;

public class Schedule {
    private List<Byte> daysOfWeek;
    private Time time;

    public List<Byte> getDaysOfWeek() {
        return daysOfWeek;
    }

    public Time getTime() {
        return time;
    }

    public void setDaysOfWeek(List<Byte> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
