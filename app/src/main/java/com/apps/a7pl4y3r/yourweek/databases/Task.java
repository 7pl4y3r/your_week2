package com.apps.a7pl4y3r.yourweek.databases;

public class Task {

    //val startHour: String?, val startMinute: String?, val endHour: String?, val endMinute: String?, val task: String?
    String startHour;
    String startMinute;
    String endHour;
    String endMinute;
    String task;


    public Task() {
        //Required for Firestore
    }

    public Task(String startHour, String startMinute, String endHour, String endMinute, String task) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.task = task;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public String getTask() {
        return task;
    }
}
