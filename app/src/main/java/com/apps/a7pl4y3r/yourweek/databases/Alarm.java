package com.apps.a7pl4y3r.yourweek.databases;

public class Alarm {

    String name;
    String day;
    String month;
    String year;
    String hour;
    String minute;

    public Alarm() {
        //Required for Firestore
    }

    public Alarm(String name, String day, String month, String year, String hour, String minute) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
