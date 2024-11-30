package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

public class OrderDetailActivity extends AppCompatActivity {
    private TextView productName, productPrice, productQuantity, totalPayment, orderDate, deliveryAddress, orderId;
    private ImageView productImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        // Ánh xạ các views
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productQuantity = findViewById(R.id.product_soluong);
        totalPayment = findViewById(R.id.total);
        orderDate = findViewById(R.id.order_date);
        deliveryAddress = findViewById(R.id.delivery_address);
        productImage = findViewById(R.id.pic);
        orderId = findViewById(R.id.tv_orderid);  // Ánh xạ TextView cho mã đơn hàng

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String productNameStr = intent.getStringExtra("product_name");
        String productPriceStr = intent.getStringExtra("product_price");
        int productQuantityValue = intent.getIntExtra("product_quantity", 0);
        String totalPaymentStr = intent.getStringExtra("total_payment");
        String productImageUrl = intent.getStringExtra("product_image_url");
        String deliveryAddressStr = intent.getStringExtra("delivery_address");
        String orderDateStr = intent.getStringExtra("order_date");
        int orderIdValue = getIntent().getIntExtra("order_id", -1); // Lấy mã đơn hàng từ Intent

        // Hiển thị dữ liệu lên giao diện
        productName.setText(productNameStr != null ? productNameStr : "Tên sản phẩm: Chưa rõ");
        productPrice.setText(productPriceStr != null ? productPriceStr : "0đ");
        productQuantity.setText("x" + productQuantityValue);
        totalPayment.setText(totalPaymentStr != null ? totalPaymentStr : "0đ");
        deliveryAddress.setText(deliveryAddressStr != null ? deliveryAddressStr : "Địa chỉ giao hàng: Chưa có");
        orderDate.setText(orderDateStr != null ? orderDateStr : "Ngày đặt hàng: Chưa rõ");

        // Hiển thị mã đơn hàng
        if (orderIdValue != -1) {
            orderId.setText("Mã đơn hàng: " + orderIdValue);
        } else {
            orderId.setText("Mã đơn hàng: Chưa rõ");
        }

        // Hiển thị hình ảnh sản phẩm
        Glide.with(this)
                .load(productImageUrl)
                .placeholder(R.drawable.co4la)
                .error(R.drawable.error)
                .into(productImage);
    }
}
