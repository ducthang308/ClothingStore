package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapter.DiscountManageAdapter;
import Interface.APIClient;
import Interface.ApiDiscounts;
import Interface.PreferenceManager;
import Model.Discount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountManageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DiscountManageAdapter adapter;
    private List<Discount> discountList = new ArrayList<>();
    private ApiDiscounts apiDiscounts;
    private Button btnAdd, btnEdit, btnDelete;
    private String token;
    private Discount selectedDiscount; // updated variable name to indicate it's the selected discount

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_manage);

        recyclerView = findViewById(R.id.recycleView_manageDiscount);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DiscountManageAdapter(this, discountList, discount -> {
            selectedDiscount = discount;
        });
        recyclerView.setAdapter(adapter);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        apiDiscounts = APIClient.getDiscounts();
        apiDiscounts = APIClient.deleteDiscounts();
        loadDiscounts();

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddDiscountActivity.class);
            startActivity(intent);
        });

        btnEdit.setOnClickListener(view -> {
            if (selectedDiscount != null) {
                Intent intent = new Intent(DiscountManageActivity.this, AddDiscountActivity.class);
                intent.putExtra("discountId", selectedDiscount.getId());
                intent.putExtra("discountPercent", selectedDiscount.getPercent());
                intent.putExtra("discountNote", selectedDiscount.getNote());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select a discount to edit.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(view -> {
            if (selectedDiscount != null) {
                deleteDiscount(selectedDiscount.getId());
            } else {
                Toast.makeText(DiscountManageActivity.this, "Vui lòng chọn mã giảm giá cần xóa.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDiscounts() {
        Call<List<Discount>> call = apiDiscounts.getDiscounts();
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    discountList.clear();
                    discountList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    String errorMessage = response.message();
                    Toast.makeText(DiscountManageActivity.this, "Error: " + statusCode + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                Toast.makeText(DiscountManageActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDiscount(int discountId) {
        // Thực hiện yêu cầu xóa discount
        apiDiscounts.deleteDiscount("Bearer " + token, discountId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DiscountManageActivity.this, "Xóa mã giảm giá thành công", Toast.LENGTH_SHORT).show();
                    loadDiscounts();
                } else {
                    handleDeleteError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DiscountManageActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DeleteDiscount", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleDeleteError(Response<Void> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("DeleteDiscount", "Error Code: " + response.code() + ", Error Body: " + errorBody);

            if (response.code() == 401) {
                Toast.makeText(DiscountManageActivity.this, "Token không hợp lệ hoặc hết hạn", Toast.LENGTH_SHORT).show();
            } else if (response.code() == 404) {
                Toast.makeText(DiscountManageActivity.this, "Không tìm thấy mã giảm giá để xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DiscountManageActivity.this, "Xóa không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("DeleteDiscount", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }


}