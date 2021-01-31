package com.wgu_wemery.c196pa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import java.util.Date;
import java.util.Locale;

public class DatabaseHandler {

    private Context context;
    private SQLiteDatabase database;
    private String DB_NAME = "c196pa_db";

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
            "CREATE TABLE IF NOT EXISTS " + TABLE_TERM + " (" +
                    TERM_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_TITLE + " TEXT, " +
                    TERM_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ENDDATE + " TEXT default CURRENT_TIMESTAMP," +
                    TERM_ACTIVE + " TEXT, " +
                    TERM_CREATED + " TEXT " +
                    ")";

    //SQL to create course table
    private static final String COURSE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COURSE + " (" +
                    COURSE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_TERM_ID + " INTEGER, " +
                    COURSE_DESCRIPTION + " TEXT, " +
                    COURSE_MENTOR + " TEXT, " +
                    COURSE_MENTOR_EMAIL + " TEXT, " +
                    COURSE_MENTOR_PHONE + " TEXT, " +
                    COURSE_STATUS + " TEXT, " +
                    COURSE_TITLE + " TEXT, " +
                    COURSE_STARTDATE + " TEXT default CURRENT_TIMESTAMP," +
                    COURSE_ENDDATE + " TEXT default CURRENT_TIMESTAMP" +
                    ")";


    //SQL to create CourseNote table
    private static final String COURSENOTE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_COURSENOTE + " (" +
                    COURSENOTE_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSENOTE_COURSE_ID + " INTEGER, " +
                    COURSENOTE_TEXT + " TEXT, " +
                    COURSE_NOTE_ATTACHMENT_URI + " TEXT, " +
                    COURSE_NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY(" + COURSENOTE_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COURSE_TABLE_ID + ")" +
                    ")";

    //SQL to create Assessment table
    private static final String ASSESSMENT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASSESSMENT + " (" +
                    ASSESSMENT_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_COURSE_ID + " INTEGER, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    ASSESSMENT_DATE + " TEXT," +
                    ASSESSMENT_DESCRIPTION + " TEXT, " +
                    ASSESSMENT_CREATED + " TEXT, " +
                    "FOREIGN KEY(" + ASSESSMENT_COURSE_ID + ") REFERENCES " + TABLE_COURSE + "(" + COURSE_TABLE_ID + ")" +
                    ")";

    //SQL to create Alert table
    private static final String ALERT_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ALERT + " (" +
                    ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ALERT_TYPE + " TEXT, " +
                    ALERT_DATE + " TEXT default CURRENT_TIMESTAMP," +
                    ALERT_COURSEID + " INTEGER " +
                    ")";


    String[] termNames;
    String[] startDates;
    String[] endDates;
    int[] active;

    //add constructor with context
    public DatabaseHandler(Context context) {
        this.context = context;
        database = context.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
        generateTables();
    }

