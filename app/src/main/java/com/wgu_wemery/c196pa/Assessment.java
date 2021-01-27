package com.wgu_wemery.c196pa;

public class Assessment {
    public String assessmentID;
    public Long courseID;
    public String assessmentType;
    public String assessmentName;
    public String assessmentDate;
    public String description;

    public Assessment(String assessmentID, Long courseID, String assessmentType, String assessmentName, String assessmentDate) {
        this.assessmentID = assessmentID;
        this.courseID = courseID;
        this.assessmentType = assessmentType;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
    }
    public Assessment(Long courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(String assessmentID) {
        this.assessmentID = assessmentID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
