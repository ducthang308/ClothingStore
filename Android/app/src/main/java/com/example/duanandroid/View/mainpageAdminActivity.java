package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanandroid.R;
import java.util.ArrayList;
import java.util.List;
import Adapter.ProductAdapter;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainpageAdminActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<ProductDTO> productList;
    private ApiProduct apiProduct;
    private ProgressBar progressBar;
    private ImageView btn_account, btn_home, product_image;
    private Intent intent;
    private ProductDTO selectedProduct;
    private ImageView chat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpageadmin);

        productRecyclerView = findViewById(R.id.items);
        progressBar = findViewById(R.id.progressBar);

        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.setHasFixedSize(true);
        productList = new ArrayList<>();

        apiProduct = APIClient.getProduct();

        fetchProducts();

        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(mainpageAdminActivity.this, ChatAdminActivity.class);
                startActivity(intent);
            }
        });

        btn_account = findViewById(R.id.btn_account);
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(mainpageAdminActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(mainpageAdminActivity.this, mainpageAdminActivity.class);
                startActivity(intent);
            }
        });

        EditText searchBox = findViewById(R.id.search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Trigger search when user types something
                searchProducts(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void searchProducts(String keyword) {
        // Call your API to fetch filtered results based on the keyword
        showLoading(true);
        apiProduct.getProducts(keyword, 0L).enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                showLoading(false);
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    if (productAdapter == null) {
                        productAdapter = new ProductAdapter(productList, mainpageAdminActivity.this, true);
                        productRecyclerView.setAdapter(productAdapter);
                    } else {
                        productAdapter.notifyDataSetChanged();
                    }
                } else {
                    handleApiError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                showLoading(false);
                handleNetworkError(t);
            }
        });
    }


    private void fetchProducts() {
        showLoading(true); // Hiển thị ProgressBar
        apiProduct.getProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                showLoading(false); // Ẩn ProgressBar khi nhận dữ liệu

                if (response.isSuccessful() && response.body() != null) {
                    // Clear danh sách cũ để tránh bị trùng lặp dữ liệu
                    productList.clear();

                    // Thêm các sản phẩm mới từ API vào danh sách
                    productList.addAll(response.body());

                    if (productAdapter == null) {
                        productAdapter = new ProductAdapter(productList, mainpageAdminActivity.this, true);
                        productRecyclerView.setAdapter(productAdapter);
                    } else {
                        productAdapter.notifyDataSetChanged();
                    }

                    Log.d("fetchProducts", "Đã tải " + productList.size() + " sản phẩm.");
                } else {
                    handleApiError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {
                showLoading(false);
                handleNetworkError(t);
            }
        });
    }

    private void handleApiError(int code, String message) {
        String errorMessage;
        switch (code) {
            case 404:
                errorMessage = "Không tìm thấy dữ liệu.";
                break;
            case 500:
                errorMessage = "Lỗi server. Vui lòng thử lại sau.";
                break;
            default:
                errorMessage = "Không thể tải danh sách sản phẩm.";
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("mainpageAdminActivity", "Lỗi API: " + code + " - " + message);
    }

    private void handleNetworkError(Throwable t) {
        String errorMessage = t instanceof java.net.ConnectException
                ? "Không có kết nối mạng."
                : "Lỗi kết nối: " + t.getMessage();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("mainpageAdminActivity", "Lỗi kết nối API: ", t);
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            productRecyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            productRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}