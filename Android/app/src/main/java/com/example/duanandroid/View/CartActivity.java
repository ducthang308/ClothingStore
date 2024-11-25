package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CartAdapter;
import Model.CartItem;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCartItems;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCartItems = findViewById(R.id.recycler_cart_items);
        recyclerCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Sample cart items data
        cartItems = new ArrayList<>();


        cartAdapter = new CartAdapter(cartItems);
        recyclerCartItems.setAdapter(cartAdapter);
        Intent intent = getIntent();
        String origin = intent.getStringExtra("origin");
        TextView btn=  findViewById(R.id.btn_checkout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, BuyandpaymentActivity.class);
                intent.putExtra("origin", "cart");
                startActivity(intent);
            }
        });


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageButton backArrow = findViewById(R.id.btn_back);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("CartToAccount".equals(origin)) {
                    Intent intent = new Intent(CartActivity.this, ManageAccountActivity.class);
//                    startActivity(intent);
                    finish();
                } else if ("CartToHome".equals(origin)) {
                    Intent intent = new Intent(CartActivity.this, mainpageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });

        ImageView imv=  findViewById(R.id.chat);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, chatUserActivity.class);
                startActivity(intent);
            }
        });
    }
}