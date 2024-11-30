package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.manage_orderAdapter;
import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
import Interface.ApiOrders;
import Interface.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageoderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private manage_orderAdapter adapter;
    private ApiOrders apiOrders;
    private ApiOrderDetail apiOrderDetail;
    private List<OrderDetailReturnDTO> orderDetailList = new ArrayList<>();
    private List<OrdersDTO> ordersDTOS = new ArrayList<>();
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_manageoder);

        // Thiết lập RecyclerView
        recyclerView = findViewById(R.id.rcv_orderManage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView searchicon = findViewById(R.id.search_icon);
        EditText search_input = findViewById(R.id.search_input);

        // Lấy token từ PreferenceManager
        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        // Khởi tạo API client
        apiOrders = APIClient.getClient().create(ApiOrders.class);
        apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);

        ImageView btnback = findViewById(R.id.back_arrow);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageoderActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });

        searchicon.setOnClickListener(v -> {
            // search by orderid
            String orderIdStr = search_input.getText().toString().trim();
            if (TextUtils.isEmpty(orderIdStr)) {
                Toast.makeText(this, "Vui lòng nhập ID đơn hàng hoặc ID người dùng", Toast.LENGTH_SHORT).show();
                return;
            }
            Long orderId = Long.parseLong(orderIdStr);
            searchOrderById(orderId);
        });

        // Lấy danh sách đơn hàng và chi tiết
        fetchOrdersAndDetails();
    }

    private void fetchOrdersAndDetails() {
        apiOrders.getAllOrders("Bearer " + token).enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ordersDTOS = response.body();  // Lưu lại danh sách tất cả các đơn hàng
                    Log.d("API Response", "Total Orders: " + ordersDTOS.size());

                    // Lấy chi tiết của từng đơn hàng
                    for (OrdersDTO order : ordersDTOS) {
                        fetchOrderDetails(order.getId());
                    }

                    // Nếu không có đơn hàng
                    if (ordersDTOS.isEmpty()) {
                        Toast.makeText(ManageoderActivity.this, "Không có đơn hàng nào!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ManageoderActivity.this, "Không thể lấy danh sách đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrdersDTO>> call, Throwable t) {
                Toast.makeText(ManageoderActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchOrderDetails(int orderId) {
        apiOrderDetail.getOrderDetails("Bearer " + token, orderId).enqueue(new Callback<List<OrderDetailReturnDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDetailReturnDTO>> call, Response<List<OrderDetailReturnDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Order Details", "Received order details: " + response.body().size());

                    // Thêm chi tiết vào danh sách
                    orderDetailList.addAll(response.body());

                    // Cập nhật RecyclerView
                    if (adapter == null) {
                        adapter = new manage_orderAdapter(ManageoderActivity.this, orderDetailList,ordersDTOS);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("API Error", "Error code: " + response.code());
                    Toast.makeText(ManageoderActivity.this, "Không thể lấy chi tiết đơn hàng! Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetailReturnDTO>> call, Throwable t) {
                Toast.makeText(ManageoderActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Tìm kiếm đơn hàng theo orderId
    private void searchOrderById(Long orderId) {
        List<OrdersDTO> filteredOrders = new ArrayList<>();
        for (OrdersDTO order : ordersDTOS) {
            if (order.getId() == orderId) {
                filteredOrders.add(order);  // Thêm đơn hàng tìm được vào danh sách filtered
            }
        }

        // Nếu tìm thấy đơn hàng
        if (!filteredOrders.isEmpty()) {
            orderDetailList.clear();  // Clear chi tiết cũ
            for (OrdersDTO order : filteredOrders) {
                fetchOrderDetails(order.getId());  // Lấy chi tiết cho từng đơn hàng
            }
            // Cập nhật RecyclerView
            if (adapter == null) {
                adapter = new manage_orderAdapter(ManageoderActivity.this, orderDetailList,ordersDTOS);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "Không tìm thấy đơn hàng với ID này", Toast.LENGTH_SHORT).show();
        }
    }
}
