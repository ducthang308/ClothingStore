package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.BuyAndPaymentAdapter;
import DTO.OrderDetailDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyandpaymentActivity extends AppCompatActivity {
    private TextView totalCostTextView;
    private TextView totalPaymentTextView1;
    private TextView totalPaymentTextView;
    private RecyclerView rcvBuyAndPayment;
    private BuyAndPaymentAdapter buyAndPaymentAdapter;
    private List<ProductDTO> productList;
    private String origin;
    private static final int REQUEST_VOUCHER_SELECTION = 1;
    private static final int REQUEST_PAYMENT_METHOD_SELECTION = 2;
    private TextView txtAddress;
    private TextView txtVoucher;
    private TextView shippingMethodText;
    private int discountId;
    private int[] quantities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyandpayment);

        // Initializing views
        initializeViews();

        // Retrieve data from intent
        productList = (List<ProductDTO>) getIntent().getSerializableExtra("productList");
        if (productList == null) {
            productList = new ArrayList<>();
        }

        // Setting up adapter and RecyclerView
        buyAndPaymentAdapter = new BuyAndPaymentAdapter(productList, this);
        rcvBuyAndPayment.setLayoutManager(new LinearLayoutManager(this));
        rcvBuyAndPayment.setAdapter(buyAndPaymentAdapter);

        // Calculating total cost
        calculateTotalCost();

        // Handling order button click
        findViewById(R.id.btn_order).setOnClickListener(view -> handleOrder(buyAndPaymentAdapter.getQuantities()));

        // Handling voucher selection
        findViewById(R.id.click_voucher).setOnClickListener(view -> {
            Intent intent = new Intent(BuyandpaymentActivity.this, SelectVoucherActivity.class);
            startActivityForResult(intent, REQUEST_VOUCHER_SELECTION);
        });

        // Handling payment method selection
        shippingMethodText.setOnClickListener(view -> {
            Intent intent = new Intent(BuyandpaymentActivity.this, PaymentMethodActivity.class);
            startActivityForResult(intent, REQUEST_PAYMENT_METHOD_SELECTION);
            calculateTotalCost();
        });

        // Handling back navigation
        findViewById(R.id.back_arrow).setOnClickListener(view -> handleBackNavigation());
    }

    private void initializeViews() {
        totalCostTextView = findViewById(R.id.shipping_cost);
        totalPaymentTextView = findViewById(R.id.total_payment);
        totalPaymentTextView1 = findViewById(R.id.total_payment1);
        rcvBuyAndPayment = findViewById(R.id.rcv_payandBuy);
        txtAddress = findViewById(R.id.address);
        txtVoucher = findViewById(R.id.discount);
        shippingMethodText = findViewById(R.id.shipping_method);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        String address = preferenceManager.getAddress();
        txtAddress.setText(address != null && !address.isEmpty() ? address : "Chưa có địa chỉ được lưu");
    }

    private void handleBackNavigation() {
        if ("cart".equals(origin) || "order_details".equals(origin)) {
            finish();
        } else {
            Toast.makeText(this, "Quay lại không xác định!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Nhận kết quả từ các activity con
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VOUCHER_SELECTION && resultCode == RESULT_OK && data != null) {
            // Lấy thông tin từ SelectVoucherActivity
            String voucherNote = data.getStringExtra("selected_voucher_note");
            double voucherPercent = data.getDoubleExtra("selected_voucher_percent", 0);
            int selectedDiscountId = data.getIntExtra("selected_voucher_id", -1); // Lấy ID của discount

            // Cập nhật discountId
            if (selectedDiscountId != -1) {
                discountId = selectedDiscountId;
            }

            // Hiển thị voucher đã chọn
            if (voucherPercent > 0) {
                txtVoucher.setText(String.format("%s (%.0f%%)", voucherNote, voucherPercent * 100));
            } else {
                txtVoucher.setText(voucherNote); // Chỉ hiển thị ghi chú của voucher
            }

            // Cập nhật tổng tiền sau khi áp dụng giảm giá
            calculateTotalCost();
        }

        if (requestCode == REQUEST_PAYMENT_METHOD_SELECTION && resultCode == RESULT_OK && data != null) {
            // Xử lý kết quả từ PaymentMethodActivity
            String selectedMethod = data.getStringExtra("selectedPaymentMethod");
            if (selectedMethod != null) {
                shippingMethodText.setText(selectedMethod);
            }
        }
    }

    public void handleOrder(int[] quantities) {
        if (productList.isEmpty()) {
            Toast.makeText(this, "Không có sản phẩm để đặt hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int quantity : quantities) {
            if (quantity <= 0) {
                Toast.makeText(this, "Số lượng sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        PreferenceManager preferenceManager = new PreferenceManager(this);
        int userId = preferenceManager.getUserId();
        if (userId == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = shippingMethodText.getText().toString().trim();
        if (paymentMethod.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        float totalPayment = calculateTotalPayment(quantities);
        if (totalPayment <= 0) {
            Toast.makeText(this, "Tổng thanh toán không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        OrdersDTO orderData = createOrderData(userId, totalPayment, paymentMethod);
        String token = preferenceManager.getToken(); // Lấy token từ PreferenceManager
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Token không hợp lệ, vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiOrders apiOrders = APIClient.getClient().create(ApiOrders.class);
        Call<OrdersDTO> callCreateOrder = apiOrders.createOrder("Bearer " + token, orderData);
        handleOrderApiResponse(callCreateOrder, quantities);
    }

    private float calculateTotalPayment(int[] quantities) {
        float totalPayment = 0f;
        for (int i = 0; i < productList.size(); i++) {
            ProductDTO product = productList.get(i);
            int quantity = quantities[i];
            totalPayment += product.getPrice() * quantity;
        }

        String voucherText = txtVoucher.getText().toString();
        float discountPercent = (float) extractDiscountPercent(voucherText);
        float totalDiscount = totalPayment * discountPercent;
        totalPayment -= totalDiscount;

        return totalPayment;
    }

    private double extractDiscountPercent(String voucherText) {
        if (voucherText.matches(".*\\d+%.*")) {
            try {
                String percentString = voucherText.replaceAll("[^\\d]", "");
                return Double.parseDouble(percentString) / 100.0;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Voucher không hợp lệ, không thể trích xuất giảm giá.", Toast.LENGTH_SHORT).show();
            }
        }
        return 0;
    }

    private OrdersDTO createOrderData(int userId, float totalPayment, String paymentMethod) {
        Date orderDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = sdf.format(orderDate);

        OrdersDTO orderData = new OrdersDTO();
        orderData.setUserId(userId);
        orderData.setTotalMoney(totalPayment);
        orderData.setOrderDate(formattedDate);
        orderData.setPaymentMethod(paymentMethod);
        orderData.setDiscounts(discountId);
        orderData.setStatus("Waiting for delivery");
        return orderData;
    }

    private void handleOrderApiResponse(Call<OrdersDTO> call, int[] quantities) {
        call.enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                         int orderId = response.body().getId();
                        Log.d("OrderResponse", "Order ID: " + orderId);

                        if (orderId > 0) {
                            Toast.makeText(BuyandpaymentActivity.this, "Tạo đơn hàng thành công! Order ID: " + orderId, Toast.LENGTH_SHORT).show();
                            createOrderDetails(orderId, quantities);
                        } else {
                            Log.e("OrderError", "Order ID không hợp lệ: " + orderId);
                            Toast.makeText(BuyandpaymentActivity.this, "Order ID không hợp lệ.", Toast.LENGTH_LONG).show();
                        }
                    } catch (NumberFormatException e) {
                        Log.e("OrderError", "Không thể parse Order ID từ response: " + response.body(), e);
                        Toast.makeText(BuyandpaymentActivity.this, "Lỗi khi xử lý Order ID.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Không rõ lỗi.";
                        Log.e("OrderError", "Tạo đơn hàng thất bại: " + errorMessage);
                        Toast.makeText(BuyandpaymentActivity.this, "Tạo đơn hàng thất bại: " + errorMessage, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("OrderError", "Không thể đọc lỗi từ API.", e);
                        Toast.makeText(BuyandpaymentActivity.this, "Không thể đọc lỗi từ API.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Log.e("OrderError", "Lỗi khi gọi API tạo đơn hàng.", t);
                Toast.makeText(BuyandpaymentActivity.this, "Lỗi khi gọi API tạo đơn hàng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createOrderDetails(int orderId, int[] quantities) {
        ApiOrderDetail apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);

        if (productList.size() != quantities.length) {
            Log.e("OrderDetailsError", "Kích thước productList và quantities không khớp.");
            Toast.makeText(this, "Kích thước danh sách sản phẩm và số lượng không khớp.", Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < productList.size(); i++) {
            ProductDTO product = productList.get(i);
            int quantity = quantities[i];

            if (quantity <= 0) {
                Log.e("OrderDetailsError", "Số lượng sản phẩm không hợp lệ: " + product.getProductName());
                Toast.makeText(this, "Số lượng sản phẩm không hợp lệ: " + product.getProductName(), Toast.LENGTH_SHORT).show();
                continue;
            }

            // Tính tổng tiền cho sản phẩm
            Float totalMoney = product.getPrice() * quantity;

            int productId = product.getId();

            OrderDetailDTO orderDetailData = new OrderDetailDTO();
            orderDetailData.setOrderId(orderId); // Sử dụng Order ID từ API
            orderDetailData.setProductId(productId);
            orderDetailData.setNumberOfProduct(quantity);
            orderDetailData.setPrice(product.getPrice());
            orderDetailData.setTotalMoney(totalMoney);

            Log.d("OrderDetailDTO", "OrderDetail: " + orderDetailData.toString());

            PreferenceManager preferenceManager = new PreferenceManager(this);
            String token = preferenceManager.getToken();
            Call<String> callCreateOrderDetail = apiOrderDetail.createOrderdetail("Bearer " + token, orderDetailData);

            callCreateOrderDetail.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.d("OrderDetailSuccess", "Tạo chi tiết đơn hàng thành công! Sản phẩm: " + product.getProductName());
                        Toast.makeText(BuyandpaymentActivity.this, "Tạo chi tiết đơn hàng thành công! Sản phẩm: " + product.getProductName(), Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Không rõ lỗi.";
                            Log.e("OrderDetailError", "Tạo chi tiết đơn hàng thất bại: " + errorMessage);
                            Toast.makeText(BuyandpaymentActivity.this, "Tạo chi tiết đơn hàng thất bại: " + errorMessage, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e("OrderDetailError", "Không thể đọc lỗi từ API.", e);
                            Toast.makeText(BuyandpaymentActivity.this, "Không thể đọc lỗi từ API.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("OrderDetailError", "Lỗi khi gọi API tạo chi tiết đơn hàng.", t);
                    Toast.makeText(BuyandpaymentActivity.this, "Lỗi khi gọi API tạo chi tiết đơn hàng: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    public void calculateTotalCost() {
        float totalCost = 0f;
        for (ProductDTO product : productList) {
            totalCost += product.getPrice();
        }
        totalCostTextView.setText(String.format("Tổng tiền: %.2f VNĐ", totalCost));

        String voucherText = txtVoucher.getText().toString().trim();
        if (voucherText.matches(".*\\d+%.*")) {
            float discountPercent = (float) extractDiscountPercent(voucherText);
            float totalDiscount = totalCost * discountPercent;
            float totalPayment = totalCost - totalDiscount;

            totalPaymentTextView.setText(String.format("Thanh toán: %.2f VNĐ (đã giảm %s)", totalPayment, voucherText));
            totalPaymentTextView1.setText(String.format("Thanh toán: %.2f VNĐ", totalPayment));
        } else {
            totalPaymentTextView.setText(String.format("Thanh toán: %.2f VNĐ", totalCost));
            totalPaymentTextView1.setText(String.format("Thanh toán: %.2f VNĐ", totalCost));
        }
    }
}
