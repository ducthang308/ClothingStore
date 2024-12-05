package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import DTO.ReviewDTO;
import Interface.APIClient;
import Interface.ApiReview;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageDanhgiaActivity extends AppCompatActivity {

    private EditText reviewContent;
    private RatingBar ratingBar;
    private Button btnSendReview;
    private ApiReview apiReview;
    private String token;
    private int productId, orderId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_danhgia);

        // Retrieve token and userId from shared preferences
        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        userId = preferenceManager.getUserId();

        // Get product and order IDs from the intent
        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", -1);
        orderId = intent.getIntExtra("orderId", -1);

        Log.d("PageDanhgiaActivity", "Received productId: " + productId + ", orderId: " + orderId);

        // Map views
        reviewContent = findViewById(R.id.et_review_input);
        ratingBar = findViewById(R.id.rating_bar);
        btnSendReview = findViewById(R.id.btn_send_review);

        // Initialize API client
        apiReview = APIClient.getClient().create(ApiReview.class);

        // Set a listener for the RatingBar to capture user interaction
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                Log.d("RatingBar", "User changed rating: " + rating);
            }
        });

        // Set onClickListener for the submit button
        btnSendReview.setOnClickListener(v -> sendReview());
    }

    private void sendReview() {
        String content = reviewContent.getText().toString();
        float rating = ratingBar.getRating();

        // Check if both content and rating are valid
        if (content.isEmpty() || rating == 0) {
            Toast.makeText(this, "Vui lòng nhập nội dung và đánh giá sao!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a ReviewDTO object with the input data
        ReviewDTO reviewDTO = new ReviewDTO(productId, userId, orderId, content, rating);

        // Make API call to submit the review
        apiReview.createReview("Bearer " + token, reviewDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PageDanhgiaActivity.this, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after review submission
                } else {
                    Toast.makeText(PageDanhgiaActivity.this, "Không thể gửi đánh giá! Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PageDanhgiaActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
