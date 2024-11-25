package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import Adapter.ReviewAdapter;
import DTO.ReviewWithUserFullNameDTO;
import Interface.APIClient;
import Interface.ApiReview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChitietsanphamActivity extends AppCompatActivity {
    private static final String TAG = "ChitietsanphamActivity";

    // Views
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private TextView productNameTextView, productPriceTextView;
    private ImageView productImageView;

    // Data
    private List<ReviewWithUserFullNameDTO> reviewList = new ArrayList<>();
    private ApiReview apiReview;
    private int productId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        initializeViews();

        boolean isDataValid = getIntentData();
        if (!isDataValid) return;

        setupRecyclerView();

        loadProductDetails();
        loadReviews();

        setupButtons();
    }

    private void initializeViews() {
        productNameTextView = findViewById(R.id.product_description);
        productPriceTextView = findViewById(R.id.product_price);
        productImageView = findViewById(R.id.product_image);
        reviewRecyclerView = findViewById(R.id.review_list);
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

        productNameTextView.setText(productName);
        productPriceTextView.setText(String.format("₫%,.0f", productPrice));

        if (productImages != null && !productImages.isEmpty()) {
            Glide.with(this)
                    .load(productImages.get(0))
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(productImageView);
        } else {
            productImageView.setImageResource(R.drawable.error);
        }

        return true;
    }

    private void setupRecyclerView() {
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Truyền context vào adapter
        reviewAdapter = new ReviewAdapter(this, reviewList);
        reviewRecyclerView.setAdapter(reviewAdapter);
    }

    private void loadProductDetails() {
        Log.d(TAG, "Product ID: " + productId);
    }

    private void loadReviews() {
        if (apiReview == null) {
            apiReview = APIClient.getClient().create(ApiReview.class);
        }
        apiReview.getAllReview(productId).enqueue(new Callback<List<ReviewWithUserFullNameDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewWithUserFullNameDTO>> call, Response<List<ReviewWithUserFullNameDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleReviewResponse(response.body());
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.message());
                    Toast.makeText(ChitietsanphamActivity.this, "No reviews available!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReviewWithUserFullNameDTO>> call, Throwable t) {
                Toast.makeText(ChitietsanphamActivity.this, "Error loading reviews!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error loading reviews: " + t.getMessage());
            }
        });
    }

    private void handleReviewResponse(List<ReviewWithUserFullNameDTO> reviewDTOs) {
        reviewList.clear();
        reviewList.addAll(reviewDTOs);
        reviewAdapter.notifyDataSetChanged();
    }

    private void setupButtons() {
        findViewById(R.id.btn_add_to_cart).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_buy_now).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, BuyandpaymentActivity.class);
            intent.putExtra("origin", "order_details");
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, chatUserActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.shopping_cart).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}
