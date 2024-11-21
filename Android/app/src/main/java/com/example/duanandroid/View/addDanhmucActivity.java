package com.example.duanandroid.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.io.IOException;

import DTO.CategoriesDTO;
import Interface.APICaterogy;
import Interface.APIClient;
import Interface.PreferenceManager;
import Model.Category;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addDanhmucActivity extends AppCompatActivity {
    private EditText edtCategoryName;
    private Button btnSave;
    private APICaterogy apiCategory;
    private String token;
    private int categoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_danhmuc);

        initViews();

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ImageButton backButton = findViewById(R.id.arrow_danhmuc);
        backButton.setOnClickListener(view -> {
            startActivity(new Intent(addDanhmucActivity.this, QuanLiDanhMucSPActivity.class));
            finish();
        });

        apiCategory = APIClient.createcategory();
        apiCategory = APIClient.updatecategory();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categoryId")) {
            categoryId = intent.getIntExtra("categoryId", -1);
            String categoryName = intent.getStringExtra("categoryname");
            edtCategoryName.setText(categoryName);
        }

        btnSave.setOnClickListener(view -> {
            if (categoryId == -1) {
                handleCreateCategory();
            } else {
                handleUpdateCategory();
            }
        });
    }

    private void initViews() {
        edtCategoryName = findViewById(R.id.edt_tendm);
        btnSave = findViewById(R.id.btn_luu);
    }

    private void createCategory(CategoriesDTO categoriesDTO) {
        apiCategory.createCategory("Bearer " + token, categoriesDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(addDanhmucActivity.this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                    navigateToCategoryManage();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(addDanhmucActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CreateCategory", "Failed: " + t.getMessage());
            }
        });
    }

    private void updateCategory(int categoryId, CategoriesDTO categoriesDTO) {
        apiCategory.updateCategory("Bearer " + token, categoryId, categoriesDTO).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(addDanhmucActivity.this, "Cập nhật danh mục thành công", Toast.LENGTH_SHORT).show();
                    navigateToCategoryManage();
                } else {
                    handleErrorResponseUpdate(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(addDanhmucActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UpdateCategory", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponseUpdate(Response<ResponseBody> response) {
        try {
            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Toast.makeText(addDanhmucActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("UpdateCategory", "Error handling response: " + e.getMessage());
            Toast.makeText(addDanhmucActivity.this, "Lỗi không xác định xảy ra", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleErrorResponse(Response<String> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("CreateCategory", "Error Code: " + response.code() + ", Error Body: " + errorBody);
            Toast.makeText(addDanhmucActivity.this, "Thêm không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("CreateCategory", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCreateCategory() {
        String categoryName = edtCategoryName.getText().toString().trim();
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên danh mục.", Toast.LENGTH_SHORT).show();
            return;
        }

        CategoriesDTO categoriesDTO = new CategoriesDTO(categoryName);
        createCategory(categoriesDTO);
    }

    private void handleUpdateCategory() {
        String categoryName = edtCategoryName.getText().toString().trim();
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên danh mục.", Toast.LENGTH_SHORT).show();
            return;
        }

        CategoriesDTO categoriesDTO = new CategoriesDTO(categoryName);
        updateCategory(categoryId,categoriesDTO);
    }

    private void navigateToCategoryManage() {
        startActivity(new Intent(addDanhmucActivity.this, QuanLiDanhMucSPActivity.class));
        finish();
    }
}
