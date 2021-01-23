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
    public static final String TERM_TABLE_ID = "_id";
    public static final String TERM_TITLE = "termTitle";
    public static final String TERM_STARTDATE = "termStartDate";
    public static final String TERM_ENDDATE = "termEndDate";
    public static final String TERM_ACTIVE = "active";
    public static final String TERM_CREATED = "_created";
    public static final String[] TERM_ALL_COLUMNS = {TERM_TABLE_ID, TERM_TITLE, TERM_STARTDATE, TERM_ENDDATE, TERM_ACTIVE, TERM_CREATED};

    // Table: Course
    public static final String TABLE_COURSE = "course";
    public static final String COURSE_TABLE_ID = "_id";
    public static final String COURSE_TERM_ID = "term_id";
    public static final String COURSE_MENTOR = "mentor";
    public static final String COURSE_MENTOR_PHONE = "mentorPhone";
    public static final String COURSE_MENTOR_EMAIL = "mentorEmail";
    public static final String COURSE_TITLE = "courseTitle";
    public static final String COURSE_DESCRIPTION = "description";
    public static final String COURSE_STARTDATE = "courseStartDate";
    public static final String COURSE_ENDDATE = "courseEndDate";
    public static final String COURSE_STATUS = "courseStatus";
    public static final String[] COURSE_ALL_COLUMNS = {COURSE_TABLE_ID, COURSE_TERM_ID, COURSE_MENTOR, COURSE_MENTOR_PHONE, COURSE_MENTOR_EMAIL, COURSE_TITLE, COURSE_DESCRIPTION, COURSE_STARTDATE, COURSE_ENDDATE, COURSE_STATUS};


    // Table: CourseNote
    public static final String TABLE_COURSENOTE = "courseNote";
    public static final String COURSENOTE_TABLE_ID = "_id";
    public static final String COURSENOTE_COURSE_ID = "course_id";
    public static final String COURSENOTE_TEXT = "courseNoteText";
    public static final String COURSE_NOTE_ATTACHMENT_URI = "uri";
    public static final String COURSE_NOTE_CREATED = "_created";
    public static final String[] COURSENOTE_ALL_COLUMNS = {COURSENOTE_TABLE_ID, COURSENOTE_TABLE_ID, COURSENOTE_COURSE_ID, COURSENOTE_TEXT, COURSE_NOTE_ATTACHMENT_URI, COURSE_NOTE_CREATED };

    // Table: Assessment
    public static final String TABLE_ASSESSMENT = "assessment";
    public static final String ASSESSMENT_TABLE_ID = "_id";
    public static final String ASSESSMENT_COURSE_ID = "course_id";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_DATE = "assessmentDate";
    public static final String ASSESSMENT_DESCRIPTION = "description";
    public static final String ASSESSMENT_CREATED = "_created";
    public static final String[] ASSESSMENT_ALL_COLUMNS = {ASSESSMENT_TABLE_ID, ASSESSMENT_COURSE_ID, ASSESSMENT_TYPE, ASSESSMENT_NAME, ASSESSMENT_DATE, ASSESSMENT_DESCRIPTION, ASSESSMENT_CREATED};

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
                    TERM_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_TITLE + " TEXT, " +
                    TERM_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ENDDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ACTIVE + " TEXT, " +
                    TERM_CREATED + " TEXT " +
                    ")";

    //SQL to create course table
    private static final String COURSE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSE + " (" +
                    COURSE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_TERM_ID + " INTEGER, " +
                    COURSE_DESCRIPTION + " TEXT, " +
                    COURSE_MENTOR + " TEXT, " +
                    COURSE_MENTOR_EMAIL + " TEXT, " +
                    COURSE_MENTOR_PHONE + " TEXT, " +
                    COURSE_STATUS + " TEXT, " +
                    COURSE_TITLE + " TEXT, " +
                    COURSE_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    COURSE_ENDDATE + " TEXT default CURRENT_TIMESTAMP," +
                    COURSE_STATUS + " TEXT " +
                    ")";


    //SQL to create CourseNote table
    private static final String COURSENOTE_TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSENOTE + " (" +
                    COURSENOTE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSENOTE_COURSE_ID + " INTEGER, " +
                    COURSENOTE_TEXT + " TEXT, " +
                    COURSE_NOTE_ATTACHMENT_URI + " TEXT, " +
                    COURSE_NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(" + COURSENOTE_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COURSE_TABLE_ID + ")" +
                    ")";

    //SQL to create Assessment table
    private static final String ASSESSMENT_TABLE_CREATE =
            "CREATE TABLE " + TABLE_ASSESSMENT + " (" +
                    ASSESSMENT_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_COURSE_ID + " INTEGER, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    ASSESSMENT_DATE + " TEXT " +
                    ASSESSMENT_DESCRIPTION + " TEXT " +
                    ASSESSMENT_CREATED + " TEXT " +
                    "FOREIGN KEY(" + ASSESSMENT_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COURSE_TABLE_ID + ")" +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM);
        onCreate(db);
    }

}