package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
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
import Model.User;

public class ChitietsanphamActivity extends AppCompatActivity {
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        String productName = getIntent().getStringExtra("productName");
        float productPrice = getIntent().getFloatExtra("productPrice", 0);
        String productImageResource = getIntent().getStringExtra("imageURL");


        TextView nameTextView = findViewById(R.id.product_description);
        TextView priceTextView = findViewById(R.id.product_price);
        ImageView imageView = findViewById(R.id.product_image);

        nameTextView.setText(productName);
        priceTextView.setText(String.format("₫%,.0f", productPrice));
        imageView.setImageResource(R.drawable.ao); // Placeholder image

        reviewRecyclerView = findViewById(R.id.review_list);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadData();

        reviewAdapter = new ReviewAdapter(reviewList, userList);
        reviewRecyclerView.setAdapter(reviewAdapter);

        ImageButton btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);
            startActivity(intent);
        });

        TextView btnBuyNow = findViewById(R.id.btn_buy_now);
        btnBuyNow.setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, BuyandpaymentActivity.class);
            intent.putExtra("origin", "order_details");
            startActivity(intent);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton btnBackHome = findViewById(R.id.back_home);
        btnBackHome.setOnClickListener(view -> finish());
    }


    private void LoadData() {

        userList = new ArrayList<>();
        userList.add(new User(1, "Nguyen Van A", "123 ABC Street", "0123456789", "a@gmail.com", null, null, "12345", "active", 1, 2));
        userList.add(new User(2, "Tran Thi B", "456 DEF Street", "0987654321", "b@gmail.com", null, null, "54321", "active", 1, 2));

        reviewList = new ArrayList<>();
        reviewList.add(new Review( "url1", "Sản phẩm rất tốt!", 4.5f, 4));
        reviewList.add(new Review( "url2", "Giao hàng nhanh!", 4.0f, 5));




    }
}
