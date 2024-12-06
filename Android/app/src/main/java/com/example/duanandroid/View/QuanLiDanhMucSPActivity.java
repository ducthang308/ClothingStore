package com.example.duanandroid.View;

import static Interface.APIClient.deletecategory;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.DiscountManageAdapter;
import Adapter.danhMucAdapter;
import DTO.CategoriesDTO;
import Interface.APICaterogy;
import Interface.APIClient;
import Interface.PreferenceManager;
import Model.Category;
import Model.Discount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLiDanhMucSPActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private danhMucAdapter adapter;
    private List<Category> caterogyList= new ArrayList<>(); // Khởi tạo danh sách ngay từ đầu
    private Button btnAdd,btnEdit,btnDelete;
    private APICaterogy categoriesApi;
    private String token;
    private Category selectedCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_danh_mucsp_activity);

        recyclerView = findViewById(R.id.recyclerView_manageCategory);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        adapter = new danhMucAdapter(caterogyList,this ,  category-> {
             selectedCategory= category;
        });
        recyclerView.setAdapter(adapter);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        categoriesApi = APIClient.getcategory();
        categoriesApi = APIClient.deletecategory();
        loadCategory();

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, addDanhmucActivity.class);
            startActivity(intent);
        });

        btnEdit.setOnClickListener(view -> {
            if (selectedCategory != null) {
                Intent intent = new Intent(QuanLiDanhMucSPActivity.this, addDanhmucActivity.class);
                intent.putExtra("categoryId", selectedCategory.getId());
                intent.putExtra("categoryname", selectedCategory.getCategoryName());// Giả sử `id` là duy nhất
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng chọn danh mục để chỉnh sửa.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(view -> {
            if (selectedCategory != null) {
                deleteCategory(selectedCategory.getId());
            } else {
                Toast.makeText(QuanLiDanhMucSPActivity.this, "Vui lòng chọn mã giảm giá cần xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView back_accountAdmin = findViewById(R.id.back_accountAdmin);
        back_accountAdmin.setOnClickListener(v->{
            Intent intent = new Intent(QuanLiDanhMucSPActivity.this, adminAcountActivity.class);
            startActivity(intent);
        });
    }

    private void loadCategory() {
        Call<List<Category>> call = categoriesApi.getAllCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    caterogyList.clear();
                    caterogyList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    String errorMessage = response.message();
                    Toast.makeText(QuanLiDanhMucSPActivity.this, "Error: " + statusCode + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
            }
        });
    }
    private void deleteCategory(int categoryId) {
        // Thực hiện yêu cầu xóa discount
        categoriesApi.deleteCategory("Bearer " + token, categoryId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(QuanLiDanhMucSPActivity.this, "Xóa mã giảm giá thành công", Toast.LENGTH_SHORT).show();
                    loadCategory();
                } else {
                    handleDeleteError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(QuanLiDanhMucSPActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DeleteDiscount", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleDeleteError(Response<Void> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("DeleteDiscount", "Error Code: " + response.code() + ", Error Body: " + errorBody);

            if (response.code() == 401) {
                Toast.makeText(QuanLiDanhMucSPActivity.this, "Token không hợp lệ hoặc hết hạn", Toast.LENGTH_SHORT).show();
            } else if (response.code() == 404) {
                Toast.makeText(QuanLiDanhMucSPActivity.this, "Không tìm thấy mã giảm giá để xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuanLiDanhMucSPActivity.this, "Xóa không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("DeleteDiscount", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }

}


