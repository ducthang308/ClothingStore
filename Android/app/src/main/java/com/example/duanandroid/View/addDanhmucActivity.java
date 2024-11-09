package com.example.duanandroid.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;
import DTO.CategoriesDTO;
import Interface.APICaterogy;
import Interface.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addDanhmucActivity extends AppCompatActivity {
    private EditText edtTenDm;
    private Button btnLuu;
    private APICaterogy categoriesApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_danhmuc);

        edtTenDm = findViewById(R.id.edt_tendm);
        btnLuu = findViewById(R.id.btn_luu);
        categoriesApi = APIClient.getCaterogyService(getApplicationContext());

        btnLuu.setOnClickListener(view -> createCategory());
    }

    private void createCategory() {
        String name = edtTenDm.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy token từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Kiểm tra nếu token rỗng
        if (token == null) {
            Toast.makeText(this, "Token không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng CategoriesDTO
        CategoriesDTO categoriesDTO = new CategoriesDTO();
        categoriesDTO.setName(name);

        // Gửi yêu cầu API để tạo danh mục mới
        categoriesApi.createCategory("Bearer " + token, categoriesDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(addDanhmucActivity.this, "Danh mục đã được tạo thành công", Toast.LENGTH_SHORT).show();
                    Log.d("Create", "Category created successfully");
                } else {
                    Log.e("Error", "Response code: " + response.code());
                    Toast.makeText(addDanhmucActivity.this, "Lỗi khi tạo danh mục", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Error", "Network error: " + t.getMessage());
                Toast.makeText(addDanhmucActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
