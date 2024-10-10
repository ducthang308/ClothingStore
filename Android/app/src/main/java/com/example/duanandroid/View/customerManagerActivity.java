package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.customerAdapter;
import Model.customer;

public class customerManagerActivity extends AppCompatActivity {

    private RecyclerView rcvCustomer;
    private List<customer> listCustomer;
    private customerAdapter cusAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_manager);
        ImageButton img_arrow_customer = findViewById(R.id.arrow_customer);
        rcvCustomer = findViewById(R.id.rcv_customer);
        listCustomer = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cusAdapter = new customerAdapter(listCustomer);
        rcvCustomer.setLayoutManager(linearLayoutManager);
        rcvCustomer.setAdapter(cusAdapter);

        customer customer1= new customer("Nguyen Thi Thao","0356621341");
        customer customer2= new customer("Nguyen Van Thang","031234566");
        customer customer3= new customer("Nguyen Duc Thang","045225477");
        customer customer4= new customer("Nguyen Thi Diem","0363545766");
        customer customer5= new customer("Nguyen A","0123456787");
        customer customer6= new customer("Nguyen Thi Thao","0123876592");
        customer customer7= new customer("Nguyen Van Thang","0912765834");
        customer customer8= new customer("Nguyen Duc Thang","0325836599");

        listCustomer.add(customer1);
        listCustomer.add(customer2);
        listCustomer.add(customer3);
        listCustomer.add(customer4);
        listCustomer.add(customer5);
        listCustomer.add(customer6);
        listCustomer.add(customer7);
        listCustomer.add(customer8);
        img_arrow_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_customer = new Intent(customerManagerActivity.this, adminAcountActivity.class);
                startActivity(intent_customer);
            }
        });


    }

}