package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ManageoderActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_manageoder);
        {

            ImageView btnback = findViewById(R.id.back_arrow);
            // Handle "Waiting for review" tab click
            btnback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ManageoderActivity.this, adminAcountActivity.class);
                    startActivity(intent);
                }
            });


        }
    }
}