package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.io.IOException;

import Interface.APIClient;
import Interface.ApiDiscounts;
import Interface.PreferenceManager;
import Model.Discount;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDiscountActivity extends AppCompatActivity {
    private EditText edt_percent, edt_note;
    private Button btnLuu;
    private ApiDiscounts apiDiscounts;
    private String token;
    private int discountId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);

        String action = getIntent().getStringExtra("action");
        initViews();

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageButton arrow_ManageDiscount = findViewById(R.id.arrow_ManageDiscount);
        arrow_ManageDiscount.setOnClickListener(view -> {
            startActivity(new Intent(AddDiscountActivity.this, DiscountManageActivity.class));
            finish();
        });

        apiDiscounts = APIClient.createDiscount();
        apiDiscounts = APIClient.updateDiscounts();

        // Xác định title
        TextView titleTextView = findViewById(R.id.tvTitle_profile); // Thay R.id.title bằng ID của TextView tiêu đề trong layout
        if ("add".equals(action)) {
            titleTextView.setText("Thêm mã giảm giá");
        } else if ("edit".equals(action)) {
            titleTextView.setText("Sửa mã giảm giá");

        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("discountId")) {
            discountId = intent.getIntExtra("discountId", -1);
            float discountPercent = intent.getFloatExtra("discountPercent", 0);
            String discountNote = intent.getStringExtra("discountNote");

            edt_percent.setText(String.valueOf(discountPercent));
            edt_note.setText(discountNote);
        }

        btnLuu.setOnClickListener(view -> {
            if (discountId == -1) {
                handleCreateDiscount();
            } else {
                handleUpdateDiscount();
            }
        });
    }

    private void initViews() {
        edt_percent = findViewById(R.id.edt_percent);
        edt_note = findViewById(R.id.edt_mota);
        btnLuu = findViewById(R.id.btn_luu);
    }

    private void createDiscount(Discount discount) {
        apiDiscounts.createDiscount("Bearer " + token, discount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddDiscountActivity.this, "Thêm mã giảm giá thành công", Toast.LENGTH_SHORT).show();
                    navigateToDiscountManage();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddDiscountActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CreateDiscount", "Failed: " + t.getMessage());
            }
        });
    }

    private void updateDiscount(int discountId, Discount discount) {
        apiDiscounts.updateDiscount("Bearer " + token, discountId, discount).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddDiscountActivity.this, "Cập nhật mã giảm giá thành công", Toast.LENGTH_SHORT).show();
                    navigateToDiscountManage();
                } else {
                    handleErrorResponseUpdate(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddDiscountActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UpdateDiscount", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponseUpdate(Response<ResponseBody> response) {
        try {
            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(AddDiscountActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("UpdateDiscount", "Error handling response: " + e.getMessage());
            Toast.makeText(AddDiscountActivity.this, "Lỗi không xác định xảy ra", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleErrorResponse(Response<String> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("CreateDiscount", "Error Code: " + response.code() + ", Error Body: " + errorBody);
            Toast.makeText(AddDiscountActivity.this, "Thêm không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("CreateDiscount", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCreateDiscount() {
        String percentText = edt_percent.getText().toString().trim();
        Float percent = parsePercent(percentText);
        if (percent == null) return;

        String note = edt_note.getText().toString().trim();
        if (note.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        Discount discount = new Discount(percent, note);
        createDiscount(discount);
    }

    private void handleUpdateDiscount() {
        String percentText = edt_percent.getText().toString().trim();
        Float percent = parsePercent(percentText);
        if (percent == null) return;

        String note = edt_note.getText().toString().trim();
        if (note.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        Discount discount = new Discount(percent, note);
        updateDiscount(discountId, discount);
    }

    private Float parsePercent(String percentText) {
        try {
            return Float.parseFloat(percentText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void navigateToDiscountManage() {
        startActivity(new Intent(AddDiscountActivity.this, DiscountManageActivity.class));
        finish();
    }
}