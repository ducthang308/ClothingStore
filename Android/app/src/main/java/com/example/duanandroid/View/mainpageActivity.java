package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.duanandroid.View.ItemDecoration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import Model.Product;
import Model.ProductImage;

public class mainpageActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList; // Danh sách hình ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagemain);

        // Khởi tạo RecyclerView
        productRecyclerView = findViewById(R.id.items);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.addItemDecoration(new ItemDecoration(2, 24, true)); // 2 cột, khoảng cách 16dp
        // Tạo danh sách sản phẩm và danh sách hình ảnh
        productList = loadDataFromLayout();
        productImageList = loadProductImages(); // Tạo dữ liệu hình ảnh

        // Tạo Adapter và kết nối với RecyclerView
        productAdapter = new ProductAdapter(productList, productImageList);
        productRecyclerView.setAdapter(productAdapter);


        LinearLayout btn=  findViewById(R.id.account_user);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpageActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

        ImageView imv=  findViewById(R.id.chat);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpageActivity.this, chatUserActivity.class);
                startActivity(intent);
            }
        });

        ImageView shoppingcart=  findViewById(R.id.shopping_cart);
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpageActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private List<Product> loadDataFromLayout() {
        List<Product> products = new ArrayList<>();
        // Tạo dữ liệu sản phẩm mẫu
        for (int i = 0; i < 8; i++) {
            // Chỉ cần tên sản phẩm và giá
            products.add(new Product("Tên sản phẩm " + (i + 1), "M", (i + 1) * 100000));
        }
        return products;
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
