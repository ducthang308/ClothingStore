package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;
import com.example.duanandroid.databinding.ActivityAdminAcountBinding;

public class adminAcountActivity extends AppCompatActivity {

    private ActivityAdminAcountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_acount);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_PersonInfo = findViewById(R.id.tv_personInfor);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_changePass = findViewById(R.id.tv_changePass);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_cus_manager = findViewById(R.id.tv_cus_manager);
        tv_PersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, editAccountActivity.class);
                startActivity(intent);
            }
        });
        tv_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(adminAcountActivity.this, changePassActivity.class);
                startActivity(intent1);
            }
        });
        tv_cus_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(adminAcountActivity.this, customerManagerActivity.class);
                startActivity(intent2);
            }
        });
    }


}