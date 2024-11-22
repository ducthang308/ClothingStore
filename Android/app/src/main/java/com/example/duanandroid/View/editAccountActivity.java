package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import Fragment.AccountUserFragment;

public class editAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_account);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton img_arrow = findViewById(R.id.arrow_profile);
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(editAccountActivity.this, AccountUserFragment.class);
//                startActivity(intent_profile);
                finish();
            }
        });



        Button btnsave = findViewById(R.id.btnSave);
        // Handle "Waiting for review" tab click
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editAccountActivity.this, AccountUserFragment.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}