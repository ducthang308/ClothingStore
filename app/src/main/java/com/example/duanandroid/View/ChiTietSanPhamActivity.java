package com.example.duanandroid.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ReviewAdapter;
import Model.Review;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // Khởi tạo RecyclerView
        reviewRecyclerView = findViewById(R.id.review_list);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo danh sách đánh giá (ví dụ tạm thời)
        reviewList = new ArrayList<>();
        reviewList.add(new Review("John Doe", "Great product!", 4.5f, R.drawable.aokhoac));
        reviewList.add(new Review("Jane Smith", "Very satisfied!", 5.0f, R.drawable.aokhoac));
        reviewList.add(new Review("Alice Brown", "Good quality.", 4.0f, R.drawable.aokhoac));

        // Thiết lập Adapter cho RecyclerView
        reviewAdapter = new ReviewAdapter(reviewList);
        reviewRecyclerView.setAdapter(reviewAdapter);
    }
}
