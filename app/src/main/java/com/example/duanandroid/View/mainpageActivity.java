package com.example.duanandroid.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import Model.Product;

public class mainpageActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu); // Chỉ sử dụng layout trangchu

        // Khởi tạo RecyclerView
        productRecyclerView = findViewById(R.id.items);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Lấy dữ liệu từ layout
        productList = loadDataFromLayout();

        // Tạo Adapter và kết nối với RecyclerView
        productAdapter = new ProductAdapter(productList);
        productRecyclerView.setAdapter(productAdapter);
    }

    private List<Product> loadDataFromLayout() {
        List<Product> products = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);

        // Giả sử bạn có 4 sản phẩm trong layout
        for (int i = 0; i < 12; i++) {
            View view = inflater.inflate(R.layout.model1, null);
            TextView productName = view.findViewById(R.id.product_name);
            TextView productPrice = view.findViewById(R.id.product_price);
            ImageView productImage = view.findViewById(R.id.product_image);

            // Thay đổi giá trị cho từng sản phẩm
            productName.setText("Tên sản phẩm " + (i + 1));
            productPrice.setText((i + 1) * 100 + ".000đ");
            productImage.setImageResource(R.drawable.aokhoac); // Thay đổi hình ảnh nếu cần

            // Thêm sản phẩm vào danh sách
            products.add(new Product(productName.getText().toString(), productPrice.getText().toString(), R.drawable.aokhoac));
        }

        return products;
    }

}
