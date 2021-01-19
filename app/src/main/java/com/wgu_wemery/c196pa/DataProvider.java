package com.wgu_wemery.c196pa;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class DataProvider extends ContentProvider {

    private static final String AUTHORITY = "com.wgu_wemery.c196pa";

    private static final String TABLE_TERMS = "terms";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_COURSE_NOTES = "courseNotes";
    private static final String TABLE_ASSESSMENTS = "assessments";
    private static final String TABLE_ASSESSMENT_NOTES = "assessmentNotes";
    private static final String ALERT_BASE_PATH = "alert";


    //uri
    public static final Uri TERM_TERMS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_TERMS );
    public static final Uri TABLE_COURSES_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_COURSES );
    public static final Uri COURSES__NOTES_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_COURSE_NOTES );
    public static final Uri TABLE_ASSESSMENTS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ASSESSMENTS );
    public static final Uri TABLE_ASSESSMENT_NOTES_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ASSESSMENT_NOTES );
    public static final Uri ALERT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ALERT_BASE_PATH );

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //options
    private static final int TERM = 1;
    private static final int TERM_ID = 2;
    private static final int COURSE = 3;
    private static final int COURSE_ID = 4;
    private static final int MENTOR = 5;
    private static final int MENTOR_ID = 6;
    private static final int COURSENOTE = 7;
    private static final int COURSENOTE_ID = 8;
    private static final int ASSESSMENT = 9;
    private static final int ASSESSMENT_ID = 10;
    private static final int ALERT = 11;
    private static final int ALERT_ID = 12;

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS, TERM);
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS + "/#", TERM_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSES, COURSE);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSES + "/#", COURSE_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSE_NOTES, MENTOR);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS + "/#", MENTOR_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS, COURSENOTE);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS + "/#", COURSENOTE_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENT_NOTES, ASSESSMENT);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENT_NOTES + "/#", ASSESSMENT_ID);
        uriMatcher.addURI(AUTHORITY, ALERT_BASE_PATH, ALERT);
        uriMatcher.addURI(AUTHORITY, ALERT_BASE_PATH + "/#", ALERT_ID);

    }


    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {

        DBOpenHelper helper = new DBOpenHelper(getContext());
        db = helper.getWritableDatabase();

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.query(DBOpenHelper.TABLE_TERM, DBOpenHelper.TERM_ALL_COLUMNS, s,
                        null, null, null, null);
            case COURSE:
                return db.query(DBOpenHelper.TABLE_COURSE, DBOpenHelper.COURSE_ALL_COLUMNS, s,
                        null, null, null, null);
            case MENTOR:
                return db.query(DBOpenHelper.TABLE_MENTOR, DBOpenHelper.MENTOR_ALL_COLUMNS, s,
                        null, null, null, null);
            case COURSENOTE:
                return   db.query(DBOpenHelper.TABLE_COURSENOTE, DBOpenHelper.COURSENOTE_ALL_COLUMNS, s,
                        null, null, null, null);
            case ASSESSMENT:
                return   db.query(DBOpenHelper.TABLE_ASSESSMENT, DBOpenHelper.ASSESSMENT_ALL_COLUMNS, s,
                        null, null, null, null);
            case ALERT:
                return db.query(DBOpenHelper.TABLE_ALERT, DBOpenHelper.ALERT_ALL_COLUMNS, s,
                        null, null, null, null);
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = 0;

        switch ( uriMatcher.match(uri)) {
            case TERM:
                id = db.insert(DBOpenHelper.TABLE_TERM, null, contentValues);
                return Uri.parse(TABLE_TERMS + "/" + id);
            case COURSE:
                id = db.insert(DBOpenHelper.TABLE_COURSE, null, contentValues);
                return Uri.parse(TABLE_COURSES + "/" + id);
            case MENTOR:
                id = db.insert(DBOpenHelper.TABLE_MENTOR, null, contentValues);
                return Uri.parse(TABLE_COURSE_NOTES + "/" + id);
            case COURSENOTE:
                id = db.insert(DBOpenHelper.TABLE_COURSENOTE, null, contentValues);
                return Uri.parse(TABLE_ASSESSMENTS + "/" + id);
            case ASSESSMENT:
                id = db.insert(DBOpenHelper.TABLE_ASSESSMENT, null, contentValues);
                return Uri.parse(TABLE_ASSESSMENT_NOTES + "/" + id);
            case ALERT:
                id = db.insert(DBOpenHelper.TABLE_ALERT, null, contentValues);
                return Uri.parse(ALERT_BASE_PATH + "/" + id);
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.delete(DBOpenHelper.TABLE_TERM, s, strings);
            case COURSE:
                return db.delete(DBOpenHelper.TABLE_COURSE, s, strings);
            case MENTOR:
                return db.delete(DBOpenHelper.TABLE_MENTOR, s, strings);
            case COURSENOTE:
                return db.delete(DBOpenHelper.TABLE_COURSENOTE, s, strings);
            case ASSESSMENT:
                return db.delete(DBOpenHelper.TABLE_ASSESSMENT, s, strings);
            case ALERT:
                return db.delete(DBOpenHelper.TABLE_ALERT, s, strings);
            default:
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.update(DBOpenHelper.TABLE_TERM, contentValues, s, strings);
            case COURSE:
                return db.update(DBOpenHelper.TABLE_COURSE, contentValues, s, strings);
            case MENTOR:
                return db.update(DBOpenHelper.TABLE_MENTOR, contentValues, s, strings);
            case COURSENOTE:
                return db.update(DBOpenHelper.TABLE_COURSENOTE, contentValues , s, strings);
            case ASSESSMENT:
                return db.update(DBOpenHelper.TABLE_ASSESSMENT, contentValues, s, strings);
            case ALERT:
                return db.update(DBOpenHelper.TABLE_ALERT, contentValues,s, strings);
            default:
                break;
        }
        return 0;
    }
}
