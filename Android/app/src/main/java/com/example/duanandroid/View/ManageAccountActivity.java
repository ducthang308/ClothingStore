package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acctivity_manage_account);
        {
            TextView btn=  findViewById(R.id.view_all_oders);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, StatusPaymentActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout btn1=  findViewById(R.id.waiting_payment_icon);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, StatusPaymentActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout btn2=  findViewById(R.id.waiting_shipping_icon);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, WaitingForShippingAcitivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout btn3=  findViewById(R.id.waiting_delivery_icon);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, WaitingForDeliveryActivity.class);
                    startActivity(intent);
                }
            });

            LinearLayout btn4=  findViewById(R.id.review_icon);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, ReviewStatusActivity.class);
                    startActivity(intent);
                }
            });


            LinearLayout backhome=  findViewById(R.id.home);
            backhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, mainpageActivity.class);
                    startActivity(intent);
                }
            });

            RelativeLayout support=  findViewById(R.id.support);
            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, HelpActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}