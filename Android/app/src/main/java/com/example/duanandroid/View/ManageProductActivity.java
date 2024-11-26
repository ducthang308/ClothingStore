package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductManageAdapter;
import Model.Product1;
import Model.ProductImage;

public class ManageProductActivity extends AppCompatActivity {
    private RecyclerView productManageRecyclerView;
    private ProductManageAdapter productManageAdapter;
    private List<Product1> productList;
    private List<ProductImage> productImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct);


        productManageRecyclerView = findViewById(R.id.rcv_productManage);
        productManageRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        productList = new ArrayList<>();
        productImageList = new ArrayList<>();
        productImageList= loadProductImages();


        Product1 pr1=new Product1(1, "Áo thun nam", 2, "2", "S", 200000, 20,100) ;
        productList.add(pr1);

        Product1 pr2 = new Product1(1, "Áo thun nam", 2, "blue", "M", 200000, 20,200);
        productList.add(pr2);
        Product1 pr3 = new Product1(1, "Áo thun nam", 2, "blue", "L", 200000, 20,50);
        productList.add(pr3);
        Product1 pr4 = new Product1(2, "Áo thun nữ", 2, "blue", "L", 200000, 20,80);
        productList.add(pr4);
        Product1 pr5 = new Product1(3, "Áo khoác", 2, "blue", "M", 200000, 20,300);
        productList.add(pr5);
        Product1 pr6 = new Product1(4, "Áo thun nữ", 2, "blue", "S", 200000, 20,40);
        productList.add(pr6);
        Product1 pr7 = new Product1(5, "Áo thun nữ", 2, "blue", "M", 200000, 20,100);
        productList.add(pr7);
        Product1 pr8 =new Product1(6, "Áo thun nữ", 2, "blue", "L", 200000, 20,30);
        productList.add(pr8);



        productManageAdapter = new ProductManageAdapter(this,productList, productImageList);
        productManageRecyclerView.setLayoutManager(linearLayoutManager);
        productManageRecyclerView.setAdapter(productManageAdapter);

        ImageView btnback = findViewById(R.id.back_arrow);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageProductActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView Addsp = findViewById(R.id.Addsp);
        Addsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageProductActivity.this, AddSanphamActivity.class);
                startActivity(intent);
            }
        });
    }
    private List<ProductImage> loadProductImages() {
        List<ProductImage> productImages = new ArrayList<>();
        // Tạo dữ liệu hình ảnh mẫu
        for (int i = 0; i < 8; i++) {
            // Giả sử bạn có hình ảnh tương ứng trong drawable với tên aokhoac1, aokhoac2, ...
            String imageName = "ao" ; // Giả sử bạn có ba hình ảnh aokhoac1, aokhoac2, aokhoac3
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            productImages.add(new ProductImage(i, i, String.valueOf(imageResId))); // Tạo sản phẩm hình ảnh
        }
        return productImages;
    }
}
