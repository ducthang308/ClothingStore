package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.LichSuVoucherAdapter;
import Model.Discount;

public class LichSuVoucherActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LichSuVoucherAdapter adapter;
    private List<Discount> LichsuvoucherList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_voucher);

        recyclerView = findViewById(R.id.recycleView_lichsu);
        LichsuvoucherList = new ArrayList<>();

        // Thiết lập adapter
        adapter = new LichSuVoucherAdapter(LichsuvoucherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadVoucherHistory();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton arrow_lichsu = findViewById(R.id.arrow_voucher_lichsu);
        arrow_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LichSuVoucherActivity.this, KhoVoucherActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void loadVoucherHistory() {
        // Thêm dữ liệu mẫu hoặc tải từ cơ sở dữ liệu
        LichsuvoucherList.add(new Discount(1, 5, "Giảm 5% cho đơn hàng trên 100k"));
        LichsuvoucherList.add(new Discount(2, 10, "Giảm 10% cho đơn hàng trên 200k"));
        LichsuvoucherList.add(new Discount(3, 15, "Giảm 15% cho đơn hàng trên 500k"));

        // Cập nhật giao diện sau khi tải dữ liệu
        adapter.notifyDataSetChanged();
    }
}
