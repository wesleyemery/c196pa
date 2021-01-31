package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

public class MainActivity extends AppCompatActivity {
    public final static String DETAILTYPE_EXTRA="com.wgu_wemery.c196pa.detailType";
    Button termsBtn, coursesBtn, assessBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        getSupportActionBar().setTitle("WGU Term Tracker");
        String []terms = {"Spring 2020","Fall 2016","Spring 2017","Fall 2017","Spring 2018","Fall 2018"};
        String []starts ={"2020-01-01","2016-07-01","2017-01-01","2017-07-01","2018-01-01","2018-07-01"};
        String []ends =  {"2020-06-30","2016-12-31","2017-06-30","2017-12-31","2018-06-30","2018-12-31"};
        int [] actives = {1,0,0,0,0,0};


        String[] courseTitle = {"C179: Business of IT - Applications","C195: Software II - Advanced Java Concepts"};
        String[] courseStartDate = {"2016-02-01","2016-03-01"};
        String[] courseEndDate = {"2016-03-01","2016-06-30"};
        String[] instructor = {"Course Mentor Group","Course Mentor Group"};
        String[] instructorEmail = {"jim.bob@wgu.edu",""};
        String[] instructorPhone = {"615-969-8888","cmprogramming@wgu.edu"};
        String[] courseDescription = {"",""};
        int[] termId = {1,1};
        String[] courseStatus = {"Plan to take","Plan to take"};


        new DatabaseHandler(this,terms,starts,ends,actives,courseTitle,courseStartDate,
                courseEndDate,instructor,instructorEmail,instructorPhone,courseDescription,termId,
                courseStatus);



        termsBtn = (Button) findViewById(R.id.termsBtn);
        coursesBtn = (Button) findViewById(R.id.courseBtn);
        assessBtn = (Button) findViewById(R.id.assessBtn);

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TermListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        coursesBtn.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,CourseListActivity.class));
        });

        assessBtn.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,AssessmentListActivity.class));
        });

    }


}