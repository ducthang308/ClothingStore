package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.danhMucAdapter;
import Model.Category;

public class QuanLiDanhMucSPActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private danhMucAdapter adapter;
    private List<Category> categoryList;
    private Button btnAdd; // Khai báo nút thêm

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_danh_mucsp_activity); // Đảm bảo bạn đã tạo layout cho Activity này

        categoryList = new ArrayList<>();
        loadCategories();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new danhMucAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(QuanLiDanhMucSPActivity.this, addDanhmucActivity.class);
            startActivity(intent);
        });
    }

    private void loadCategories() {
        categoryList.add(new Category(1, "Thời trang mùa đông"));
        categoryList.add(new Category(2, "Thời trang mùa hè"));
        categoryList.add(new Category(3, "Thời trang thể thao "));
        categoryList.add(new Category(4, "Thời trang công sở"));
        categoryList.add(new Category(5, "Thời trang nữ"));
        categoryList.add(new Category(6, "Thời trang nam"));
//        adapter.notifyDataSetChanged(); // Đảm bảo cập nhật RecyclerView
    }
}
