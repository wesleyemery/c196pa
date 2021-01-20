package com.wgu_wemery.c196pa;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class TermListActivity extends AppCompatActivity {

    public static final int TERM_EDITOR_ACTIVITY_CODE = 11111;
    public static final int TERM_VIEWER_ACTIVITY_CODE = 22222;

    DataProvider dataProvider;
    private CursorAdapter ca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setTitle("Terms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        String[] from = { DBOpenHelper.TERM_TITLE, DBOpenHelper.TERM_STARTDATE, DBOpenHelper.TERM_ENDDATE };
        int[] to = { R.id.termTitle, R.id.startDate, R.id.endDate };

        ca = new SimpleCursorAdapter(this, R.layout.content_term_list, null, from, to, 0);
        dataProvider = new DataProvider();

        ListView list = (ListView) findViewById(R.id.termsListView);
        list.setAdapter(ca);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TermListActivity.this, TermListActivity.class);
                Uri uri = Uri.parse(DataProvider.TERM_TERMS_URI + "/" + id);
                startActivityForResult(intent, TERM_VIEWER_ACTIVITY_CODE);
            }
        });

    }




    /* @Override
        protected void onCreate(@ Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_term_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Terms");

            String[] from = { DBOpenHelper.TERM_TITLE, DBOpenHelper.TERM_STARTDATE, DBOpenHelper.TERM_ENDDATE };
           // int[] to = { R.id., R.id.tvTermStartDate, R.id.tvTermEndDate };

            //ca = new SimpleCursorAdapter(this, R.layout.term_list_item, null, from, to, 0);
            db = new DataProvider();

        }
    */
    public void onBack(View view) {
        Intent intent = new Intent(TermListActivity.this,  MainActivity.class);
        startActivity(intent);
    }

}
