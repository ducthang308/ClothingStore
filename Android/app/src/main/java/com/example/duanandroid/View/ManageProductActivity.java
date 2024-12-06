package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.ProductManageAdapter;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiProduct;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProductActivity extends AppCompatActivity {

    // UI components
    private ListView listView;
    private ProgressBar progressBar;
    private TextView noDataText;
    private Button updateButton, btnDelete;
    private ProductManageAdapter adapter;
    private Intent intent;
    private String token;

    // API and Data
    private List<ProductDTO> productList = new ArrayList<>();
    private ApiProduct apiProduct;
    private ProductDTO selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageproduct);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        initializeViews();
        setupListView();
        apiProduct = APIClient.getProduct();

        fetchProducts();
    }

    private void initializeViews() {
        listView = findViewById(R.id.lv_productManage);
        progressBar = findViewById(R.id.progressBar);
        noDataText = findViewById(R.id.noDataText);

        ImageView backButton = findViewById(R.id.back_arrow);
        backButton.setOnClickListener(v -> {
            intent = new Intent(ManageProductActivity.this, adminAcountActivity.class);
            startActivity(intent);
        });

        TextView addButton = findViewById(R.id.Addsp);
        addButton.setOnClickListener(v -> {
            intent = new Intent(ManageProductActivity.this, AddSanphamActivity.class);
            startActivity(intent);
        });

        updateButton = findViewById(R.id.btn_modify);
        updateButton.setOnClickListener(view -> {
            selectedProduct = adapter.getSelectedProduct();
            if (selectedProduct != null) {
                navigateToEditProduct(selectedProduct);
            } else {
                Toast.makeText(this, "Vui lòng chọn sản phẩm để chỉnh sửa.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(view -> {
            selectedProduct = adapter.getSelectedProduct();
            if (selectedProduct != null) {
                deleteProduct(selectedProduct.getId());
            } else {
                Toast.makeText(ManageProductActivity.this, "Vui lòng chọn sản phẩm cần xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(v -> {
            intent = new Intent(ManageProductActivity.this, adminAcountActivity.class);
            startActivity(intent);
        });
    }

    private void setupListView() {
        // Cấu hình ListView
        adapter = new ProductManageAdapter(this, productList, product -> {
            selectedProduct = product;
            Toast.makeText(this, "Đã chọn: " + product.getProductName(), Toast.LENGTH_SHORT).show();
        });

        // Set the adapter for ListView
        listView.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn một item (removed since handled by adapter)
    }

    private void fetchProducts() {
        showLoading(true);

        apiProduct.getProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    adapter.updateData(productList);

                    if (productList.isEmpty()) {
                        showNoData(true);
                    } else {
                        showNoData(false);
                        Log.d("ManageProductActivity", "Số sản phẩm: " + productList.size());
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

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        listView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        noDataText.setVisibility(View.GONE);
    }

    private void showNoData(boolean isNoData) {
        noDataText.setVisibility(isNoData ? View.VISIBLE : View.GONE);
        listView.setVisibility(isNoData ? View.GONE : View.VISIBLE);
    }

    private void handleApiError(int code, String message) {
        String errorMessage;
        switch (code) {
            case 404:
                errorMessage = "Sản phẩm không tìm thấy.";
                break;
            case 500:
                errorMessage = "Lỗi server. Vui lòng thử lại sau.";
                break;
            default:
                errorMessage = "Không thể tải danh sách sản phẩm.";
        }
        showToast(errorMessage);
        Log.e("ManageProductActivity", "Lỗi API: " + code + " - " + message);
    }

    private void handleNetworkError(Throwable t) {
        String errorMessage = t instanceof java.net.ConnectException
                ? "Không có kết nối mạng."
                : "Lỗi kết nối: " + t.getMessage();
        showToast(errorMessage);
        Log.e("ManageProductActivity", "Lỗi kết nối API: ", t);
    }

    private void navigateToEditProduct(ProductDTO product) {
        Intent intent = new Intent(ManageProductActivity.this, AddSanphamActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productCategory", product.getCategoryId());
        intent.putExtra("productColor", product.getColor());
        intent.putExtra("productPrice", product.getPrice());

        ArrayList<String> imageUrls = new ArrayList<>(product.getImageUrls());
        intent.putExtra("productImage", imageUrls);

        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void deleteProduct(int productId) {
        apiProduct.deleteProduct("Bearer " + token, productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ManageProductActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    fetchProducts();
                } else {
                    handleDeleteError(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ManageProductActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Delete", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleDeleteError(Response<Void> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("Delete", "Error Code: " + response.code() + ", Error Body: " + errorBody);

            if (response.code() == 401) {
                Toast.makeText(ManageProductActivity.this, "Token không hợp lệ hoặc hết hạn", Toast.LENGTH_SHORT).show();
            } else if (response.code() == 404) {
                Toast.makeText(ManageProductActivity.this, "Không tìm thấy sản phẩm để xóa", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageProductActivity.this, "Xóa không thành công: " + errorBody, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("Delete", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}
