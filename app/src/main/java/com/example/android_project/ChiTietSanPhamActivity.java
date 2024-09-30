package com.example.android_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietsanpham); // Layout chi tiết sản phẩm

        // Nhận dữ liệu từ Intent
        String productName = getIntent().getStringExtra("productName");
        String productPrice = getIntent().getStringExtra("productPrice");
        int productImageResource = getIntent().getIntExtra("productImageResource", 0);

        // Tham chiếu đến các view trong layout chitietsanpham
        TextView nameTextView = findViewById(R.id.product_description);
        TextView priceTextView = findViewById(R.id.product_price);
        ImageView imageView = findViewById(R.id.product_image);

        // Hiển thị dữ liệu vào các view
        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        imageView.setImageResource(productImageResource);
    }
}
