package com.alexlesaka.carshare.models;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Alex on 11/9/17.
 */

public class Repetition
{
    public boolean monday = false;
    public boolean tuesday = false;
    public boolean wednesday = false;
    public boolean thursday = false;
    public boolean friday = false;
    public boolean saturday = false;
    public boolean sunday = false;

    public Date fromDate;
    public Date toDate;
    public String hour;


    public Repetition(){}

    public Repetition(boolean monday, boolean tuesday, boolean wednesday, boolean thursday,
                      boolean friday, boolean saturday, boolean sunday, Date fromDate,
                      Date toDate, String hour) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.hour = hour;
    }

    public boolean getMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean getThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean getFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean getSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
