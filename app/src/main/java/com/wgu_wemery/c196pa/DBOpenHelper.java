package com.wgu_wemery.c196pa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    // Database Information
    private static final String DATABASE_NAME = "wgu.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Terms
    public static final String TABLE_TERM = "term";
    public static final String TERM_ID = "_id";
    public static final String TERM_TITLE = "termTitle";
    public static final String TERM_STARTDATE = "termStartDate";
    public static final String TERM_ENDDATE = "termEndDate";
    public static final String TERM_ACTIVE = "active";
    public static final String TERM_CREATED = "_created";
    public static final String[] TERM_ALL_COLUMNS = {TERM_ID, TERM_TITLE, TERM_STARTDATE, TERM_ENDDATE, TERM_ACTIVE, TERM_CREATED};

    // Table: Course
    public static final String TABLE_COURSE = "course";
    public static final String COURSE_ID = "_id";
    public static final String COURSE_TERM_ID = "term_id";
    public static final String COURSE_MENTOR_ID = "mentor_id";
    public static final String COURSE_TITLE = "courseTitle";
    public static final String COURSE_STARTDATE = "courseStartDate";
    public static final String COURSE_ENDDATE = "courseEndDate";
    public static final String COURSE_STATUS = "courseStatus";
    public static final String[] COURSE_ALL_COLUMNS = {COURSE_ID, COURSE_TERM_ID, COURSE_MENTOR_ID, COURSE_TITLE, COURSE_STARTDATE, COURSE_ENDDATE, COURSE_STATUS};

    // Table: Mentor
    public static final String TABLE_MENTOR = "mentor";
    public static final String MENTOR_ID = "_id";
    public static final String MENTOR_NAME = "mentorName";
    public static final String MENTOR_PHONE = "mentorPhone";
    public static final String MENTOR_EMAIL = "mentorEmail";
    public static final String[] MENTOR_ALL_COLUMNS = {MENTOR_ID, MENTOR_NAME, MENTOR_PHONE, MENTOR_EMAIL};

    // Table: CourseNote
    public static final String TABLE_COURSENOTE = "courseNote";
    public static final String COURSENOTE_ID = "_id";
    public static final String COURSENOTE_COURSE_ID = "course_id";
    public static final String COURSENOTE_NOTE = "courseNoteNote";
    public static final String[] COURSENOTE_ALL_COLUMNS = {COURSENOTE_ID, COURSENOTE_NOTE, COURSENOTE_COURSE_ID};

    // Table: Assessment
    public static final String TABLE_ASSESSMENT = "assessment";
    public static final String ASSESSMENT_ID = "_id";
    public static final String ASSESSMENT_COURSE_ID = "course_id";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_DATE = "assessmentDate";
    public static final String[] ASSESSMENT_ALL_COLUMNS = {ASSESSMENT_ID, ASSESSMENT_COURSE_ID, ASSESSMENT_TYPE, ASSESSMENT_NAME, ASSESSMENT_DATE};

    // Table: Alert
    public static final String TABLE_ALERT = "alert";
    public static final String ALERT_ID = "_id";
    public static final String ALERT_TYPE = "alertType";
    public static final String ALERT_DATE = "alertDate";
    public static final String ALERT_COURSEID = "course_id";
    public static final String[] ALERT_ALL_COLUMNS = {ALERT_ID, ALERT_TYPE, ALERT_DATE, ALERT_COURSEID};

    //SQL to create term table
    private static final String TERM_TABLE_CREATE =
            "CREATE TABLE " + TABLE_TERM + " (" +
                    TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_TITLE + " TEXT, " +
                    TERM_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ENDDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ACTIVE + " TEXT, " +
                    TERM_CREATED + " TEXT " +
                    ")";

    //SQL to create course table
    private static final String COURSE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSE + " (" +
                    COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_TERM_ID + " INTEGER, " +
                    COURSE_MENTOR_ID + " INTEGER, " +
                    COURSE_TITLE + " TEXT, " +
                    COURSE_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    COURSE_ENDDATE + " TEXT default CURRENT_TIMESTAMP," +
                    COURSE_STATUS + " TEXT " +
                    ")";

    //SQL to create CourseMentor table
    private static final String MENTOR_TABLE_CREATE =
            "CREATE TABLE " + TABLE_MENTOR + " (" +
                    MENTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MENTOR_NAME + " TEXT, " +
                    MENTOR_PHONE + " TEXT, " +
                    MENTOR_EMAIL + " TEXT " +
                    ")";

    //SQL to create CourseNote table
    private static final String COURSENOTE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSENOTE + " (" +
                    COURSENOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSENOTE_NOTE + " TEXT, " +
                    COURSENOTE_COURSE_ID + " INTEGER " +
                    ")";

    //SQL to create Assessment table
    private static final String ASSESSMENT_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ASSESSMENT + " (" +
                    ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_COURSE_ID + " INTEGER, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    ASSESSMENT_DATE + " TEXT " +
                    ")";

    //SQL to create Alert table
    private static final String ALERT_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ALERT + " (" +
                    ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALERT_TYPE + " TEXT, " +
                    ALERT_DATE + " TEXT default CURRENT_TIMESTAMP," +
                    ALERT_COURSEID + " INTEGER " +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TERM_TABLE_CREATE);
        db.execSQL(MENTOR_TABLE_CREATE);
        db.execSQL(COURSE_TABLE_CREATE);
        db.execSQL(COURSENOTE_TABLE_CREATE);
        db.execSQL(ALERT_TABLE_CREATE);
        db.execSQL(ASSESSMENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSENOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        onCreate(db);
    }

}