    public DatabaseHandler(Context context, String[] termNames, String[] startDates,
                           String[] endDates, int[] active,
                           String[] courseTitle,String[] courseStartDate, String[] courseEndDate,
                           String[] instructor, String[] instructorEmail,String[] instructorPhone,
                           String[] courseDescription, int[] termId,String[] courseStatus) {
        this.context = context;
        database = context.openOrCreateDatabase(DB_NAME,Context.MODE_PRIVATE,null);
        generateTables();

        this.termNames = termNames;
        this.startDates = startDates;
        this.endDates = endDates;
        this.active = active;

        SharedPreferences myPref = context.getSharedPreferences("MY_PREFS",Context.MODE_PRIVATE);
        boolean isFirstRun = myPref.getBoolean("FIRST",true);
        if(isFirstRun){

            for (int i = 0; i < termNames.length; i++) {
                //Add term
                addTerm(
                        termNames[i],
                        startDates[i],
                        endDates[i],
                        active[i]
                );

            }

            for (int i = 0; i <courseTitle.length ; i++) {

                addCourse(courseTitle[i],courseStartDate[i],courseEndDate[i],instructor[i],instructorEmail[i],instructorPhone[i],courseDescription[i],termId[i],courseStatus[i]);

            }

            addAssessment(1,"Performance","Mobile Application Development","","As a competent mobile application developer, your understanding of mobile application structure and design will help you to develop applications to meet customer requirements. The following project to develop a student scheduler/student progress tracking application, will help you to apply these skills in a familiar, real-world scenario. This task will allow you to demonstrate your ability to apply the skills learned in the course.\n" +
                    "\n" +
                    "You will develop a multiple-screen mobile application for WGU students to track their terms, courses associated with each term, and assessment(s) associated with each course. The application will allow students to enter, edit, and delete term, course, and assessment data. It should provide summary and detailed views of courses for each term and provide alerts for upcoming performance and objective assessments. This application will use a SQLite database.\n" +
                    "\n\n" +
                    "adding another line",true);
            addAssessment(1,"Performance","Second Assessment, although this one has a name that won't fit on the grid","2016-10-01 10:30:00 AM","Assessment Description",false);

            myPref.edit().putBoolean("FIRST",false).apply();

        }


    }

    private void generateTables(){
        database.execSQL(TERM_TABLE_CREATE);
        database.execSQL(COURSE_TABLE_CREATE);
        database.execSQL(COURSENOTE_TABLE_CREATE);
        database.execSQL(ALERT_TABLE_CREATE);
        database.execSQL(ASSESSMENT_TABLE_CREATE);
    }
    public String timeStamp(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }
    //Add term
    public void addTerm(String termTitle, String termStart, String termEnd, int termActive){
        //Add query
        String addTermQuery = "INSERT into " + TABLE_TERM + " (" +
                TERM_TITLE+"," +
                TERM_STARTDATE+","+
                TERM_ENDDATE+","+
                TERM_ACTIVE+","+
                TERM_CREATED+
                ") VALUES (?,?,?,?,?)";
        //execute query
        database.execSQL(addTermQuery,new Object[]{termTitle,termStart,termEnd,termActive,timeStamp()});

    }
    //load terms
    public Cursor loadTerms(){
        //String for loading
        String loadTermsQuery = "SELECT * FROM "+TABLE_TERM;
        return database.rawQuery(loadTermsQuery,null);
    }

    public Cursor loadTerms(long id){
        //String for loading
        String loadTermsQuery = "SELECT * FROM "+TABLE_TERM+" WHERE _id = ?";
        return database.rawQuery(loadTermsQuery,new String[]{String.valueOf(id)});
    }

    //delete term
    public boolean deleteTerm(int id){
        boolean isDeleted = false;
        //check if courses exist
        String checkCourses = "SELECT * FROM "+
                TABLE_COURSE+
                " WHERE "+
                COURSE_TERM_ID+"= ?";
        Cursor courses = database.rawQuery(checkCourses,new String[]{String.valueOf(id)});
        if(courses.getCount()<1){
            //remove term
            String deleteTermQuery = "DELETE from "+TABLE_TERM+" WHERE "+TERM_TABLE_ID+"=?";
            database.execSQL(deleteTermQuery,new Object[]{id});
            isDeleted = true;
        }
        courses.close();
        return isDeleted;
    }
    //check course count in term
    public int courseCount(int id){
        int count = 0;
        //check if courses exist
        String checkCourses = "SELECT * FROM "+
                TABLE_COURSE+
                " WHERE "+
                COURSE_TERM_ID+"= ?";
        Cursor courses = database.rawQuery(checkCourses,new String[]{String.valueOf(id)});
        count = courses.getCount();
        courses.close();
        return count;
    }

