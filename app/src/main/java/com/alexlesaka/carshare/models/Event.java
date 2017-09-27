package com.alexlesaka.carshare.models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by aabuin on 25/09/2017.
 */

public class Event
{
    private String id;
    private String name;
    private String from;
    private String to;
    private Date date;
    private String hour;
    private String usernamePlan;
    private String usernameDone;

    public Event(){}

    public Event(String id, String name, String from, String to, Date date, String hour, String usernamePlan, String usernameDone) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.date = date;
        this.hour = hour;
        this.usernamePlan = usernamePlan;
        this.usernameDone = usernameDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUsernamePlan() {
        return usernamePlan;
    }

    public void setUsernamePlan(String usernamePlan) {
        this.usernamePlan = usernamePlan;
    }

    public String getUsernameDone() {
        return usernameDone;
    }

    public void setUsernameDone(String usernameDone) {
        this.usernameDone = usernameDone;
    }
}
