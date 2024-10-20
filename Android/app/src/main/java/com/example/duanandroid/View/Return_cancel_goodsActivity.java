package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class Return_cancel_goodsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canceloder);

        LinearLayout btn = findViewById(R.id.item_oder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, ReasoncancelActivity.class);
                startActivity(intent);
            }
        });
// Tab "Waiting for shipping"
        TextView tabWaitingShipping = findViewById(R.id.tab_waiting_shipping);

        // Handle "Waiting for shipping" tab click
        tabWaitingShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, WaitingForShippingAcitivity.class);
                startActivity(intent);
            }
        });


        TextView tabwaitingdelivery = findViewById(R.id.waiting_delivery);
        // Handle "Waiting for delivery" tab click
        tabwaitingdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, WaitingForDeliveryActivity.class);
                startActivity(intent);
            }
        });

        TextView tabreviewstatus = findViewById(R.id.tab_review);
        // Handle "Waiting for review" tab click
        tabreviewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, ReviewStatusActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnback = findViewById(R.id.back_arrow);
        // Handle "Waiting for review" tab click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_return_cancel_goods = findViewById(R.id.tab_waiting_patment);
        // Handle "Waiting for review" tab click
        tab_return_cancel_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Return_cancel_goodsActivity.this, StatusPaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}

