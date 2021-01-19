package com.wgu_wemery.c196pa;

public class Course {

    public long courseId;
    public long courseTermID;
    public String courseTitle;
    public String courseStartDate;
    public String courseEndDate;
    public String status;
    public String mentorID;

    public Course() {
    }
    public Course(long courseId, long courseTermID, String courseTitle, String courseStartDate, String courseEndDate, String status,  String mentorID) {
        this.courseId = courseId;
        this.courseTermID = courseTermID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.status = status;
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

    public String getcourseStartDate() {
        return courseStartDate;
    }

    public void setcourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getcourseEndDate() {
        return courseEndDate;
    }

    public void setcourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMentorID() {
        return mentorID;
    }

    public void setMentorID(String mentorID) {
        this.mentorID = mentorID;
    }
}
