package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.wgu_wemery.c196pa.models.Assessment;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.Locale;

public class AssessmentDetailActivity extends AppCompatActivity {

    DatabaseHandler handler;
    Assessment myAssessment;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        handler = new DatabaseHandler(this);
        myAssessment = (Assessment) getIntent().getSerializableExtra("ASSESSMENT_DETAIL");

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        getSupportActionBar().setTitle("ASSESSMENT DETAILS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initUI();
    }

    private void initUI(){
        View add_assessment = LayoutInflater.from(this).inflate(R.layout.activity_assessment_detail,null,false);

        TextInputEditText ass_title = findViewById(R.id.edt_new_term_title);
        TextInputEditText ass_due_date = findViewById(R.id.edt_new_term_start_date);
        TextInputEditText ass_description = findViewById(R.id.edt_new_term_end_date);
        RadioButton alert = findViewById(R.id.rb_yes);
        AppCompatSpinner status = findViewById(R.id.spinnerStatusCourse);
        Button addAssessment = findViewById(R.id.btn_add_term);
        Button delete = findViewById(R.id.delete_ass);

        ass_due_date.setText(currentDate());

        ass_title.setText(myAssessment.getAssessmentName());
        ass_due_date.setText(myAssessment.getAssessmentDate());
        ass_description.setText(myAssessment.getDescription());


        id = Integer.parseInt(myAssessment.getAssessmentID());

        if ("Performance".equals(myAssessment.getAssessmentType())) {
            status.setSelection(0);
        } else {
            status.setSelection(1);
        }

        ass_due_date.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
                new DatePickerDialog(AssessmentDetailActivity.this, dated(ass_due_date), myCalendar
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
                    handler.updateAssessment(id,
                            assignmentType,
                            ass_title.getText().toString(),
                            ass_due_date.getText().toString(),
                            ass_description.getText().toString(),
                            alerted);
                    Toast.makeText(AssessmentDetailActivity.this,"UPDATED ASSESSMENT SUCCESSFULLY",Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(v->{
            new MaterialAlertDialogBuilder(AssessmentDetailActivity.this)
                    .setTitle("DELETE ASSESSMENT")
                    .setMessage("Are you sure you want to delete this assessment?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        handler.deleteAssessment(id);
                        Toast.makeText(getApplicationContext(),"DELETED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),AssessmentListActivity.class));
                        finish();
                    })
                    .setNegativeButton("NO",(dialog, which) -> {
                    })
                    .create().show();

        });

        findViewById(R.id.btn_add_ALERT).setOnClickListener(v->{
            startActivity(new Intent(AssessmentDetailActivity.this,AlertActivity.class));
        });

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

}