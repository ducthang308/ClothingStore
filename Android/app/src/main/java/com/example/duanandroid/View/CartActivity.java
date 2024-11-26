package com.example.duanandroid.View;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CartAdapter;
import DTO.ProductDTO;
import Model.CartItem;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCartItems;
    private CartAdapter cartAdapter;
    private List<ProductDTO> cartItems = new ArrayList<>();
    private Intent intent;
    private int productId = -1;
    private TextView tv_color, tv_name, tv_price;
    private ImageView iv_image;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerCartItems = findViewById(R.id.recycler_cart_items);
        recyclerCartItems.setLayoutManager(new LinearLayoutManager(this));

        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);
        tv_color = findViewById(R.id.tv_color);
        iv_image = findViewById(R.id.iv_image);


        boolean isDataValid = getIntentData();
        if (!isDataValid) return;
        cartItems = new ArrayList<>();



        cartAdapter = new CartAdapter(cartItems);
        recyclerCartItems.setAdapter(cartAdapter);

        String origin = intent.getStringExtra("origin");
        TextView btn = findViewById(R.id.btn_checkout);
        btn.setOnClickListener(view -> {
            Intent checkoutIntent = new Intent(CartActivity.this, BuyandpaymentActivity.class);
            checkoutIntent.putExtra("origin", "cart");
            startActivity(checkoutIntent);
        });

        ImageButton backArrow = findViewById(R.id.btn_back);
        backArrow.setOnClickListener(view -> {
            if ("CartToAccount".equals(origin)) {
                Intent accountIntent = new Intent(CartActivity.this, ManageAccountActivity.class);
                finish();
            } else if ("CartToHome".equals(origin)) {
                Intent homeIntent = new Intent(CartActivity.this, mainpageActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
            } else {
                finish();
            }
        });

        ImageView chatIcon = findViewById(R.id.chat);
        chatIcon.setOnClickListener(view -> {
            Intent chatIntent = new Intent(CartActivity.this, chatUserActivity.class);
            startActivity(chatIntent);
        });
    }

    private boolean getIntentData() {
        productId = getIntent().getIntExtra("productId", -1);
        String productName = getIntent().getStringExtra("productName");
        float productPrice = getIntent().getFloatExtra("productPrice", 0);
        ArrayList<String> productImages = getIntent().getStringArrayListExtra("productImage");

        if (productId == -1 || productName == null || productPrice <= 0.0) {
            Log.e(TAG, "Invalid product data.");
            Toast.makeText(this, "Invalid product data!", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        tv_name.setText(productName);
        tv_price.setText(String.format("₫%,.0f", productPrice));

        if (productImages != null && !productImages.isEmpty()) {
            Glide.with(this)
                    .load(productImages.get(0))
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(iv_image);
        } else {
            iv_image.setImageResource(R.drawable.error);
        }

        return true;
    }

}