package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.manage_orderAdapter;
import Model.OrderManage;

public class ManageoderActivity extends AppCompatActivity {

    private RecyclerView rvcOrderManage;
    private List<OrderManage> ListOrder;
    private manage_orderAdapter manageOrderAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_manageoder);

        rvcOrderManage = findViewById(R.id.rcv_orderManage);
        ListOrder = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvcOrderManage.setLayoutManager(linearLayoutManager);


        manageOrderAdapter = new manage_orderAdapter(ListOrder);
        rvcOrderManage.setAdapter(manageOrderAdapter);


        OrderManage order1 = new OrderManage(1, new Date(), 150000, 300000, "Nguyễn Văn A", "Áo Thun ngắn tay nữ ", 2, "delivering", R.drawable.ao);


        ListOrder.add(order1);


        manageOrderAdapter.notifyDataSetChanged();


        ImageView btnback = findViewById(R.id.back_arrow);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageoderActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });
    }
}
