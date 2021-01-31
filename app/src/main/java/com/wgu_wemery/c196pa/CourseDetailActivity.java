package com.wgu_wemery.c196pa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.wgu_wemery.c196pa.models.Assessment;
import com.wgu_wemery.c196pa.models.Course;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.ListHelper;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.ArrayList;
import java.util.Locale;

public class CourseDetailActivity extends AppCompatActivity {

    TextInputEditText title,instName,instEmail,instPhone,startDate,endDate,description;
    AppCompatSpinner statusSpinner;
    Button addCourseBtn;
    ListView assessmentLists;
    DatabaseHandler handler;
    TextView no_assesment;
    ArrayList<Assessment> assessments;
    ArrayList<String> myAssessments;
    Course myCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        getSupportActionBar().setTitle("YOUR COURSE NOTES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initUI();
    }

    final Calendar myCalendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener dated(TextInputEditText inputEditText){
        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setCalendarDate(inputEditText);
        };
        return date;
    }

    private void setCalendarDate(TextInputEditText inputEditText){
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        inputEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private String currentDate(){
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return sdf.format(myCalendar.getTime());
    }

    private void initUI(){
        assessments = new ArrayList<>();
        myAssessments = new ArrayList<>();
        handler = new DatabaseHandler(this);

        myCourse = (Course) getIntent().getSerializableExtra("COURSE_DETAILS");

        //load term name
        StringBuilder termName = new StringBuilder("TERM NAME: ");
        Cursor terms = handler.loadTerms(myCourse.getCourseTermID());
        if(terms.moveToFirst()){
            do {
                termName.append(terms.getString(1));
            }while (terms.moveToNext());
        }
        getSupportActionBar().setTitle(termName.toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //initialize user interface
        title = findViewById(R.id.edt_new_course_title);
        instName = findViewById(R.id.edt_new_mentor_name);
        instEmail = findViewById(R.id.edt_new_mentor_email);
        instPhone = findViewById(R.id.edt_new_mentor_phone);
        startDate = findViewById(R.id.edt_new_course_start);
        endDate = findViewById(R.id.edt_new_course_end);
        description = findViewById(R.id.edt_new_course_description);
        statusSpinner = findViewById(R.id.spinnerStatusCourse);
        addCourseBtn = findViewById(R.id.btn_add_course);
        assessmentLists = findViewById(R.id.assessmentList);
        no_assesment = findViewById(R.id.txt_no_assessment);

        loadAssessments();

        //set details
        title.setText(myCourse.getCourseTitle());
        instName.setText(myCourse.getCourseMentor());
        instEmail.setText(myCourse.getCourseMentorEmail());
        instPhone.setText(myCourse.getCourseMentorPhone());
        startDate.setText(myCourse.getCourseStartDate());
        endDate.setText(myCourse.getCourseEndDate());
        description.setText(myCourse.getCourseDescription());

        switch (myCourse.getStatus()){
            case "Dropped":
                statusSpinner.setSelection(2);
                break;
            case "In Progress":
                statusSpinner.setSelection(0);
                break;
            case "Plan to take":
                statusSpinner.setSelection(3);
                break;
            case "Completed":
                statusSpinner.setSelection(1);
                break;
            default:
                break;
        }

        addCourseBtn.setOnClickListener(v->{
           String status = this.getResources().getStringArray(R.array.course_status)[statusSpinner.getSelectedItemPosition()];
           handler.updateCourse(title.getText().toString(),
                   startDate.getText().toString(),
                   endDate.getText().toString(),
                   instName.getText().toString(),
                   instEmail.getText().toString(),
                   instPhone.getText().toString(),
                   description.getText().toString(),
                   (int) myCourse.getCourseId(),
                   status);
            Snackbar.make(v,"UPDATED COURSE SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
        });


    }

    private void addAssessment(int id,View views){
        View add_assessment = LayoutInflater.from(this).inflate(R.layout.add_assessment_dialog,null,false);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle("ADD ASSESSMENT")
                .setView(add_assessment)
                .setPositiveButton("CANCEL",((dialog, which) -> {}))
                .setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextInputEditText ass_title = add_assessment.findViewById(R.id.edt_new_term_title);
        TextInputEditText ass_due_date = add_assessment.findViewById(R.id.edt_new_term_start_date);
        TextInputEditText ass_description = add_assessment.findViewById(R.id.edt_new_term_end_date);
        RadioButton alert = add_assessment.findViewById(R.id.rb_yes);
        AppCompatSpinner status = add_assessment.findViewById(R.id.spinnerStatusCourse);
        Button addAssessment = add_assessment.findViewById(R.id.btn_add_term);

        ass_due_date.setText(currentDate());

        ass_due_date.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
                new DatePickerDialog(CourseDetailActivity.this, dated(ass_due_date), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        addAssessment.setOnClickListener(v->{
            if (ass_title.getText().toString().isEmpty()){
                ass_title.setError("Your assessment needs a title");
            }else{
                if (ass_description.getText().toString().isEmpty()){
                    ass_description.setError("Please provide a description");
                }else{
                    String assignmentType = getResources().getStringArray(R.array.assessment_type)[status.getSelectedItemPosition()];
                    boolean alerted = alert.isChecked();
                    handler.addAssessment(id,
                            assignmentType,
                            ass_title.getText().toString(),
                            ass_due_date.getText().toString(),
                            ass_description.getText().toString(),
                            alerted);
                    Snackbar.make(views,"ADDED ASSESSMENT SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
                    ass_title.setText("");
                    ass_description.setText("");
                    dialog.dismiss();
                    loadAssessments();
                }
            }
        });

    }

    private void loadAssessments(){
        myAssessments.clear();
        Cursor assessMentCursor = handler.loadAssessment(myCourse.getCourseId());

        if(assessMentCursor.moveToFirst()){
            do {
                Assessment singleAssessment = new Assessment();
                StringBuilder assessmentString = new StringBuilder();
                String type = assessMentCursor.getString(2);
                singleAssessment.setAssessmentType(type);
                assessmentString.append("TYPE: ").append(type).append("\n");
                String name = assessMentCursor.getString(3);
                singleAssessment.setAssessmentName(name);
                assessmentString.append("TITLE: ").append(name).append("\n");
                String dueDate = assessMentCursor.getString(4);
                singleAssessment.setAssessmentDate(dueDate);
                assessmentString.append("DUE DATE: ").append(dueDate).append("\n");
                String description = assessMentCursor.getString(5);
                singleAssessment.setDescription(description);
                assessmentString.append("Description: ").append(description);
                myAssessments.add(assessmentString.toString());
                int id = assessMentCursor.getInt(0);
                singleAssessment.setAssessmentID(String.valueOf(id));
                assessments.add(singleAssessment);

            }while (assessMentCursor.moveToNext());
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myAssessments);
        assessmentLists.setAdapter(adapter);
        ListHelper.setSize(assessmentLists);

        assessmentLists.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(new Intent(getApplicationContext(),AssessmentDetailActivity.class).putExtra("ASSESSMENT_DETAIL",assessments.get(position)));
        });

        if(myAssessments.size()<1){
            no_assesment.setVisibility(View.VISIBLE);
        }else{
            no_assesment.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_add_assessment:
                addAssessment((int) myCourse.getCourseId(),findViewById(R.id.root_course_detail));
                break;
            case R.id.action_course_alerts:
                startActivity(new Intent(this,AlertActivity.class));
                break;
            case R.id.action_course_notes:
                Intent myIntent = new Intent(CourseDetailActivity.this, CourseNotes.class);
                myIntent.putExtra("COURSE",(int) myCourse.getCourseId());
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}