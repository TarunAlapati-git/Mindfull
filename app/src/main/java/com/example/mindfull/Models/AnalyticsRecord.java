package com.example.mindfull.Models;

public class AnalyticsRecord {

    String Day,Month,repCount;

    public AnalyticsRecord(String day, String month, String repCount) {
        Day = day;
        Month = month;
        this.repCount = repCount;
    }

    public  AnalyticsRecord() {

    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getRepCount() {
        return repCount;
    }

    public void setRepCount(String repCount) {
        this.repCount = repCount;
    }
}
