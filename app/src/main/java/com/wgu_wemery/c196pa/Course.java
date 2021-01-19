package com.wgu_wemery.c196pa;

public class Course {

    private long courseId;
    private long courseTermID;
    private String courseTitle;
    private String startDate;
    private String endDate;
    private String status;
    private String mentorName;
    private String mentorID;


    public Course(long courseId, long courseTermID, String courseTitle, String startDate, String endDate, String status, String mentorName, String mentorID) {
        this.courseId = courseId;
        this.courseTermID = courseTermID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorID = mentorID;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getCourseTermID() {
        return courseTermID;
    }

    public void setCourseTermID(long courseTermID) {
        this.courseTermID = courseTermID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorID() {
        return mentorID;
    }

    public void setMentorID(String mentorID) {
        this.mentorID = mentorID;
    }
}
