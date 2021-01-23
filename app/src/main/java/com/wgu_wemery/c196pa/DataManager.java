package com.wgu_wemery.c196pa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

class DataManager {

    //Terms
    public static Uri insertTerm(Context context, String termTitle, String termStart, String termEnd, int termActive) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.TERM_TITLE, termTitle);
        values.put(DBOpenHelper.TERM_STARTDATE, termStart);
        values.put(DBOpenHelper.TERM_ENDDATE, termEnd);
        values.put(DBOpenHelper.TERM_ACTIVE, termActive);
        Uri termUri = context.getContentResolver().insert(DataProvider.TERM_URI, values);
        return termUri;
    }

    public static Term getTerm(Context context, long id) {
        Cursor cursor = context.getContentResolver().query(DataProvider.TERM_URI, DBOpenHelper.TERM_ALL_COLUMNS, DBOpenHelper.TERM_TABLE_ID + "=" + id, null, null);

        cursor.moveToFirst();
        String termTitle = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_TITLE));
        String termStartDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_STARTDATE));
        String termEndDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_ENDDATE));
        int termActive = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.TERM_ACTIVE));

        Term t = new Term();
        t.termId = t.getTermId();
        t.termTitle = termTitle;
        t.termStartDate = termStartDate;
        t.termEndDate = termEndDate;
        t.current = termActive;
        return t;
    }

    public static Uri insertCourse(Context context, long termId, String courseName, String courseStart,
                                   String courseEnd, String courseMentor,  String status) {

        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COURSE_TERM_ID, termId);
        values.put(DBOpenHelper.COURSE_MENTOR_ID, courseMentor);
        values.put(DBOpenHelper.COURSE_TITLE, courseName);
        values.put(DBOpenHelper.COURSE_STARTDATE, courseStart);
        values.put(DBOpenHelper.COURSE_ENDDATE, courseEnd);
        values.put(DBOpenHelper.COURSE_STATUS, status);
        Uri courseUri = context.getContentResolver().insert(DataProvider.TABLE_COURSES_URI, values);
        return courseUri;
    }

    public static Course getCourse(Context context, long courseId) {
        Cursor cursor = context.getContentResolver().query(DataProvider.TABLE_COURSES_URI, DBOpenHelper.COURSE_ALL_COLUMNS, DBOpenHelper.COURSE_ID + "=" + courseId, null, null);

        cursor.moveToFirst();
        Course c = new Course();
        c.courseId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COURSE_ID));
        c.courseTermID = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COURSE_TERM_ID));
        c.mentorID = cursor.getString(cursor.getColumnIndex(DBOpenHelper.MENTOR_ID));
        c.courseTitle = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_TITLE));
        c.courseStartDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_STARTDATE));
        c.courseEndDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_ENDDATE));
        c.status = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_STATUS));
        return c;
    }

    public static boolean deleteCourse(Context context, long courseId) {
        Cursor notesCursor = context.getContentResolver().query(DataProvider.TABLE_COURSES_URI, DBOpenHelper.COURSENOTE_ALL_COLUMNS, DBOpenHelper.COURSENOTE_COURSE_ID + "=" + courseId, null, null );
        while (notesCursor.moveToNext()) {
            DataManager.deleteCourseNote(context, notesCursor.getLong(notesCursor.getColumnIndex(DBOpenHelper.COURSENOTE_ID)));
        }
        context.getContentResolver().delete(DataProvider.COURSES_NOTES_URI, DBOpenHelper.COURSE_ID + " = " + courseId, null);
        return true;
    }

    public static Uri insertCourseNote(Context context, long courseId, String text) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COURSENOTE_NOTE, text);
        values.put(DBOpenHelper.COURSENOTE_COURSE_ID, courseId);

        Uri courseUri = context.getContentResolver().insert(DataProvider.COURSES_NOTES_URI, values);
        Log.d("DataManager", "Inserted Course Note: " + courseUri.getLastPathSegment());

        return courseUri;
    }

    public static CourseNote getCourseNote(Context context, long courseNoteId) {
        Cursor cursor = context.getContentResolver().query(DataProvider.COURSES_NOTES_URI, DBOpenHelper.COURSENOTE_ALL_COLUMNS, DBOpenHelper.COURSENOTE_ID + "=" + courseNoteId, null, null);

        cursor.moveToFirst();
        CourseNote c = new CourseNote(courseNoteId);
        c.courseNoteNote = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSENOTE_NOTE));
        c.courseID = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.COURSENOTE_ID));
        return c;
    }

    public static boolean deleteCourseNote(Context context, long courseNoteId) {
        context.getContentResolver().delete(DataProvider.COURSES_NOTES_URI, DBOpenHelper.COURSENOTE_ID + " = " + courseNoteId, null);
        return true;
    }

    public static Assessment getAssessment(Context context, long assessmentId) {
        Cursor cursor = context.getContentResolver().query(DataProvider.TABLE_ASSESSMENTS_URI, DBOpenHelper.ASSESSMENT_ALL_COLUMNS, DBOpenHelper.ASSESSMENT_ID + "=" + assessmentId, null, null);

        cursor.moveToFirst();
        Assessment assessment = new Assessment(assessmentId);
        assessment.assessmentID = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_ID));
        assessment.courseID = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_COURSE_ID));
        assessment.assessmentName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_NAME));
        assessment.assessmentType = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_TYPE));
        assessment.assessmentDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_DATE));
        return assessment;
    }

    public static boolean deleteAssessment(Context context, long assessmentId) {
        Cursor notesCursor = context.getContentResolver().query(DataProvider.TABLE_ASSESSMENTS_URI, DBOpenHelper.ASSESSMENT_ALL_COLUMNS, DBOpenHelper.ASSESSMENT_ID + "=" + assessmentId, null, null );
        while (notesCursor.moveToNext()) {
            deleteAssessmentNote(context, notesCursor.getLong(notesCursor.getColumnIndex(DBOpenHelper.ASSESSMENT_ID)));
        }

        context.getContentResolver().delete(DataProvider.TABLE_ASSESSMENTS_URI, DBOpenHelper.ASSESSMENT_ID + " = " + assessmentId, null);
        return true;
    }

    public static Uri insertAssessment(Context context, long courseId, String id, String name, String type, String dateTime) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ASSESSMENT_COURSE_ID, courseId);
        values.put(DBOpenHelper.ASSESSMENT_ID, id);
        values.put(DBOpenHelper.ASSESSMENT_NAME, name);
        values.put(DBOpenHelper.ASSESSMENT_DATE, dateTime);
        values.put(DBOpenHelper.ASSESSMENT_TYPE, type);

        Uri assessmentUri = context.getContentResolver().insert(DataProvider.TABLE_ASSESSMENTS_URI, values);
        Log.d("DataManager", "Inserted Assessment: " + assessmentUri.getLastPathSegment());

        return assessmentUri;
    }
