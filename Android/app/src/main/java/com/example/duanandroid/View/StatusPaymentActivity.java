package com.example.duanandroid.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class StatusPaymentActivity extends AppCompatActivity {

    // Giả định rằng bạn đã có những danh sách này
    private List<OrderDetail> orderDetails; // Danh sách chi tiết đơn hàng
    private List<Product> products;         // Danh sách sản phẩm
    private List<ProductImage> productImages; // Danh sách hình ảnh sản phẩm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentstatus);

        // Nạp dữ liệu cho danh sách (giả định bạn đã có dữ liệu)
        orderDetails = new ArrayList<>();
        products = new ArrayList<>();
        productImages = new ArrayList<>();

        // Hiển thị thông tin cho sản phẩm đầu tiên trong danh sách đơn hàng
        if (!orderDetails.isEmpty()) {
            OrderDetail orderDetail = orderDetails.get(0); // Lấy thông tin đơn hàng đầu tiên

            // Lấy thông tin sản phẩm từ productId
            Product product = findProductById(orderDetail.getProductId());
            String imageUrl = findImageUrlByProductId(orderDetail.getProductId());

            // Giả sử bạn đã khai báo các View trong layout
            TextView productNameTextView = findViewById(R.id.product_name);
            TextView productSizeTextView = findViewById(R.id.product_size);
            TextView productQuantityTextView = findViewById(R.id.product_quantity);
            TextView productPriceTextView = findViewById(R.id.product_price);
            TextView totalMoneyTextView = findViewById(R.id.total_money);
            ImageView productImageView = findViewById(R.id.product_image);

            // Hiển thị thông tin sản phẩm
            if (product != null) {
                productNameTextView.setText(product.getProductName());
                productSizeTextView.setText(product.getSize());
                productQuantityTextView.setText(String.valueOf(orderDetail.getNumberOfProduct()));
                productPriceTextView.setText(String.format("%,.0f đ", product.getPrice())); // Định dạng giá
                totalMoneyTextView.setText(String.format("%,.0f đ", orderDetail.getTotalMoney())); // Định dạng tổng tiền
                // Tải hình ảnh từ URL (bạn có thể sử dụng thư viện như Glide hoặc Picasso)
                // Glide.with(this).load(imageUrl).into(productImageView);
            }
        }
    }

    private Product findProductById(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null; // Nếu không tìm thấy sản phẩm
    }

    private String findImageUrlByProductId(int productId) {
        for (ProductImage productImage : productImages) {
            if (productImage.getProductId() == productId) {
                return productImage.getImageUrl();
            }
        }
        return null; // Nếu không tìm thấy hình ảnh
    }
}
