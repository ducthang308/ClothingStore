package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ItemInStatusPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.item_in_status_payment);
        {
            Button cancel=  findViewById(R.id.btn_cancel_order);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ItemInStatusPayment.this, Return_cancel_goodsActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}