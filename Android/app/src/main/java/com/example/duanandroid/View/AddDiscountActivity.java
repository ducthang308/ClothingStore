package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class AddDiscountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_discount);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton arrow_ManageDiscount = findViewById(R.id.arrow_ManageDiscount);
        arrow_ManageDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDiscountActivity.this, DiscountManageActivity.class);
                startActivity(intent);
            }
        });
    }
}