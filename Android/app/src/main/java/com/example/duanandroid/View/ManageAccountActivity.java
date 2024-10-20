package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import Fragment.TabLayOutActivity;

public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acctivity_manage_account);
        {
            TextView btn=  findViewById(R.id.view_all_oders);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 3);
                    startActivity(intent);
                }
            });

            LinearLayout btn1=  findViewById(R.id.waiting_payment_icon);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 0);
                    startActivity(intent);
                }
            });

            LinearLayout btn2=  findViewById(R.id.waiting_shipping_icon);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 1);
                    startActivity(intent);
                }
            });

            LinearLayout btn3=  findViewById(R.id.waiting_delivery_icon);
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 2);
                    startActivity(intent);
                }
            });

            LinearLayout btn4=  findViewById(R.id.review_icon);
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 3);
                    startActivity(intent);
                }
            });


            LinearLayout backhome=  findViewById(R.id.home);
            backhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);
                    intent.putExtra("tabPosition", 4);
                    startActivity(intent);
                }
            });

            RelativeLayout support=  findViewById(R.id.support);
            support.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, HelpActivity.class);
                    startActivity(intent);
                }
            });

            RelativeLayout changepass=  findViewById(R.id.changepassword);
            changepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, changePassActivity.class);
                    startActivity(intent);
                }
            });

            ImageView user=  findViewById(R.id.avatarImage);
            user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, editAccountActivity.class);
                    startActivity(intent);
                }
            });

            RelativeLayout chatwithshop=  findViewById(R.id.Chat_with_shop);
            chatwithshop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, chatUserActivity.class);
                    startActivity(intent);
                }
            });


            RelativeLayout myreview=  findViewById(R.id.My_review);
            myreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, TabLayOutActivity.class);

                    startActivity(intent);
                }
            });


            RelativeLayout myshoppingcart=  findViewById(R.id.My_shopping_cart);
            myshoppingcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ManageAccountActivity.this, CartActivity.class);
                    intent.putExtra("origin", "CartToAccount");
                    startActivity(intent);
                }
            });


//            LinearLayout userTab = findViewById(R.id.home);  // Tab user/account
//            ImageView userIcon = findViewById(R.id.userIcon);
//
//// Set OnClickListener for user tab
//            userTab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Set selected state for user icon
//                    userIcon.setSelected(true);
//                    // Deselect other tabs if needed
//                    // Example:
//                    ImageView homeIcon = findViewById(R.id.logo);  // Another tab
//                    homeIcon.setSelected(false);  // Deselect other tabs
//                }
//            });
        }
    }
}