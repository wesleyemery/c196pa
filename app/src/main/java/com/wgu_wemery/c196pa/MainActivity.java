package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "Lifecycle";
    public final static String DETAILTYPE_EXTRA="com.wgu_wemery.c196pa.detailType";
    Button termsBtn, coursesBtn, assessBtn;
    DBOpenHelper database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("WGU Term Tracker");
        Log.d(TAG, "onCreate");
        termsBtn = (Button) findViewById(R.id.termsBtn);
        coursesBtn = (Button) findViewById(R.id.courseBtn);
        assessBtn = (Button) findViewById(R.id.assessBtn);

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                database = new DBOpenHelper(context);
                Intent intent = new Intent(v.getContext(), TermListActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }




}