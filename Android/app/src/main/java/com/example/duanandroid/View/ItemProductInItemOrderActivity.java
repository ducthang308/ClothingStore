package com.example.duanandroid.View;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ItemProductInItemOrderAdapter;
import DTO.OrderDetailDTO;
import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class ItemProductInItemOrderActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private ItemProductInItemOrderAdapter itemProductInItemOrderAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList;
    private List<OrderDetail> orderDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_order_manage);

        rcvProduct = findViewById(R.id.rcv_product);
        productList = new ArrayList<>();
        productImageList = new ArrayList<>();
        orderDetailList = new ArrayList<>();

        // Cài đặt Adapter
        itemProductInItemOrderAdapter = new ItemProductInItemOrderAdapter(this, productList, productImageList, orderDetailList);
        rcvProduct.setLayoutManager(new LinearLayoutManager(this));
        rcvProduct.setAdapter(itemProductInItemOrderAdapter);

    }
}


