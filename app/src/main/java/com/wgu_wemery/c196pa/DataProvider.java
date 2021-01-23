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
    private static final String TABLE_COURSE = "courses";
    private static final String TABLE_COURSE_NOTES = "courseNotes";
    private static final String TABLE_ASSESSMENTS = "assessments";
    private static final String ALERT_BASE = "alert";

    //uri
    public static final Uri TERM_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_TERMS );
    public static final Uri COURSE_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_COURSE );
    public static final Uri COURSES_NOTES_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_COURSE_NOTES );
    public static final Uri ASSESSMENTS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ASSESSMENTS );
    public static final Uri ALERT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ALERT_BASE );

    //options
    private static final int TERM = 1;
    private static final int TERM_ID = 2;
    private static final int COURSE = 3;
    private static final int COURSE_ID = 4;
    private static final int COURSENOTE = 5;
    private static final int COURSENOTE_ID = 6;
    private static final int ASSESSMENT = 7;
    private static final int ASSESSMENT_ID = 8;
    private static final int ALERT = 9;
    private static final int ALERT_ID = 10;

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS, TERM);
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS + "/#", TERM_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSE, COURSE);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSE + "/#", COURSE_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSE_NOTES, COURSENOTE);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSE_NOTES + "/#", COURSENOTE_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS + "/#", ASSESSMENT);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS + "/#", ASSESSMENT_ID);
        uriMatcher.addURI(AUTHORITY, ALERT_BASE, ALERT);
        uriMatcher.addURI(AUTHORITY, ALERT_BASE + "/#", ALERT_ID);
    }

    private static final String TERMS_CONTENT_TYPE = "terms";
    private static final String COURSES_CONTENT_TYPE = "courses";
    private static final String COURSE_NOTES_CONTENT_TYPE = "courseNotes";
    private static final String ASSESSMENTS_CONTENT_TYPE = "assessments";
    private static final String ALERT_CONTENT_TYPE = "alert";

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query( Uri uri,  String[] strings,  String selection,  String[] strings1, String s1) {
        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.query(DBOpenHelper.TABLE_TERM, DBOpenHelper.TERM_ALL_COLUMNS, selection,
                        null, null, null, DBOpenHelper.TERM_TABLE_ID + " ASC");
            case COURSE:
                return db.query(DBOpenHelper.TABLE_COURSE, DBOpenHelper.COURSE_ALL_COLUMNS, selection,
                        null, null, null, null);
            case COURSE_ID:
                return db.query(DBOpenHelper.TABLE_COURSE, DBOpenHelper.COURSE_ALL_COLUMNS, DBOpenHelper.COURSE_TABLE_ID + "=" + uri.getLastPathSegment(),
                        null, null, null, DBOpenHelper.COURSE_TABLE_ID + " ASC" );
            case COURSENOTE:
                return   db.query(DBOpenHelper.TABLE_COURSENOTE, DBOpenHelper.COURSENOTE_ALL_COLUMNS, selection,
                        null, null, null, null);
            case ASSESSMENT:
                return   db.query(DBOpenHelper.TABLE_ASSESSMENT, DBOpenHelper.ASSESSMENT_ALL_COLUMNS, selection,
                        null, null, null, null);
            case ALERT:
                return db.query(DBOpenHelper.TABLE_ALERT, DBOpenHelper.ALERT_ALL_COLUMNS, selection,
                        null, null, null, null);
            default:
                throw new IllegalArgumentException("URI Unsupported: " + uri);
        }
   }

    @Nullable
    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri, ContentValues contentValues) {
        long id = 0;

        switch ( uriMatcher.match(uri)) {
            case TERM:
                id = db.insert(DBOpenHelper.TABLE_TERM, null, contentValues);
                return Uri.parse(TABLE_TERMS + "/" + id);
            case COURSE:
                id = db.insert(DBOpenHelper.TABLE_COURSE, null, contentValues);
                return Uri.parse(TABLE_COURSE + "/" + id);
            case COURSENOTE:
                id = db.insert(DBOpenHelper.TABLE_COURSENOTE, null, contentValues);
                return Uri.parse(TABLE_COURSE_NOTES + "/" + id);
            case ASSESSMENT:
                id = db.insert(DBOpenHelper.TABLE_ASSESSMENT, null, contentValues);
                return Uri.parse(TABLE_ASSESSMENTS + "/" + id);
            case ALERT:
                id = db.insert(DBOpenHelper.TABLE_ALERT, null, contentValues);
                return Uri.parse(ALERT_BASE + "/" + id);
            default:
                throw new IllegalArgumentException("URI Unsupported: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.delete(DBOpenHelper.TABLE_TERM, s, strings);
            case COURSE:
                return db.delete(DBOpenHelper.TABLE_COURSE, s, strings);
            case COURSENOTE:
                return db.delete(DBOpenHelper.TABLE_COURSENOTE, s, strings);
            case ASSESSMENT:
                return db.delete(DBOpenHelper.TABLE_ASSESSMENT, s, strings);
            case ALERT:
                return db.delete(DBOpenHelper.TABLE_ALERT, s, strings);
            default:
                throw new IllegalArgumentException("URI Unsupported: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch ( uriMatcher.match(uri)) {
            case TERM:
                return db.update(DBOpenHelper.TABLE_TERM, contentValues, s, strings);
            case COURSE:
                return db.update(DBOpenHelper.TABLE_COURSE, contentValues, s, strings);
            case COURSENOTE:
                return db.update(DBOpenHelper.TABLE_COURSENOTE, contentValues , s, strings);
            case ASSESSMENT:
                return db.update(DBOpenHelper.TABLE_ASSESSMENT, contentValues, s, strings);
            case ALERT:
                return db.update(DBOpenHelper.TABLE_ALERT, contentValues,s, strings);
            default:
                throw new IllegalArgumentException("URI Unsupported: " + uri);
        }
    }
}
