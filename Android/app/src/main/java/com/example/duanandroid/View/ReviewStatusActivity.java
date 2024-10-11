package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ReviewStatusActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitivity_reviewstatus);

        TextView btn=  findViewById(R.id.btn_review);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewStatusActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });

        TextView tabWaitingShipping = findViewById(R.id.tab_waiting_shipping);

        // Handle "Waiting for shipping" tab click
        tabWaitingShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, WaitingForShippingAcitivity.class);
                startActivity(intent);
            }
        });


        TextView tabwaitingdelivery = findViewById(R.id.waiting_delivery);
        // Handle "Waiting for delivery" tab click
        tabwaitingdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, WaitingForDeliveryActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_waiting_payment = findViewById(R.id.tab_waiting_patment);
        // Handle "Waiting for review" tab click
        tab_waiting_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, StatusPaymentActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_return_cancel_goods = findViewById(R.id.return_cancel_goods);
        // Handle "Waiting for review" tab click
        tab_return_cancel_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, Return_cancel_goodsActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnback = findViewById(R.id.back_arrow);
        // Handle "Waiting for review" tab click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

        Button btnreview = findViewById(R.id.btn_review);
        // Handle "Waiting for review" tab click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewStatusActivity.this, ReviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
