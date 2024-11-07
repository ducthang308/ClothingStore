package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class addDanhmucActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_danhmuc);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView arrow_danhmuc = findViewById(R.id.arrow_danhmuc);
        arrow_danhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}