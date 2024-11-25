package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.manage_orderAdapter;
import DTO.OrdersDTO;
import Interface.APIClient;
import Interface.ApiOrders;
import Model.OrderManage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageoderActivity extends AppCompatActivity {
    private TextView search_input, search_icon;
    private RecyclerView rvcOrderManage;
    private List<OrdersDTO> ListOrder;
    private manage_orderAdapter manageOrderAdapter;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_manageoder);

        search_input = findViewById(R.id.search_input);
        search_icon = findViewById(R.id.search_icon);
        rvcOrderManage = findViewById(R.id.rcv_orderManage);
        ListOrder = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvcOrderManage.setLayoutManager(linearLayoutManager);

        manageOrderAdapter = new manage_orderAdapter(ListOrder);
        rvcOrderManage.setAdapter(manageOrderAdapter);

        // Xử lý nút tìm kiếm
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderIdStr = search_input.getText().toString();

                if (TextUtils.isEmpty(orderIdStr)) {
                    Toast.makeText(ManageoderActivity.this, "Vui lòng nhập mã đơn hàng", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long orderId = Long.parseLong(orderIdStr);
                searchOrderById(orderId);
            }
        });

        // Xử lý nút quay lại
        ImageView btnback = findViewById(R.id.back_arrow);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageoderActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức tìm kiếm đơn hàng qua API
    private void searchOrderById(Long orderId) {
        ApiOrders apiOrders = APIClient.getOrderById(); // Lấy instance từ APIClient

        apiOrders.getOrderById(orderId).enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrdersDTO order = response.body();
                    ListOrder.clear(); // Xóa danh sách cũ
                    ListOrder.add(order); // Thêm kết quả mới vào danh sách
                    manageOrderAdapter.notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
                } else {
                    Toast.makeText(ManageoderActivity.this, "Không tìm thấy đơn hàng với mã: " + orderId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Toast.makeText(ManageoderActivity.this, "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}