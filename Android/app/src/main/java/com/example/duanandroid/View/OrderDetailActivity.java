package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class OrderDetailActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyandpayment);

//        // Nhận dữ liệu từ Intent
//        String productName = getIntent().getStringExtra("productName");
//        float productPrice = getIntent().getFloatExtra("productPrice", 0);
//        String productSize = getIntent().getStringExtra("productSize");
//        int numberOfProduct = getIntent().getIntExtra("numberOfProduct", 1);
//        String address = getIntent().getStringExtra("address");
//        String paymentMethod = getIntent().getStringExtra("paymentMethod");
//        float totalMoney = getIntent().getFloatExtra("totalMoney", 0);
//        String productImageResource = getIntent().getStringExtra("imageURL");
//
//        // Tham chiếu đến các view trong layout activity_manage_oder
//        TextView nameTextView = findViewById(R.id.product_name);
//        TextView priceTextView = findViewById(R.id.product_price);
//        TextView sizeTextView = findViewById(R.id.product_size);
//        TextView quantityTextView = findViewById(R.id.product_quantity);
//        TextView totalMoneyTextView = findViewById(R.id.total_money);
//        TextView addressTextView = findViewById(R.id.address);
//        TextView paymentMethodTextView = findViewById(R.id.shipping_method);
//        ImageView imageView = findViewById(R.id.product_image);
//
//        // Hiển thị dữ liệu vào các view
//        nameTextView.setText(productName);
//        priceTextView.setText(String.format("₫%,.0f", productPrice)); // Định dạng giá tiền
//        sizeTextView.setText(productSize);
//        quantityTextView.setText(String.valueOf(numberOfProduct));
//        totalMoneyTextView.setText(String.format("₫%,.0f", totalMoney));
//        addressTextView.setText(address);
//        paymentMethodTextView.setText(paymentMethod);
//
//        // Hiển thị hình ảnh sản phẩm (nếu có)
//        if (productImageResource != null && !productImageResource.isEmpty()) {
//            // Nếu có URL ảnh thì sẽ load ảnh từ đó, nhưng trong trường hợp này chỉ sử dụng hình ảnh có sẵn
//            // Ví dụ: holder.productImage.setImageResource(R.drawable.tên_hình);
//            // Ở đây bạn có thể thêm logic để load hình từ URL tùy theo yêu cầu của ứng dụng
//        } else {
//            imageView.setImageResource(R.drawable.ao); // Hình ảnh mặc định nếu không có URL
//        }
    }
}
