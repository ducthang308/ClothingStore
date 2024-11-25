package com.example.duanandroid.View;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ItemProductInItemOrderAdapter;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    private ItemProductInItemOrderAdapter itemProductInItemOrderAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList;
    private List<OrderDetail> orderDetailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        // Lấy orderId từ Intent
        int orderId = getIntent().getIntExtra("orderId", -1); // -1 là giá trị mặc định nếu không có dữ liệu

        // Sử dụng orderId (ví dụ: tải dữ liệu từ cơ sở dữ liệu hoặc API)
        if (orderId != -1) {
            // Thực hiện logic, ví dụ:
            loadOrderDetails(orderId);
        } else {
            // Xử lý lỗi nếu không nhận được orderId
        }

        rcvProduct = findViewById(R.id.rcv_product);
        productList = new ArrayList<>();
        productImageList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        Product product = new Product(1, "Áo thun ngắn tay nữ trắng"," size M", 200000);
        ProductImage productImage = new ProductImage(1, R.drawable.ao);
        OrderDetail orderDetail = new OrderDetail(1, 1, 1,1);

        Product product2 = new Product(2, "Áo sơ mi tay dài nam", "size L", 350000);
        ProductImage productImage2 = new ProductImage(2, R.drawable.ao);
        OrderDetail orderDetail2 = new OrderDetail(2, 2, 1, 2);

        Product product3 = new Product(3, "Quần jean nam", "size 32", 450000);
        ProductImage productImage3 = new ProductImage(3, R.drawable.ao);
        OrderDetail orderDetail3 = new OrderDetail(3, 3, 1, 1);

        productList.add(product);
        productImageList.add(productImage);
        orderDetailList.add(orderDetail);

        productList.add(product2);
        productImageList.add(productImage2);
        orderDetailList.add(orderDetail2);

        productList.add(product3);
        productImageList.add(productImage3);
        orderDetailList.add(orderDetail3);
        // Cài đặt Adapter
        itemProductInItemOrderAdapter = new ItemProductInItemOrderAdapter(this, productList, productImageList, orderDetailList);
        rcvProduct.setLayoutManager(new LinearLayoutManager(this));
        rcvProduct.setAdapter(itemProductInItemOrderAdapter);

    }
    private void loadOrderDetails(int orderId) {
        // Gọi API hoặc truy vấn cơ sở dữ liệu để lấy chi tiết đơn hàng
    }
}
