package android.mybicycleshopv4.mybicyclecohort.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mybicycleshopv4.mybicyclecohort.R;
import android.os.Bundle;
import android.view.View;

public class PartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
    }

    public void addPart(View view) {
        Intent intent=new Intent(PartActivity.this,PartDetail.class);
        startActivity(intent);
    }
}