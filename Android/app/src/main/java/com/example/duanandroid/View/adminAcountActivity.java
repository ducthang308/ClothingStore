package com.example.duanandroid.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class adminAcountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_acount);
        TextView tv_cus_manager = findViewById(R.id.tv_cus_manager);

        LinearLayout nav_account = findViewById(R.id.account);
        nav_account.setBackgroundColor(getResources().getColor(R.color.colorgray));


        tv_cus_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(adminAcountActivity.this, customerManagerActivity.class);
                startActivity(intent2);
            }
        });

        TextView odermanage = findViewById(R.id.oder_manage);
        odermanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, ManageoderActivity.class);
                startActivity(intent);
            }
        });

        TextView manageproduct = findViewById(R.id.manage_product);
        manageproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, ManageProductActivity.class);
                startActivity(intent);
            }
        });

        TextView tvHelp = findViewById(R.id.tv_help);
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminAcountActivity.this, ChatAdminActivity.class);
                intent.putExtra("previousActivity", "adminAccountActivity");
                startActivity(intent);
            }
        });

        LinearLayout backhome = findViewById(R.id.nav_home);
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

        TextView Admin_logout = findViewById(R.id.Admin_logout);
        Admin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(adminAcountActivity.this)
                        .setMessage("Bạn chắc chắn muốn đăng xuất?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(adminAcountActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
    }
}
