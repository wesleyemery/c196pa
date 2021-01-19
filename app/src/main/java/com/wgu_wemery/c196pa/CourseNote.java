package com.wgu_wemery.c196pa;

public class CourseNote {
    public Long courseNoteID;
    public String courseNoteNote;
    public Long courseID;

    public CourseNote() {

    }
    public CourseNote(String courseNoteNote, Long courseID) {
        this.courseNoteNote = courseNoteNote;
        this.courseID = courseID;
    }

    public CourseNote(long courseNoteId) {
    }

    public Long getCourseNoteID() {
        return courseNoteID;
    }

    public void setCourseNoteID(Long courseNoteID) {
        this.courseNoteID = courseNoteID;
    }

    public String getCourseNoteNote() {
        return courseNoteNote;
    }

    public void setCourseNoteNote(String courseNoteNote) {
        this.courseNoteNote = courseNoteNote;
    }

    public Long getCourseID() {
        return courseID;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "CourseNote{" +
                "courseNoteNote='" + courseNoteNote + '\'' +
                '}';
    }
}
