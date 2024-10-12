package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.PaymentStatusAdapter;
import Model.Order;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class StatusPaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PaymentStatusAdapter paymentStatusAdapter;
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;
//    private List<Order> orderList;
    private LinearLayout layoutProductButtons;
    private LinearLayout layoutShippingButtons;
    private LinearLayout layoutDeliveryButtons;
    private LinearLayout layoutReviewButtons;
    private LinearLayout layoutReviewnote;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentstatus);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.itempayment_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Lists
        productList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        productImageList = new ArrayList<>();

        // Add Dummy Data (for testing)
        productList.add(new Product("Áo thun ngắn tay nữ trắng", "M", 200000));
        orderDetailList.add(new OrderDetail(1, 1, 1, 200000, 100, 200000, null));
        productImageList.add(new ProductImage(1, 1, "url_image_here"));

        // Initialize Adapter
        paymentStatusAdapter = new PaymentStatusAdapter(this, productList, orderDetailList, productImageList);
        recyclerView.setAdapter(paymentStatusAdapter);

        // Initialize button layouts
        layoutProductButtons = findViewById(R.id.layout_product_buttons);
        layoutShippingButtons = findViewById(R.id.layout_shipping_buttons);
        layoutDeliveryButtons = findViewById(R.id.layout_delivery_buttons);
        layoutReviewButtons = findViewById(R.id.layout_review_buttons);
        layoutReviewnote = findViewById(R.id.layout_review_note);
        // Tab "Waiting for shipping"
        TextView tabWaitingShipping = findViewById(R.id.tab_waiting_shipping);

        // Handle "Waiting for shipping" tab click
        tabWaitingShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusPaymentActivity.this, WaitingForShippingAcitivity.class);
                startActivity(intent);
            }
        });


        TextView tabwaitingdelivery = findViewById(R.id.waiting_delivery);
        // Handle "Waiting for delivery" tab click
        tabwaitingdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusPaymentActivity.this, WaitingForDeliveryActivity.class);
                startActivity(intent);
            }
        });

        TextView tabreviewstatus = findViewById(R.id.tab_review);
        // Handle "Waiting for review" tab click
        tabreviewstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusPaymentActivity.this, ReviewStatusActivity.class);
                startActivity(intent);
            }
        });

        TextView tab_return_cancel_goods = findViewById(R.id.return_cancel_goods);
        // Handle "Waiting for review" tab click
        tab_return_cancel_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusPaymentActivity.this, Return_cancel_goodsActivity.class);
                startActivity(intent);
            }
        });

        ImageView btnback = findViewById(R.id.back_arrow);
        // Handle "Waiting for review" tab click
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusPaymentActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });

//        Button btcancel = findViewById(R.id.btn_cancel_order);
//        // Handle "Waiting for review" tab click
//        btcancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(StatusPaymentActivity.this, Return_cancel_goodsActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
