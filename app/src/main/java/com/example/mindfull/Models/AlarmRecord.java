package com.example.mindfull.Models;

public class AlarmRecord {

    int timeSlept;
    String date;

    public AlarmRecord(int timeSlept, String date) {
        this.timeSlept = timeSlept;
        this.date = date;
    }

    public  AlarmRecord() {

    }

    public int getTimeSlept() {
        return timeSlept;
    }

    public void setTimeSlept(int timeSlept) {
        this.timeSlept = timeSlept;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
