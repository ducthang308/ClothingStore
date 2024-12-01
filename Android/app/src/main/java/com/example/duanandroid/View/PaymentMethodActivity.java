package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.PaymentMethodAdapter;

public class PaymentMethodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> paymentMethods; // Danh sách các phương thức thanh toán
    private PaymentMethodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        recyclerView = findViewById(R.id.payment_method_list);

        // Tạo danh sách phương thức thanh toán
        paymentMethods = new ArrayList<>();
        paymentMethods.add("Thanh toán khi nhận hàng");
        paymentMethods.add("Thanh toán qua Banking");
        paymentMethods.add("Thanh toán qua ví điện tử");

        // Thiết lập RecyclerView
        adapter = new PaymentMethodAdapter(paymentMethods);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện khi người dùng chọn phương thức thanh toán
        adapter.setOnItemClickListener(new PaymentMethodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String selectedMethod) {
                // Gửi phương thức thanh toán đã chọn về BuyandpaymentActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedPaymentMethod", selectedMethod);
                setResult(RESULT_OK, resultIntent);
                finish(); // Quay lại màn hình trước đó (BuyandpaymentActivity)
            }
        });
    }
}

