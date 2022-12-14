package com.example.mindfull.Models;

public class ExerciseRecord {

    String uId;
    String repName;
    String repCount;
    String date;
    String repId;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    String month;

    public String getRepId() {
        return repId;
    }

    public void setRepId(String repId) {
        this.repId = repId;
    }

    public ExerciseRecord(String uId, String repName, String repCount, String date,String month) {
        this.uId = uId;
        this.repName = repName;
        this.repCount = repCount;
        this.date = date;
        this.month = month;
    }

    public ExerciseRecord(String uId, String repName,String repCount) {
        this.repName = repName;
        this.repCount = repCount;
    }

    public  ExerciseRecord() {

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getRepCount() {
        return repCount;
    }

    public void setRepCount(String repCount) {
        this.repCount = repCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }
}
