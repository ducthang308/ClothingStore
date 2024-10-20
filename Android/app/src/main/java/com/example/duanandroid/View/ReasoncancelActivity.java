package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class ReasoncancelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitvity_reasoncancel);

        ImageView btnback = findViewById(R.id.back_arrow);
        Button btnsubmit = findViewById(R.id.btn_submit);

        // Lấy thông tin tab hiện tại từ Intent
        Intent intent = getIntent();
        int tabPosition = intent.getIntExtra("tabPosition", 0); // Giá trị mặc định là 0 (WaitingForPayment)

//         /Xử lý nút back
//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Quay lại TabLayOutActivity với tab tương ứng
//                Intent backIntent = new Intent(ReasoncancelActivity.this, TabLayOutActivity.class);
//                backIntent.putExtra("tabPosition", tabPosition);
//                startActivity(backIntent);
//            }
//        });

        // Xử lý nút submit
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitIntent = new Intent(ReasoncancelActivity.this, Return_cancel_goodsActivity.class);
                startActivity(submitIntent);
            }
        });
    }
}
