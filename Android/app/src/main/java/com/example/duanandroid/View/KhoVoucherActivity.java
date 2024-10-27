package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.DiscountManageAdapter;
import Fragment.AccountUserFragment;
import Model.Discount;

public class KhoVoucherActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiscountManageAdapter adapter;
    private List<Discount> discountList;
    private Button btnAdd, btnEdit, btnDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_voucher);
        recyclerView = findViewById(R.id.recycleView_khoVoucher);
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView not found. Check if the ID 'recycleView' exists in activity_kho_voucher.xml");
        }
        discountList = new ArrayList<>();
        adapter = new DiscountManageAdapter(discountList, 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadVouchers();

        TextView lichsu = findViewById(R.id.tv_lichsu);
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhoVoucherActivity.this, LichSuVoucherActivity.class);
                startActivity(intent);
            }
        });
        ImageButton arrow_voucher = findViewById(R.id.arrow_voucher);
        arrow_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhoVoucherActivity.this, AccountUserFragment.class);
                finish();
            }
        });
    }


    private void loadVouchers() {
        discountList.add(new Discount(1, 5f, "Giảm giá cho đơn hàng trên 100k"));
        discountList.add(new Discount(2, 10f, "Giảm giá cho đơn hàng trên 200k"));
        discountList.add(new Discount(3, 15f, "Giảm giá cho đơn hàng trên 500k"));


        adapter.notifyDataSetChanged();
    }

}
