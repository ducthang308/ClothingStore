package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class BuyandpaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buyandpayment);

        // Lấy giá trị "origin" từ intent
        Intent intent = getIntent();
        String origin = intent.getStringExtra("origin");

        TextView btn = findViewById(R.id.btn_oder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyandpaymentActivity.this, mainpageActivity.class);
                startActivity(intent);
            }
        });

        ImageView back = findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra "origin" và điều hướng về trang phù hợp
                if ("cart".equals(origin)) {
                    Intent intent = new Intent(BuyandpaymentActivity.this, CartActivity.class);
                    startActivity(intent);
                } else if ("order_details".equals(origin)) {
                    Intent intent = new Intent(BuyandpaymentActivity.this, ChitietsanphamActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
