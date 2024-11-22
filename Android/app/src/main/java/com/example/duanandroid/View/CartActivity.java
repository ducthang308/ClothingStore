package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CartAdapter;
import Model.CartItem;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCartItems;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private CheckBox cbxSelectAll;
    private TextView tvGia; // Hiển thị tổng giá

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ánh xạ các thành phần trong layout
        recyclerCartItems = findViewById(R.id.recycler_cart_items);
        cbxSelectAll = findViewById(R.id.cbx_select_all);
        tvGia = findViewById(R.id.tv_gia);
        recyclerCartItems.setLayoutManager(new LinearLayoutManager(this));

        // Tạo dữ liệu mẫu
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao, false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        cartItems.add(new CartItem("Áo thun ngắn tay nữ", "trắng, size M", 200000, R.drawable.ao,false));
        // Thiết lập Adapter
        cartAdapter = new CartAdapter(cartItems, this::onCartItemSelectionChanged);
        recyclerCartItems.setAdapter(cartAdapter);

        // Nút "Thanh toán"
        TextView btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, BuyandpaymentActivity.class);
            intent.putExtra("origin", "cart");
            startActivity(intent);
        });

        // Checkbox "Chọn tất cả"
        cbxSelectAll.setOnClickListener(v -> {
            boolean isChecked = cbxSelectAll.isChecked();
            cartAdapter.selectAllItems(isChecked);
            calculateTotalPrice();
        });

        // Nút quay lại
        ImageButton backArrow = findViewById(R.id.btn_back);
        backArrow.setOnClickListener(view -> finish());

        // Biểu tượng Chat
        ImageView chatIcon = findViewById(R.id.chat);
        chatIcon.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, chatUserActivity.class);
            startActivity(intent);
        });

        // Tính tổng giá ban đầu
        calculateTotalPrice();
    }

    /**
     * Cập nhật trạng thái Checkbox "Chọn tất cả" và tính tổng giá
     */
    private void onCartItemSelectionChanged() {
        updateSelectAllCheckBox();
        calculateTotalPrice();
    }

    /**
     * Cập nhật trạng thái của Checkbox "Chọn tất cả"
     */
    private void updateSelectAllCheckBox() {
        boolean allSelected = true;
        for (CartItem item : cartItems) {
            if (!item.isSelected()) {
                allSelected = false;
                break;
            }
        }
        cbxSelectAll.setChecked(allSelected);
    }

    /**
     * Tính tổng giá của các sản phẩm đã được chọn
     */
    private void calculateTotalPrice() {
        float totalPrice = 0;
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                totalPrice += item.getPrice();
            }
        }
        // Hiển thị tổng giá với định dạng "xxx,xxx đ"
        tvGia.setText(String.format("%,.0f đ", totalPrice));
    }
}
