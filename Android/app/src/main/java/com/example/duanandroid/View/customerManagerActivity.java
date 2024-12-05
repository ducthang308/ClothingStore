package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.customerAdapter;
import Interface.APIClient;
import Interface.ApiUsers;
import Interface.PreferenceManager;
import Model.Discount;
import Model.User;
import Model.customer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class customerManagerActivity extends AppCompatActivity {

    private RecyclerView rcvCustomer;
    private List<User> listCustomer;
    private customerAdapter cusAdapter;
    private ApiUsers apiUsers;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_manager);
        ImageButton img_arrow_customer = findViewById(R.id.arrow_customer);
        rcvCustomer = findViewById(R.id.rcv_customer);
        listCustomer = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cusAdapter = new customerAdapter(listCustomer, this, token);
        rcvCustomer.setLayoutManager(linearLayoutManager);
        rcvCustomer.setAdapter(cusAdapter);


        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();

        apiUsers = APIClient.getClient().create(ApiUsers.class);

        img_arrow_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_customer = new Intent(customerManagerActivity.this, adminAcountActivity.class);
                startActivity(intent_customer);
            }
        });

        loadCustomers();
    }

    private void loadCustomers() {
        Call<List<User>> call = apiUsers.getAllUser("Bearer " + token);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cusAdapter.updateData(response.body());
                } else {
                    int statusCode = response.code();
                    String errorMessage = response.message();
                    Toast.makeText(customerManagerActivity.this, "Error: " + statusCode + " - " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(customerManagerActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}