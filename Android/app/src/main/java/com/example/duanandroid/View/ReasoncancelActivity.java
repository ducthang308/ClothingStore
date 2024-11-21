package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ReasonCancelAdapter;
import Fragment.TabLayOutActivity;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class ReasoncancelActivity extends AppCompatActivity {
    private RecyclerView rcv_reasonCancel;
    private ReasonCancelAdapter reasonCancelAdapter; // Sửa lại nếu cần
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitvity_reasoncancel);

        rcv_reasonCancel = findViewById(R.id.rcv_reasonCancel);
        rcv_reasonCancel.setLayoutManager(new LinearLayoutManager(this));
        ImageView btnback = findViewById(R.id.back_arrow_trangthai);
        Button btnsubmit = findViewById(R.id.btn_submit);

        productList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        productImageList = new ArrayList<>();



        // Initialize Adapter
        reasonCancelAdapter = new ReasonCancelAdapter(this, productList, orderDetailList, productImageList);
        rcv_reasonCancel.setAdapter(reasonCancelAdapter);

        // Xử lý nút submit
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitIntent = new Intent(ReasoncancelActivity.this, TabLayOutActivity.class);
                submitIntent.putExtra("tabPosition", 4);
                startActivity(submitIntent);
                finish();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReasoncancelActivity.this, TabLayOutActivity.class);
//                intent.putExtra("tabPosition", 0);
//                startActivity(intent);
                finish();
            }
        });
    }
}
