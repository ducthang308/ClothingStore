package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import Adapter.ItemProductInItemOrderAdapter;
import Adapter.manage_orderAdapter;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Interface.APIClient;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import Model.Order;
import Model.OrderManage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageoderActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrders, recyclerViewProducts;
    private manage_orderAdapter manageOrderAdapter;
    private ItemProductInItemOrderAdapter productAdapter;
    private List<Order> ordersList;
    private List<OrdersDTO> ordersDTOS;
    private List<ProductDTO> productList;
    private List<ProductImageDTO> imageList;
    private String token;
    private ApiOrders apiOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_manageoder);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerViewOrders = findViewById(R.id.rcv_orderManage);
        ImageView searchicon = findViewById(R.id.search_icon);
        EditText search_input = findViewById(R.id.search_input);

        // Khởi tạo danh sách và adapter
        ordersList = new ArrayList<>();


<<<<<<< HEAD
        manageOrderAdapter = new manage_orderAdapter(this,ordersList);
        recyclerViewOrders.setAdapter(manageOrderAdapter);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        manageOrderAdapter.setOnItemClickListener(order -> {
            Intent intent = new Intent(ManageoderActivity.this, OrderDetailActivity.class);
            intent.putExtra("orderId", order.getId());
            startActivity(intent);
=======
                Long orderId = Long.parseLong(orderIdStr);
                //searchOrderById(orderId);
            }
>>>>>>> origin/main
        });


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

            //search by userid
//            String userid = search_input.getText().toString().trim();
//            if (TextUtils.isEmpty(orderIdStr)) {
//                Toast.makeText(this, "Vui lòng nhập ID đơn hàng hoặc ID người dùng", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Long userId= Long.parseLong(userid);
//            getAllOrdersByUser(userId);
        });
        getAllOrders();

    }

<<<<<<< HEAD
    private void searchOrderById(Long orderId) {
        apiOrders = APIClient.getOrderById();
        String authToken = "Bearer " + token;

        Call<List<OrdersDTO>> call = apiOrders.getOrderById(authToken, orderId);

        call.enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrdersDTO> order = response.body();
                    ordersDTOS.clear();
                    ordersDTOS.add((OrdersDTO) order);
                    manageOrderAdapter.notifyDataSetChanged();
                } else {
                    Log.e("API_RESPONSE", "Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(ManageoderActivity.this, "Không tìm thấy đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrdersDTO>> call, Throwable t) {
                Log.e("API_CALL", "Error: " + t.getMessage());
                Toast.makeText(ManageoderActivity.this, "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllOrders() {
        apiOrders = APIClient.getAllOrders();
        String authToken = "Bearer " + token;

        Call<List<Order>> call = apiOrders.getAllOrders(authToken);

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Data: " + response.body().toString());
                    // Xóa danh sách cũ và thêm dữ liệu mới
                    ordersList.clear();
                    ordersList.addAll(response.body());
                    manageOrderAdapter.notifyDataSetChanged();
                    Toast.makeText(ManageoderActivity.this, "Tải danh sách đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API_RESPONSE", "Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(ManageoderActivity.this, "Không thể tải danh sách đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("API_CALL", "Error: " + t.getMessage());
                Toast.makeText(ManageoderActivity.this, "Lỗi : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void getAllOrdersByUser(Long userId) {
//        apiOrders = APIClient.getAllOrdersByUser();
//        String authToken = "Bearer " + token; // Token từ PreferenceManager
//
//        Call<List<OrdersDTO>> call = apiOrders.getAllOrdersByUser(authToken, userId);
//
//        call.enqueue(new Callback<List<OrdersDTO>>() {
//            @Override
//            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Xóa danh sách cũ và thêm dữ liệu mới
//                    ordersList.clear();
//                    ordersList.addAll(response.body());
//                    manageOrderAdapter.notifyDataSetChanged();
//                    Toast.makeText(ManageoderActivity.this, "Tải danh sách đơn hàng thành công!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.e("API_RESPONSE", "Code: " + response.code() + ", Message: " + response.message());
//                    Toast.makeText(ManageoderActivity.this, "Không thể tải danh sách đơn hàng!", Toast.LENGTH_SHORT).show();
=======
//    private void searchOrderById(int orderId) {
//        ApiOrders apiOrders = APIClient.getOrderById();
//
//        apiOrders.getOrderById(orderId).enqueue(new Callback<OrdersDTO>() {
//            @Override
//            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    OrdersDTO order = response.body();
//                    ListOrder.clear(); // Xóa danh sách cũ
//                    ListOrder.add(order); // Thêm kết quả mới vào danh sách
//                    manageOrderAdapter.notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
//                } else {
//                    Toast.makeText(ManageoderActivity.this, "Không tìm thấy đơn hàng với mã: " + orderId, Toast.LENGTH_SHORT).show();
>>>>>>> origin/main
//                }
//            }
//
//            @Override
<<<<<<< HEAD
//            public void onFailure(Call<List<OrdersDTO>> call, Throwable t) {
//                Log.e("API_CALL", "Error: " + t.getMessage());
//                Toast.makeText(ManageoderActivity.this, "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
=======
//            public void onFailure(Call<OrdersDTO> call, Throwable t) {
//                Toast.makeText(ManageoderActivity.this, "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
>>>>>>> origin/main
//            }
//        });
//    }
}