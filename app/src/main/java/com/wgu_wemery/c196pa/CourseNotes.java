package com.wgu_wemery.c196pa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.wgu_wemery.c196pa.models.CourseNote;
import com.wgu_wemery.c196pa.utils.DatabaseHandler;
import com.wgu_wemery.c196pa.utils.Util;

import java.util.ArrayList;

public class CourseNotes extends AppCompatActivity {

    private ListView notesList;
    private ArrayList<CourseNote> courseNotesArrayList;
    private ArrayList<String> notesSimpleList;
    private Button addNote;
    private TextView noNotes;
    private DatabaseHandler handler;
    private  int course_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes);
        handler = new DatabaseHandler(this);
        course_id = getIntent().getIntExtra("COURSE",1);

        if(new Util(this).isNightMode){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_purple)));
        }

        initUI();
    }

    private void initUI(){
        notesList = findViewById(R.id.myNotesList);
        noNotes = findViewById(R.id.txtNote);
        addNote = findViewById(R.id.button);
        notesSimpleList = new ArrayList<>();
        courseNotesArrayList = new ArrayList<>();

        addNote.setOnClickListener(v->{
            addNote(v);
        });

        loadNotes();
    }

    private void loadNotes(){
        notesSimpleList.clear();
        courseNotesArrayList.clear();

        Cursor notesCursor = handler.retreiveNotes(course_id);
        if(notesCursor.moveToFirst()){
            do {

                int note_id = notesCursor.getInt(0);
                int course_id = notesCursor.getInt(1);
                String text = notesCursor.getString(2);
                CourseNote courseNote = new CourseNote(text,(long)note_id);
                courseNotesArrayList.add(courseNote);
                notesSimpleList.add(text);

            }while (notesCursor.moveToNext());
        }

        if(notesSimpleList.size()<1){
            noNotes.setText("ADD A NOTE. YOU HAVE NO NOTES YET");
        }else{
            noNotes.setText("CLICK ON A NOTE TO SHARE");
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, notesSimpleList);
        notesList.setAdapter(myAdapter);
        notesList.setOnItemClickListener((parent, view, position, id) -> {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_SUBJECT, "COURSE NOTES");
            email.putExtra(Intent.EXTRA_TEXT, courseNotesArrayList.get(position).getCourseNoteNote());
            email.setType("text/plain");
            startActivity(Intent.createChooser(email, "SHARE via SMS"));
        });

    }

    private void addNote(View XX){
        View add = LayoutInflater.from(this).inflate(R.layout.add_note_layout,null,false);
        EditText note = add.findViewById(R.id.note_add);
        EditText url = add.findViewById(R.id.editTextTextPersonName2);

        new MaterialAlertDialogBuilder(this)
            .setTitle("ADD NOTE TO COURSE")
            .setView(add)
            .setPositiveButton("ADD",(dialog, which) -> {

                String notes = note.getText().toString();
                String urls = url.getText().toString();

                if(notes.isEmpty()){
                    Snackbar.make(XX,"YOU NEED A NOTE TO ADD",Snackbar.LENGTH_LONG).show();
                }else{

                    handler.addCourseNote(course_id,notes,urls);
                    Snackbar.make(XX,"ADDED SUCCESSFULLY",Snackbar.LENGTH_LONG).show();
                    note.setText("");
                    url.setText("");
                    loadNotes();
                }

            })
            .setNegativeButton("CANCEL",(dialog, which) -> {

            }).create().show();
    }


}