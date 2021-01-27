package com.wgu_wemery.c196pa;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.URI;
import java.util.ArrayList;

public class TermListActivity extends AppCompatActivity {

    public static final int TERM_EDITOR_ACTIVITY_CODE = 11111;
    public static final int TERM_VIEWER_ACTIVITY_CODE = 22222;

    DataProvider dataProvider;
    private CursorAdapter ca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setTitle("Terms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        String[] from = { DBOpenHelper.TERM_TITLE, DBOpenHelper.TERM_STARTDATE, DBOpenHelper.TERM_ENDDATE };
        int[] to = { R.id.termTitle, R.id.startDate, R.id.endDate };

        ca = new SimpleCursorAdapter(this, R.layout.content_term_list, null, from, to, 0);
        dataProvider = new DataProvider();

        ListView list = (ListView) findViewById(R.id.termsListView);
        list.setAdapter(ca);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TermListActivity.this, TermListActivity.class);
                Uri uri = Uri.parse(DataProvider.TERM_URI + "/" + id);
                startActivityForResult(intent, TERM_VIEWER_ACTIVITY_CODE);
            }
        });
    }

    private boolean sampleData() {
        Uri term1Uri = DataManager.insertTerm(this, "Spring 2020", "2020-01-01", "2020-06-30", 1);
        Uri term2Uri = DataManager.insertTerm(this, "Fall 2016", "2016-07-01", "2016-12-31", 0);
        Uri term3Uri = DataManager.insertTerm(this, "Spring 2017", "2017-01-01", "2017-06-30", 0);
        Uri term4Uri = DataManager.insertTerm(this, "Fall 2017", "2017-07-01", "2017-12-31", 0);
        Uri term5Uri = DataManager.insertTerm(this, "Spring 2018", "2018-01-01", "2018-06-30", 0);
        Uri term6Uri = DataManager.insertTerm(this, "Fall 2018", "2018-07-01", "2018-12-31", 0);

        Uri courseWGUUri = DataManager.insertCourse(this, Long.parseLong(term1Uri.getLastPathSegment()), "C196 - Mobile Development", "2020-01-01", "2020-02-01", "Jim Bob",
                "jim.bob@wgu.edu", "121-151-9999", "IN_PROGRESS");

        DataManager.insertCourse(this, Long.parseLong(term1Uri.getLastPathSegment()),
                "C179: Business of IT - Applications", "2016-02-01", "2016-03-01",
                "Course Mentor Group", "jim.bob@wgu.edu", "615-969-8888", "PAN_TO_TAKE");

        DataManager.insertCourse(this, Long.parseLong(term2Uri.getLastPathSegment()),
                "C195: Software II - Advanced Java Concepts", "2016-03-01", "2016-06-30",
                "Course Mentor Group", "", "cmprogramming@wgu.edu",
                "PLAN_TO_TAKE");

        DataManager.insertCourseNote(this, Long.parseLong(courseWGUUri.getLastPathSegment()),
                "This is a short test note");

        /*DataManager.insertCourseNote(this, Long.parseLong(courseWGUUri.getLastPathSegment()),
                getString(R.string.));*/

        Uri ass1Uri = DataManager.insertAssessment(this, Long.parseLong(courseWGUUri.getLastPathSegment()), "CLP1",
                "Mobile Application Development", "As a competent mobile application developer, your understanding of mobile application structure and design will help you to develop applications to meet customer requirements. The following project to develop a student scheduler/student progress tracking application, will help you to apply these skills in a familiar, real-world scenario. This task will allow you to demonstrate your ability to apply the skills learned in the course.\n" +
                        "\n" +
                        "You will develop a multiple-screen mobile application for WGU students to track their terms, courses associated with each term, and assessment(s) associated with each course. The application will allow students to enter, edit, and delete term, course, and assessment data. It should provide summary and detailed views of courses for each term and provide alerts for upcoming performance and objective assessments. This application will use a SQLite database.\n" +
                        "\n\n" +
                        "adding another line", "2016-10-01 02:30:00 PM");

        Uri ass2Uri = DataManager.insertAssessment(this, Long.parseLong(courseWGUUri.getLastPathSegment()), "ABC3",
                "Second Assessment, although this one has a name that won't fit on the grid",
                "Assessment Description", "2016-10-01 10:30:00 AM");


        return false;
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void termAdd(View view){
        Intent intent = new Intent(this, TermDetailActivity.class);
        intent.putExtra("com.ntlts.c196.ADD", true);
        startActivityForResult(intent,5);
    }



    public void onBack(View view) {
        Intent intent = new Intent(TermListActivity.this,  MainActivity.class);
        startActivity(intent);
    }

}
