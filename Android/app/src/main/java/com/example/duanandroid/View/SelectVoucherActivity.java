package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.SelectVoucherAdapter;
import Interface.APIClient;
import Interface.ApiDiscounts;
import Model.Discount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectVoucherActivity extends AppCompatActivity implements SelectVoucherAdapter.OnVoucherSelectListener {

    private RecyclerView recyclerView;
    private SelectVoucherAdapter adapter;
    private List<Discount> discountList = new ArrayList<>();
    private ApiDiscounts apiDiscounts;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_voucher);

        recyclerView = findViewById(R.id.recycleView_selectMagiamgia);

        // Khởi tạo API client
        apiDiscounts = APIClient.getDiscounts();

        // Set up RecyclerView và Adapter
        adapter = new SelectVoucherAdapter(discountList, this); // Callback về activity
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Gọi API load vouchers
        loadVouchers();

        // Xử lý nút quay lại
        ImageButton arrow_voucher = findViewById(R.id.arrow_voucher);
        arrow_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Quay lại màn hình trước đó
            }
        });
    }

    /**
     * Gọi API để tải danh sách voucher
     */
    public void loadVouchers() {
        Call<List<Discount>> call = apiDiscounts.getDiscounts();
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Cập nhật danh sách voucher
                    discountList.clear();
                    discountList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    String errorMessage = response.message();
                    Toast.makeText(SelectVoucherActivity.this, "Error: " + statusCode + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                Toast.makeText(SelectVoucherActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Callback khi chọn một voucher từ Adapter
     */
    @Override
    public void onVoucherSelected(Discount discount) {
        // Truyền ID, Note và Percent của voucher qua Intent
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selected_voucher_id", discount.getId()); // ID của voucher
        resultIntent.putExtra("selected_voucher_note", discount.getNote()); // Ghi chú của voucher
        resultIntent.putExtra("selected_voucher_percent", discount.getPercent()); // Tỷ lệ giảm giá
        setResult(RESULT_OK, resultIntent);

        // Kết thúc Activity
        finish();
    }
}