    //add term to course
    public void addCourse(String courseTitle,String courseStartDate, String courseEndDate,
                          String instructor, String instructorEmail,String instructorPhone,
                          String courseDescription, int termId,String status){
        String addCourseQuery = "INSERT into "+TABLE_COURSE+" VALUES (?,?,?,?,?,?,?,?,?,?)";
        database.execSQL(addCourseQuery,new Object[]{null,termId,courseDescription,instructor,instructorEmail,instructorPhone,status,courseTitle,courseStartDate,courseEndDate});
    }
    //load all courses
    public Cursor loadCourses(){
        //String for loading
        String loadTermsQuery = "SELECT * FROM "+TABLE_COURSE;
        return database.rawQuery(loadTermsQuery,null);
    }

    //update course
    public void updateCourse(String courseTitle,String courseStartDate, String courseEndDate,
                             String instructor, String instructorEmail,String instructorPhone,
                             String courseDescription, int courseId,String status){

        String updateCourseQuery = "UPDATE "+TABLE_COURSE+" SET "+COURSE_DESCRIPTION+"= ?,"
                +COURSE_MENTOR+"=?,"
                +COURSE_MENTOR_EMAIL+"=?,"
                +COURSE_MENTOR_PHONE+"=?,"
                +COURSE_STATUS+"=?,"
                +COURSE_TITLE+"=?,"
                +COURSE_STARTDATE+"=?,"
                +COURSE_ENDDATE+"=? WHERE "
                +COURSE_TABLE_ID+"=?";
        Log.d("ID",""+courseId);
        database.execSQL(updateCourseQuery,new Object[]{courseDescription,instructor,instructorEmail,instructorPhone,status,courseTitle,courseStartDate,courseEndDate,courseId});


    }

    //add assessment
    public void addAssessment(int courseId,String type,String name, String date, String description,boolean addAlert){
        String addQuery = "INSERT into assessment VALUES (?,?,?,?,?,?,?)";
        database.execSQL(addQuery,new Object[]{null,courseId,type,name,date,description,timeStamp()});
        if(addAlert){
            database.execSQL("INSERT into alert VALUES (?,?,?,?)",new Object[]{null,"Assessment Reminder",date,courseId});
        }
    }

    public Cursor loadAssessment(long id){
        //String for loading
        String loadTermsQuery = "SELECT * FROM "+TABLE_ASSESSMENT+" WHERE course_id = ?";
        return database.rawQuery(loadTermsQuery,new String[]{String.valueOf(id)});
    }

    public Cursor loadAssessment(){
        String loadAssessment = "SELECT * FROM assessment INNER JOIN  course ON course._id = assessment.course_id";
        return database.rawQuery(loadAssessment,null);
    }

    public void updateAssessment(int assessmentId,String type,String name, String date, String description,boolean addAlert){
        String addQuery = "UPDATE assessment SET assessmentType = ?, assessmentName = ?, assessmentDate = ?, description = ?  WHERE _id = ?";
        database.execSQL(addQuery,new Object[]{type,name,date,description,assessmentId});
    }

    public void deleteCourse(int courseId){
        String dropTable = "DELETE FROM course WHERE _id = ?";
        String dropAssessments = "DELETE FROM assessment WHERE course_id = ?";
        database.execSQL(dropTable,new Object[]{courseId});
        database.execSQL(dropAssessments,new Object[]{courseId});
    }

    public void deleteAssessment(int courseId){
        String dropAssessments = "DELETE FROM assessment WHERE _id = ?";
        database.execSQL(dropAssessments,new Object[]{courseId});
    }

    public void addCourseNote(int course_id, String text, String uri){
        String addNote = "INSERT into courseNote VALUES (?,?,?,?,?)";
        database.execSQL(addNote,new Object[]{null,course_id,text,uri,timeStamp()});
    }

    public Cursor retreiveNotes(int id){
        String retrieveNote = "SELECT * FROM courseNote WHERE course_id = ?";
        return database.rawQuery(retrieveNote,new String[]{String.valueOf(id)});
    }

}
