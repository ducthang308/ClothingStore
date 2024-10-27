package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.SelectVoucherAdapter;
import Model.Discount;

public class SelectVoucherActivity extends AppCompatActivity implements SelectVoucherAdapter.OnVoucherSelectListener {
    private RecyclerView recyclerView;
    private SelectVoucherAdapter adapter;
    private List<Discount> discountList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_voucher);

        recyclerView = findViewById(R.id.recycleView_selectMagiamgia);
        Button btn_use = findViewById(R.id.btn_use);  // Khởi tạo btn_use

        discountList = new ArrayList<>();
        adapter = new SelectVoucherAdapter(discountList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadVouchers();

        ImageButton arrow_voucher = findViewById(R.id.arrow_voucher);
        arrow_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectVoucherActivity.this, BuyandpaymentActivity.class);
                startActivity(intent);
            }
        });
//        btn_use.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SelectVoucherActivity.this, BuyandpaymentActivity.class);
//                startActivity(intent);
//            }
//        });

    }



    private void loadVouchers() {
        discountList.add(new Discount(1, 5, "Giảm 5% cho đơn hàng trên 100k"));
        discountList.add(new Discount(2, 10, "Giảm 10% cho đơn hàng trên 200k"));
        discountList.add(new Discount(3, 15, "Giảm 15% cho đơn hàng trên 500k"));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onVoucherSelected(Discount discount) {
        // Thực hiện xử lý khi voucher được chọn
        Toast.makeText(this, "Voucher đã chọn: " + discount.getDiscount() + "% - " + discount.getMota(), Toast.LENGTH_SHORT).show();
    }
}
