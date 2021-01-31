package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wgu_wemery.c196pa.adapters.CourseAdapter;
import com.wgu_wemery.c196pa.models.Course;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.ArrayList;

public class CourseListActivity extends AppCompatActivity {

    private ListView coursesList;
    private CourseAdapter courseAdapter;
    private ArrayList<Course> courseArrayList;
    public TextView no_course_txt;
    public DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        //initialize database
        handler = new DatabaseHandler(this);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        initUI();

    }

    private void initUI(){
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        coursesList = findViewById(R.id.list_courses);
        no_course_txt = findViewById(R.id.txt_no_courses);
        loadList();
    }

    private void loadList(){
        courseArrayList = new ArrayList<>();
        Cursor myCursor = handler.loadCourses();

        if(myCursor.moveToFirst()){
            do {
                Course singleCourse = new Course();
                singleCourse.setCourseId(myCursor.getInt(0));
                singleCourse.setCourseTermID(myCursor.getInt(1));
                singleCourse.setCourseDescription(myCursor.getString(2));
                singleCourse.setCourseMentor(myCursor.getString(3));
                singleCourse.setCourseMentorEmail(myCursor.getString(4));
                singleCourse.setCourseMentorPhone(myCursor.getString(5));
                singleCourse.setStatus(myCursor.getString(6));
                singleCourse.setCourseTitle(myCursor.getString(7));
                singleCourse.setCourseStartDate(myCursor.getString(8));
                singleCourse.setCourseEndDate(myCursor.getString(9));

                courseArrayList.add(singleCourse);
            }while(myCursor.moveToNext());
        }
        myCursor.close();
        courseAdapter = new CourseAdapter(courseArrayList,this,handler);
        coursesList.setAdapter(courseAdapter);
        if(courseArrayList.size()>0){
            no_course_txt.setVisibility(View.GONE);
        }else{
            no_course_txt.setVisibility(View.VISIBLE);
        }
    }


}