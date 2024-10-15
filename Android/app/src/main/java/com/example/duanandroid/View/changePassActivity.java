package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanandroid.R;

public class changePassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ImageButton img_arrow = findViewById(R.id.arrow_changepass);
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(changePassActivity.this, ManageAccountActivity.class);
                startActivity(intent_profile);
            }
        });

        Button btnsave = findViewById(R.id.btnSave);
        // Handle "Waiting for review" tab click
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changePassActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}