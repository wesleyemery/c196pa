package com.wgu_wemery.c196pa;

public class Course {

    public long courseId;
    public long courseTermID;
    public String courseTitle;
    public String courseStartDate;
    public String courseEndDate;
    public String courseMentor;
    public String courseMentorEmail;
    public String courseMentorPhone;
    public String status;

    public Course() {
    }

    public Course(long courseId, long courseTermID, String courseTitle, String courseStartDate, String courseEndDate, String status, String courseMentor, String courseMentorEmail, String courseMentorPhone) {
        this.courseId = courseId;
        this.courseTermID = courseTermID;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseMentor = courseMentor;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhone = courseMentorPhone;
        this.status = status;
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

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) { this.courseStartDate = courseStartDate; }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseMentor() {
        return courseMentor;
    }

    public void setCourseMentor(String courseMentor) {
        this.courseMentor = courseMentor;
    }

    public String getCourseMentorEmail() { return courseMentorEmail; }

    public void setCourseMentorEmail(String courseMentorEmail) { this.courseMentorEmail = courseMentorEmail; }

    public String getCourseMentorPhone() { return courseMentorPhone; }

    public void setCourseMentorPhone(String courseMentorPhone) { this.courseMentorPhone = courseMentorPhone; }

}
