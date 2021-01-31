package com.wgu_wemery.c196pa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.wgu_wemery.c196pa.adapters.TermsAdapter;
import com.wgu_wemery.c196pa.models.Term;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.ArrayList;
import java.util.Locale;

public class TermListActivity extends AppCompatActivity {

    public static final int TERM_EDITOR_ACTIVITY_CODE = 11111;
    public static final int TERM_VIEWER_ACTIVITY_CODE = 22222;

    //add the views
    public Button add_term;
    public DatabaseHandler handler;
    public ListView myTerms;
    public TermsAdapter adapter;
    public ArrayList<Term> termList;
    public TextView no_terms_txt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }
        getSupportActionBar().setTitle("Terms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //call init method
        initUI();

    }

    public void initUI(){
        handler = new DatabaseHandler(this);
        add_term = findViewById(R.id.addTermBtn);
        myTerms = findViewById(R.id.termsListView);
        add_term.setOnClickListener(this::createDialog);
        no_terms_txt = findViewById(R.id.txt_add_term_no);
        loadList();
    }

    public void loadList(){
      termList = new ArrayList<>();
      termList.clear();
      //call cursor from terms
      Cursor termCursor = handler.loadTerms();
      if(termCursor.moveToFirst()){
          do {
              Term singleTerm = new Term();
              singleTerm.setTermId(termCursor.getInt(0));
              singleTerm.setTermTitle(termCursor.getString(1));
              singleTerm.setTermStartDate(termCursor.getString(2));
              singleTerm.setTermEndDate(termCursor.getString(3));
              if (termCursor.getString(4).equals("1")){
                  singleTerm.setCurrent(1);
              }
              singleTerm.setCreationDate(termCursor.getString(5));
              singleTerm.setCourseCount(handler.courseCount((int) singleTerm.getTermId()));
              termList.add(singleTerm);
          }while(termCursor.moveToNext());
      }
      termCursor.close();
      adapter = new TermsAdapter(this,termList,handler);
      myTerms.setAdapter(adapter);
      if(termList.size()>0){
         no_terms_txt.setVisibility(View.GONE);
      }else{
          no_terms_txt.setVisibility(View.VISIBLE);
      }
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

    private void createDialog(View view){

        View add_term_view = LayoutInflater.from(this).inflate(R.layout.add_term_dialog,null,false);
        TextInputEditText title = add_term_view.findViewById(R.id.edt_new_term_title);
        TextInputEditText start_date = add_term_view.findViewById(R.id.edt_new_term_start_date);
        TextInputEditText end_date = add_term_view.findViewById(R.id.edt_new_term_end_date);
        Button added_term = add_term_view.findViewById(R.id.btn_add_term);
        RadioButton rb_yes = add_term_view.findViewById(R.id.rb_yes);
        RadioButton rb_no = add_term_view.findViewById(R.id.rb_no);

        //set UI data

        start_date.setText(currentDate());
        end_date.setText(currentDate());

        start_date.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
            new DatePickerDialog(TermListActivity.this, dated(start_date), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        end_date.setOnFocusChangeListener((v,hasFocus) -> {
            if(hasFocus)
            new DatePickerDialog(TermListActivity.this, dated(end_date), myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        //create a dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setView(add_term_view)
                .setTitle("ADD A NEW TERM")
                .setPositiveButton("CANCEL",((dialog, which) -> {}))
                .setCancelable(false);
        AlertDialog add_term_dialog = builder.create();
        add_term_dialog.show();

        //add click listener
        added_term.setOnClickListener(v -> {

            if (title.getText().toString().isEmpty()){
                title.setError("KINDLY ADD A TERM NAME");
            }else{
                int active = 0;
                if (rb_yes.isChecked()){
                    active = 1;
                }
                handler.addTerm(title.getText().toString(),
                        start_date.getText().toString(),
                        end_date.getText().toString(),
                        active);
                Snackbar.make(view,"ADDED TERM SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
                title.setText("");
                loadList();
                add_term_dialog.dismiss();
            }

        });

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
