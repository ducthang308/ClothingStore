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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.danhMucAdapter;
import DTO.CategoriesDTO;
import Interface.APICaterogy;
import Interface.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLiDanhMucSPActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private danhMucAdapter adapter;
    private List<CategoriesDTO> caterogyDTOList;
    private Button btnAdd;
    private APICaterogy categoriesApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_danh_mucsp_activity);

        caterogyDTOList = new ArrayList<>();
        categoriesApi = APIClient.getCaterogyService(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Khởi tạo adapter chỉ một lần
        adapter = new danhMucAdapter(caterogyDTOList, this);
        recyclerView.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(QuanLiDanhMucSPActivity.this, addDanhmucActivity.class);
            startActivity(intent);
        });

        ImageView back_accountAdmin = findViewById(R.id.back_accountAdmin);
        back_accountAdmin.setOnClickListener(view -> {
            Intent intent = new Intent(QuanLiDanhMucSPActivity.this, adminAcountActivity.class);
            startActivity(intent);
        });

        fetchAllCategories(); // Load categories from API
    }

    private void fetchAllCategories() {
        categoriesApi.getAllCategories().enqueue(new Callback<List<CategoriesDTO>>() {
            @Override
            public void onResponse(Call<List<CategoriesDTO>> call, Response<List<CategoriesDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoriesDTO> categories = response.body();
                    Log.d("Categories", "Fetched " + categories.size() + " categories.");

                    if (!categories.isEmpty()) {
                        caterogyDTOList.clear(); // Clear previous data
                        caterogyDTOList.addAll(categories); // Add new data

                        // Notify adapter of data changes
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("Categories", "Danh mục trống");
                    }
                } else {
                    // Lỗi nếu response không thành công
                    Log.e("Error", "Response không thành công: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesDTO>> call, Throwable t) {
                // Lỗi mạng hoặc kết nối
                Log.e("Error", "Network error: " + t.getMessage());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //fetchAllCategories(); // Làm mới danh sách khi quay lại Activity
    }
}