/*
    public static Uri insertAssessmentNote(Context context, long courseId, String text) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.ASSESSMENT_NOTE_TEXT, text);
        values.put(DBOpenHelper.ASSESSMENT_NOTE_ASSESSMENT_ID, courseId);

        Uri courseUri = context.getContentResolver().insert(DataProvider.ASSESSMENT_NOTE_URI, values);
        Log.d("DataManager", "Inserted Assessment Note: " + courseUri.getLastPathSegment());

        return courseUri;
    }
*/
    public static boolean deleteAssessmentNote(Context context, long assessmentNoteId) {
        context.getContentResolver().delete(DataProvider.TABLE_ASSESSMENTS_URI, DBOpenHelper.ASSESSMENT_ID + " = " + assessmentNoteId, null);
        return true;
    }
/*
    public static _AssessmentNote getAssessmentNote(Context context, long courseNoteId) {
        Cursor cursor = context.getContentResolver().query(DataProvider.ASSESSMENT_NOTE_URI, DBOpenHelper.ASSESSMENT_NOTE_COLUMNS, DBOpenHelper.ASSESSMENT_NOTE_TABLE_ID + "=" + courseNoteId, null, null);

        cursor.moveToFirst();
        _AssessmentNote c = new _AssessmentNote(courseNoteId);
        c.text = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_NOTE_TEXT));
        c.assessmentId = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_NOTE_ASSESSMENT_ID));
        return c;
    }


    public static _Image getImage(Context context, long imageId) {
        Cursor cursor = context.getContentResolver().query(DataProvider.IMAGE_URI, DBOpenHelper.IMAGE_COLUMNS, DBOpenHelper.IMAGE_TABLE_ID + "=" + imageId, null, null);

        cursor.moveToFirst();
        _Image c = new _Image(imageId);
        c.timeStamp = cursor.getLong(cursor.getColumnIndex(DBOpenHelper.IMAGE_TIMESTAMP));
        c.parentUri = Uri.parse(cursor.getString(cursor.getColumnIndex(DBOpenHelper.IMAGE_PARENT_URI)));
        return c;
    }

    public static Uri insertImage(Context context, long timestamp, Uri parentUri) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.IMAGE_TIMESTAMP, timestamp);
        values.put(DBOpenHelper.IMAGE_PARENT_URI, parentUri.toString());

        Uri courseUri = context.getContentResolver().insert(DataProvider.IMAGE_URI, values);
        Log.d("DataManager", "Inserted Image: " + courseUri.getLastPathSegment());

        return courseUri;
    }*/

}