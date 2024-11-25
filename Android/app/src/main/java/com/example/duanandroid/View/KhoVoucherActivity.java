package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.DiscountManageAdapter;
import Fragment.AccountUserFragment;
import Interface.APIClient;
import Interface.ApiDiscounts;
import Interface.PreferenceManager;
import Model.Discount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhoVoucherActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiscountManageAdapter adapter;
    private List<Discount> discountList = new ArrayList<>();
    private ApiDiscounts apiDiscounts;
    private Button btnAdd, btnEdit, btnDelete;
    private String token;
    private Discount selectedDiscount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_voucher);
        recyclerView = findViewById(R.id.recycleView_khoVoucher);

        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView not found. Check if the ID 'recycleView' exists in activity_kho_voucher.xml");
        }

        adapter = new DiscountManageAdapter(this, discountList, discount -> {
            selectedDiscount = discount;
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        apiDiscounts = APIClient.getDiscounts();

        loadDiscounts();

        TextView lichsu = findViewById(R.id.tv_lichsu);
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhoVoucherActivity.this, LichSuVoucherActivity.class);
                startActivity(intent);
            }
        });
        ImageButton arrow_voucher = findViewById(R.id.arrow_voucher);
        arrow_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KhoVoucherActivity.this, AccountUserFragment.class);
                finish();
            }
        });
    }


    private void loadDiscounts() {
        Call<List<Discount>> call = apiDiscounts.getDiscounts();
        call.enqueue(new Callback<List<Discount>>() {
            @Override
            public void onResponse(Call<List<Discount>> call, Response<List<Discount>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    discountList.clear();
                    discountList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    String errorMessage = response.message();
                    Toast.makeText(KhoVoucherActivity.this, "Error: " + statusCode + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Discount>> call, Throwable t) {
                Toast.makeText(KhoVoucherActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
