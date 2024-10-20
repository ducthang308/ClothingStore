package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class WaitingForShippingAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shippingstaytus);

        TextView tab_waiting_payment = findViewById(R.id.tab_waiting_patment);

        // Handle "Waiting for shipping" tab click
        tab_waiting_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForShippingAcitivity.this, StatusPaymentActivity.class);
                startActivity(intent);
            }
        });

        TextView tabwaitingdelivery = findViewById(R.id.waiting_delivery);
        // Handle "Waiting for delivery" tab click
        tabwaitingdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForShippingAcitivity.this, WaitingForDeliveryActivity.class);
                startActivity(intent);
            }
        });

        TextView tabreviewstatus = findViewById(R.id.tab_review);
        // Handle "Waiting for review" tab click
        tabreviewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForShippingAcitivity.this, ReviewStatusActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_return_cancel_goods = findViewById(R.id.return_cancel_goods);
        // Handle "Return/cancel goods" tab click
        tab_return_cancel_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForShippingAcitivity.this, Return_cancel_goodsActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnback = findViewById(R.id.back_arrow);
        // Handle "Back" button click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaitingForShippingAcitivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

//        Button btcancel = findViewById(R.id.btn_cancel_order);
//        // Handle "Cancel order" button click
//        btcancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(WaitingForShippingAcitivity.this, ReasoncancelActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
