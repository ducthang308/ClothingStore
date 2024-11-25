package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import Interface.PreferenceManager;
//import com.example.duanandroid.databinding.ActivityAdminAcountBinding;

public class adminAcountActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_acount);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_cus_manager = findViewById(R.id.tv_cus_manager);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout nav_account = findViewById(R.id.account);
        nav_account.setBackgroundColor(getResources().getColor(R.color.colorgray));
        tv_cus_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(adminAcountActivity.this, customerManagerActivity.class);
                startActivity(intent2);
            }
        });

        TextView odermanage=  findViewById(R.id.oder_manage);
        odermanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, ManageoderActivity.class);
                startActivity(intent);
            }
        });


        TextView manageproduct=  findViewById(R.id.manage_product);
        manageproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, ManageProductActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout backhome=  findViewById(R.id.nav_home);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, mainpageAdminActivity.class);
                startActivity(intent);
            }
        });
        TextView tv_discount = findViewById(R.id.tv_discount_manage);
        tv_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, DiscountManageActivity.class);
                startActivity(intent);
            }
        });

        TextView tv_quanlydanhmuc = findViewById(R.id.tv_quanlydanhmuc);
        tv_quanlydanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, QuanLiDanhMucSPActivity.class);
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView Admin_logout = findViewById(R.id.Admin_logout);
        Admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String userName = preferenceManager.getName();

        TextView nameAdmin = findViewById(R.id.name_admin);
        if (userName != null && !userName.isEmpty()) {
            nameAdmin.setText(userName);
        }
    }

}