package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class WaitingForDeliveryActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitvity_deliverystatus);

        TextView tab_waiting_payment = findViewById(R.id.tab_waiting_patment);

        // Handle "Waiting for shipping" tab click
        tab_waiting_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForDeliveryActivity.this, StatusPaymentActivity.class);
                startActivity(intent);
            }
        });


        TextView tabWaitingShipping = findViewById(R.id.tab_waiting_shipping);
        // Handle "Waiting for delivery" tab click
        tabWaitingShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForDeliveryActivity.this, WaitingForShippingAcitivity.class);
                startActivity(intent);
            }
        });

        TextView tabreviewstatus = findViewById(R.id.tab_review);
        // Handle "Waiting for review" tab click
        tabreviewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForDeliveryActivity.this, ReviewStatusActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_return_cancel_goods = findViewById(R.id.return_cancel_goods);
        // Handle "Waiting for review" tab click
        tab_return_cancel_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForDeliveryActivity.this, Return_cancel_goodsActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnback = findViewById(R.id.back_arrow);
        // Handle "Waiting for review" tab click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForDeliveryActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
