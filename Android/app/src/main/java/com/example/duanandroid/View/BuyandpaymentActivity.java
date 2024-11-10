package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.BuyAndPaymentAdapter;
import Model.OrderDetail;
import Model.Product1;
import Model.ProductImage;

public class BuyandpaymentActivity extends AppCompatActivity {
    private RecyclerView rcv_buyandpayment;
    private BuyAndPaymentAdapter buyAndPaymentAdapter;
    private List<Product1> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;
    private String origin;

    private static final int REQUEST_VOUCHER_SELECTION = 1; // Thêm hằng số để xác định request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyandpayment);

        // Ánh xạ RecyclerView từ layout
        rcv_buyandpayment = findViewById(R.id.rcv_payandBuy);
        rcv_buyandpayment.setLayoutManager(new GridLayoutManager(this, 1));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // Khởi tạo danh sách sản phẩm, order detail, và hình ảnh
        productList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        productImageList = loadProductImages();

        // Thêm dữ liệu mẫu vào danh sách sản phẩm
        Product1 pr1 = new Product1(1, "Áo thun ngắn tay cho nữ", 2, "Red", "M", 200000, 20, 100);
        productList.add(pr1);

        orderDetailList.add(new OrderDetail(1, 1, 1, 200000, 1, 200000, null));

        // Khởi tạo Adapter và kết nối với RecyclerView
        buyAndPaymentAdapter = new BuyAndPaymentAdapter(this, productList, orderDetailList, productImageList);
        rcv_buyandpayment.setLayoutManager(linearLayoutManager);
        rcv_buyandpayment.setAdapter(buyAndPaymentAdapter);

        // Lấy giá trị "origin" từ intent
        Intent intent = getIntent();
        origin = intent.getStringExtra("origin");

        // Thiết lập sự kiện cho nút "Đặt hàng"
        TextView btn = findViewById(R.id.btn_order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyandpaymentActivity.this, mainpageActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện chọn voucher và dùng startActivityForResult
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        LinearLayout selectVoucher = findViewById(R.id.click_voucher);
        selectVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyandpaymentActivity.this, SelectVoucherActivity.class);
                startActivityForResult(intent, REQUEST_VOUCHER_SELECTION);
            }
        });

        // Thiết lập sự kiện nút back
        ImageView back = findViewById(R.id.back_arrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBackNavigation();
            }
        });
    }

    private void handleBackNavigation() {
        if ("cart".equals(origin)) {
            finish();
        } else if ("order_details".equals(origin)) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VOUCHER_SELECTION) {
            // Xử lý dữ liệu khi quay lại từ SelectVoucherActivity
            if (resultCode == RESULT_OK) {
                // Nhận dữ liệu voucher nếu có và xử lý ở đây
            }
        }
    }

    // Tạo dữ liệu hình ảnh mẫu
    private List<ProductImage> loadProductImages() {
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            String imageName = "ao";
        }
        for (int i = 0; i <=1; i++) {
            String imageName = "ao" ;
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            productImages.add(new ProductImage(i, i, String.valueOf(imageResId)));
        }
        return productImages;
    }
}