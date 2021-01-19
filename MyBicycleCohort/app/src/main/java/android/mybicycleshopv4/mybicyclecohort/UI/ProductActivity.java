package android.mybicycleshopv4.mybicyclecohort.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.mybicycleshopv4.mybicyclecohort.R;

import android.os.Bundle;
import android.view.View;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }

    public void addProduct(View view) {
        Intent intent=new Intent(ProductActivity.this,PartActivity.class);
        startActivity(intent);
    }
}