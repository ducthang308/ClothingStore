package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ListReviewAdapter;
import Model.Product;
import Model.ProductImage;
import Model.Review;
import Model.User;

public class ListReviewActivity extends AppCompatActivity {
    private RecyclerView reviewRecyclerView;
    private ListReviewAdapter listReviewAdapter;
    private List<Review> reviewList;
    private List<User> userList;
    private List<Product> productList;
    private List<ProductImage> productImageList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review);

        ImageView btn_back = findViewById(R.id.listDanhgia_arrow_user);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        // Khởi tạo RecyclerView
        reviewRecyclerView = findViewById(R.id.recycleView_listReview);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo dữ liệu mẫu
        initSampleData();

        // Khởi tạo adapter
        listReviewAdapter = new ListReviewAdapter(reviewList, userList, productList);

        // Gắn adapter vào RecyclerView
        reviewRecyclerView.setAdapter(listReviewAdapter);
    }

    /**
     * Hàm tạo dữ liệu mẫu (giả lập dữ liệu cho Review, User và Product)
     */
    private void initSampleData() {
        // Dữ liệu giả lập cho User
        userList = new ArrayList<>();
        userList.add(new User(1, "ThaoNguyen", "123 ABC Street", "0123456789", "a@gmail.com", null, null, "12345", "active", 1, 2));


        // Dữ liệu giả lập cho Product
        productList = new ArrayList<>();
        productList.add(new Product("Áo thun trắng", "size M", 200000));


        reviewList = new ArrayList<>();
        reviewList.add(new Review( "url1", "Sản phẩm rất tốt!", 4.5f, 4));

        productImageList = new ArrayList<>();
        productImageList.add(new ProductImage(1, 1, "url_image_here"));

    }

}
