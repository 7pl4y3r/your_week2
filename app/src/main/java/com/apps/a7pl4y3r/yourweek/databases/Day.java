package com.apps.a7pl4y3r.yourweek.databases;

import java.util.List;



public class Day {

    private String dayName;
    private List<Task> tasks;

    public Day() {
        //Required for Firestore
    }

    public Day(String dayName, List<Task> tasks) {
        this.dayName = dayName;
        this.tasks = tasks;
    }

    public String getDayName() {
        return dayName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
