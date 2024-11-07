package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.DiscountManageAdapter;
import Model.Discount;

public class DiscountManageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiscountManageAdapter adapter;
    private List<Discount> discountList;
    private Button btnAdd, btnEdit, btnDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_manage);

        recyclerView = findViewById(R.id.recycleView_manageDiscount);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // Khởi tạo danh sách discount và tải dữ liệu
        discountList = new ArrayList<>();
        // Khởi tạo adapter với activityType là 1 cho DiscountManageActivity
        adapter = new DiscountManageAdapter(discountList, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadDiscounts();

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(DiscountManageActivity.this, AddDiscountActivity.class);
            startActivity(intent);
        });
        ImageView arrow_account = findViewById(R.id.arrow_account);
        arrow_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiscountManageActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadDiscounts() {
        discountList.add(new Discount(1, 5f, "Cho đơn hàng trên 100k"));
        discountList.add(new Discount(2, 10f, "Cho đơn hàng trên 200k"));
        discountList.add(new Discount(3, 15f, "Cho đơn hàng trên 500k"));

        // Thông báo cho adapter cập nhật dữ liệu
        adapter.notifyDataSetChanged();
    }
}
