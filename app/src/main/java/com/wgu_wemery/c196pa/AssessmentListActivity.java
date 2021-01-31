package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wgu_wemery.c196pa.models.Assessment;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {

    private ListView assessmentLists;
    private Assessment myAssessment;
    private ArrayList<String> arrayList;
    private DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        handler = new DatabaseHandler(this);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        getSupportActionBar().setTitle("YOUR ASSESSMENTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        initUI();
    }

    private void initUI(){
        assessmentLists = findViewById(R.id.assessments);
        arrayList = new ArrayList<>();
        List<Assessment> assessments = new ArrayList<>();
        Cursor myCursor = handler.loadAssessment();
        if(myCursor.moveToFirst()){
            do {
                String fullText = "";
                myAssessment = new Assessment();
                int id = myCursor.getInt(0);
                int course = myCursor.getInt(1);
                String type = myCursor.getString(2);
                String name = myCursor.getString(3);
                String date = myCursor.getString(4);
                String description = myCursor.getString(5);
                String created = myCursor.getString(6);

                fullText += "ASSESSMENT TYPE: "+type+"\n";
                fullText += "ASSESSMENT TITLE: "+name+"\n";
                fullText += "ASSESSMENT DUE: "+date+"\n";
                fullText += "ASSESSMENT ASSIGNED: "+created;
                arrayList.add(fullText);

                Log.d("XAXA","ID:"+id);

                myAssessment.setAssessmentDate(date);
                myAssessment.setAssessmentID(String.valueOf(id));
                myAssessment.setAssessmentName(name);
                myAssessment.setAssessmentType(type);
                myAssessment.setCourseID((long) course);
                myAssessment.setDescription(description);
                assessments.add(myAssessment);

            }while (myCursor.moveToNext());
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        assessmentLists.setAdapter(myAdapter);

        assessmentLists.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(new Intent(getApplicationContext(),AssessmentDetailActivity.class).putExtra("ASSESSMENT_DETAIL",assessments.get(position)));
        });

    }

}