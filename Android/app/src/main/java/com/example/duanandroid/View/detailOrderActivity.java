package com.example.duanandroid.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.databinding.ActivityDetailOrderBinding;

import Model.Product;

public class detailOrderActivity extends AppCompatActivity {
    private Product product;
    ActivityDetailOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getVariable();
        setVariable();
    }

    private void getVariable() {
//        binding.productName.setText(product.getProductName());
//        binding.productColor.setText(product.getColor());
//        binding.size.setText(product.getSize());
//        //binding.productSoluong.setText(product.getsoLuong);
//        binding.productPrice.setText(product.getPrice()+"đ");

        //Glide.with(detailOrderActivity.this)
          //      .load(product.getImage())
            //    .into(binding.pic);
    }

    private void setVariable() {
        product = (Product) getIntent().getSerializableExtra("product");
    }
}