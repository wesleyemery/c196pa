package com.wgu_wemery.c196pa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public final static String DETAILTYPE_EXTRA="com.wgu_wemery.c196pa.detailType";
    Button termsBtn, coursesBtn, assessBtn;
    DataProvider dataProvider = new DataProvider();
    DBOpenHelper database;



    public void onClick(View v) {

        int id = v.getId();

        switch(id){
            case R.id.termsBtn: {
                //Toast.makeText(this, "Terms pressed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TermListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.courseBtn: {
                Intent intent = new Intent(this, CourseListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.assessBtn: {
                //Toast.makeText(this, "Terms pressed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AssessmentListActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("WGU Term Tracker");
        termsBtn = (Button) findViewById(R.id.termsBtn);
        coursesBtn = (Button) findViewById(R.id.courseBtn);
        assessBtn = (Button) findViewById(R.id.assessBtn);

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                database = new DBOpenHelper(context);
                Intent intent = new Intent(view.getContext(), TermListActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }


}