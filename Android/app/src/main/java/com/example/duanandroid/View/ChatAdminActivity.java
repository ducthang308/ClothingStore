package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ChatAdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_admin);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout ln_chat_admin= findViewById(R.id.item_chat);
        ln_chat_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_chat = new Intent(ChatAdminActivity.this, detailChatAdminActivity.class);
                startActivity(intent_chat);
            }
        });
    }
}