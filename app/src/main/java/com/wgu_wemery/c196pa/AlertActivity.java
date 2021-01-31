package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.wgu_wemery.c196pa.receivers.Receivers;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.Locale;

public class AlertActivity extends AppCompatActivity {

    long dateSet = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        getSupportActionBar().setTitle("SET ALERT");
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

            new TimePickerDialog(AlertActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    setCalendarDate(inputEditText);
                }
            }, myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),false).show();

        };
        return date;
    }

    private void setCalendarDate(TextInputEditText inputEditText){
        dateSet = myCalendar.getTimeInMillis();
        String myFormat = "dd-MMM-yyyy hh:mm:ss a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        inputEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private String currentDate(){
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return sdf.format(myCalendar.getTime());
    }

    private void initUI(){
        TextInputEditText time,title,description;
        time = findViewById(R.id.edt_alert_date);
        title = findViewById(R.id.edt_alert_title);
        description = findViewById(R.id.edt_alert_description);
        Button setAlarm = findViewById(R.id.btn_setAlert);

        time.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus){
                new DatePickerDialog(AlertActivity.this, dated(time), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        setAlarm.setOnClickListener(v->{
            Intent myIntent = new Intent(getApplicationContext(), Receivers.class)
                    .putExtra("ALARM_TITLE",title.getText().toString())
                    .putExtra("ALARM_DESCRIPTION",description.getText().toString());
            PendingIntent myPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    898,
                    myIntent,
                    0);
            AlarmManager myManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            myManager.set(AlarmManager.RTC_WAKEUP, dateSet,myPendingIntent);
            Toast.makeText(this, "SUCCESSFULLY ADDED ALERT", Toast.LENGTH_SHORT).show();
            title.setText("");
            description.setText("");
            super.onBackPressed();
        });


    }

}