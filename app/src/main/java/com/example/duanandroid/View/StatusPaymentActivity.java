package com.example.duanandroid.View;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class StatusPaymentActivity extends AppCompatActivity {
    private LinearLayout layoutProductButtons;
    private LinearLayout layoutShippingButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_status); // Replace with your layout

        // Initialize button layouts
        layoutProductButtons = findViewById(R.id.layout_product_buttons);
        layoutShippingButtons = findViewById(R.id.layout_shipping_buttons);

        // Tab "Waiting for shipping"
        TextView tabWaitingShipping = findViewById(R.id.tab_waiting_shipping);

        // Handle "Waiting for shipping" tab click
        tabWaitingShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the product buttons
                layoutProductButtons.setVisibility(View.GONE);

                // Show the shipping buttons
                layoutShippingButtons.setVisibility(View.VISIBLE);
            }
        });
    }
}

