package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ReasoncancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitvity_reasoncancel);
        {
            ImageView btnback = findViewById(R.id.back_arrow);
            // Handle "Waiting for review" tab click
            btnback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReasoncancelActivity.this, Return_cancel_goodsActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}