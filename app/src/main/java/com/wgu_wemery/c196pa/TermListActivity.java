package com.wgu_wemery.c196pa;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TermListActivity extends AppCompatActivity {

    public static final int TERM_EDITOR_ACTIVITY_CODE = 11111;
    public static final int TERM_VIEWER_ACTIVITY_CODE = 22222;

    DataProvider database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Terms");

        String[] from = { DBOpenHelper.TERM_TITLE, DBOpenHelper.TERM_STARTDATE, DBOpenHelper.TERM_ENDDATE };
        ListView list = (ListView) findViewById(android.R.id.list);

        database = new DataProvider();

    }



}
