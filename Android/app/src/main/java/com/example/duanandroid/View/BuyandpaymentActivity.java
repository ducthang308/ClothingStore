package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.BuyAndPaymentAdapter;
import DTO.CartItemsDTO;
import DTO.OrderDetailDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import Fragment.TabLayOutActivity;
import Fragment.WaitingForDeliveryFragment;
import Interface.APIClient;
import Interface.ApiCartItems;
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
    private List<CartItemsDTO> cartItemsList;
    private float voucherPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyandpayment);

        initializeViews();

        Intent intent = getIntent();
        String origin = intent.getStringExtra("origin");

        cartItemsList = new ArrayList<>();

        if ("cart".equals(origin)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                List<Integer> idsCart = bundle.getIntegerArrayList("idsCart");
                List<Integer> ids = bundle.getIntegerArrayList("ids");
                List<String> names = bundle.getStringArrayList("names");
                List<String> images = bundle.getStringArrayList("images");
                List<Float> prices = (List<Float>) bundle.getSerializable("prices");
                ArrayList<Integer> quantities = intent.getIntegerArrayListExtra("quantities");

                if (ids != null && names != null && images != null && prices != null) {
                    for (int i = 0; i < ids.size(); i++) {
                        CartItemsDTO cartItem = new CartItemsDTO();
                        cartItem.setId(idsCart.get(i));
                        cartItem.setProductId(ids.get(i));
                        cartItem.setProductName(names.get(i));
                        cartItem.setImageUrl(images.get(i));
                        cartItem.setPrice(prices.get(i));
                        cartItem.setQuantity(quantities.get(i));
                        cartItemsList.add(cartItem);
                    }
                }
            }
            calculateTotalCostForCart();
        } else if ("chitietsanpham".equals(origin)) {
            productList = (ArrayList<ProductDTO>) intent.getSerializableExtra("productList");
            updateTotalCostView(calculateTotalCost());
        }

        buyAndPaymentAdapter = new BuyAndPaymentAdapter(productList, cartItemsList, this);
        rcvBuyAndPayment.setLayoutManager(new LinearLayoutManager(this));
        rcvBuyAndPayment.setAdapter(buyAndPaymentAdapter);

        if (cartItemsList.isEmpty()) {
            cartItemsList = new ArrayList<>();
        }

        if (productList == null) {
            productList = new ArrayList<>();
        }

        buyAndPaymentAdapter.setOnQuantityChangeListener(this::calculateTotalCostForCurrentOrigin);

        findViewById(R.id.btn_order).setOnClickListener(view -> {
            List<Integer> updatedQuantities = buyAndPaymentAdapter.getUpdatedQuantities();

            int[] quantitiesArray = new int[updatedQuantities.size()];
            for (int i = 0; i < updatedQuantities.size(); i++) {
                quantitiesArray[i] = updatedQuantities.get(i);
            }

            if (quantitiesArray.length > 0) {
                handleOrder(quantitiesArray, cartItemsList);

                // Xóa các sản phẩm khỏi giỏ hàng sau khi đặt hàng
                for (CartItemsDTO cartItem : cartItemsList) {
                    deleteCartItem(cartItem.getProductId());
                }

                Intent intent1 = new Intent(BuyandpaymentActivity.this, TabLayOutActivity.class);
                intent1.putExtra("tabPosition", 0);
                startActivity(intent1);
            } else {
                Toast.makeText(this, "Không có sản phẩm để đặt hàng.", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.click_voucher).setOnClickListener(view -> {
            Intent voucherIntent = new Intent(BuyandpaymentActivity.this, SelectVoucherActivity.class);
            startActivityForResult(voucherIntent, REQUEST_VOUCHER_SELECTION);
        });

        shippingMethodText.setOnClickListener(view -> {
            Intent paymentIntent = new Intent(BuyandpaymentActivity.this, PaymentMethodActivity.class);
            startActivityForResult(paymentIntent, REQUEST_PAYMENT_METHOD_SELECTION);
            calculateTotalCost();
        });

        findViewById(R.id.back_arrow).setOnClickListener(view -> {
            onBackPressed();
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VOUCHER_SELECTION && resultCode == RESULT_OK && data != null) {
            String voucherNote = data.getStringExtra("selected_voucher_note");
            voucherPercent = data.getFloatExtra("selected_voucher_percent", 0);
            int selectedDiscountId = data.getIntExtra("selected_voucher_id", -1);

            if (selectedDiscountId == -1) {
                voucherNote = "Default Voucher";
                voucherPercent = 0.1f;
                selectedDiscountId = 1;
            }

            discountId = selectedDiscountId;

            if (voucherPercent > 0) {
                txtVoucher.setText(String.format("%s (%.0f%%)", voucherNote, voucherPercent * 100));
            } else {
                txtVoucher.setText(voucherNote);
            }

            if ("cart".equals(origin)) {
                calculateTotalCostForCart();
            } else if ("chitietsanpham".equals(origin)) {
                calculateTotalCost();
            }
        }
        updateTotalCostView(calculateTotalCost());
    }


    public void handleOrder(int[] quantities, List<CartItemsDTO> selectedProducts) {
        if ((selectedProducts == null || selectedProducts.isEmpty()) && (cartItemsList == null || cartItemsList.isEmpty())) {
            showToast("Không có sản phẩm để đặt hàng!");
            return;
        }

        for (int quantity : quantities) {
            if (quantity <= 0) {
                showToast("Số lượng sản phẩm không hợp lệ!");
                return;
            }
        }

        PreferenceManager preferenceManager = new PreferenceManager(this);
        int userId = preferenceManager.getUserId();
        if (userId == -1) {
            showToast("Không tìm thấy thông tin người dùng!");
            return;
        }

        String paymentMethod = shippingMethodText.getText() != null ? shippingMethodText.getText().toString().trim() : "";
        if (paymentMethod.isEmpty()) {
            showToast("Vui lòng chọn phương thức thanh toán!");
            return;
        }

        float totalPayment = calculateTotalCost();
        if (discountId > 0) {
            totalPayment *= voucherPercent;
        }

        if (totalPayment <= 0) {
            showToast("Tổng thanh toán không hợp lệ!");
            return;
        }

        OrdersDTO orderData = createOrderData(userId, totalPayment, paymentMethod);

        String token = preferenceManager.getToken();
        if (token == null || token.isEmpty()) {
            showToast("Token không hợp lệ, vui lòng đăng nhập lại!");
            return;
        }

        ApiOrders apiOrders = APIClient.getClient().create(ApiOrders.class);
        Call<OrdersDTO> callCreateOrder = apiOrders.createOrder("Bearer " + token, orderData);

        // Truyền sản phẩm phù hợp vào hàm xử lý API
        if (selectedProducts != null && !selectedProducts.isEmpty()) {
            handleOrderApiResponse(callCreateOrder, quantities, selectedProducts);
        } else {
            handleOrderApiResponse(callCreateOrder, quantities, cartItemsList);
        }
    }

    public void buyFromProductDetails(int productId, int selectedQuantity) {
        if (selectedQuantity <= 0) {
            showToast("Số lượng sản phẩm không hợp lệ!");
            return;
        }

        // Tạo sản phẩm tạm thời
        CartItemsDTO product = new CartItemsDTO();
        product.setProductId(productId);
        product.setProductName("Tên sản phẩm"); // Lấy từ giao diện
        product.setPrice(100.0f); // Giá sản phẩm, lấy từ giao diện

        List<CartItemsDTO> selectedProducts = new ArrayList<>();
        selectedProducts.add(product);

        // Truyền danh sách sản phẩm và số lượng vào handleOrder
        int[] quantities = new int[]{selectedQuantity};
        handleOrder(quantities, selectedProducts);
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

        if (discountId <= 0) {
            orderData.setDiscounts(null);
        } else {
            orderData.setDiscounts(discountId);
        }

        orderData.setStatus("Waiting for delivery");
        return orderData;
    }


    private void handleOrderApiResponse(Call<OrdersDTO> call, int[] quantities, List<CartItemsDTO> productList) {
        call.enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int orderId = response.body().getId();
                    Log.d("OrderResponse", "Order ID: " + orderId);

                    if (orderId > 0) {
                        Toast.makeText(BuyandpaymentActivity.this, "Tạo đơn hàng thành công! Order ID: " + orderId, Toast.LENGTH_SHORT).show();

                        // Tạo chi tiết đơn hàng với danh sách sản phẩm
                        createOrderDetails(orderId, quantities, productList);
                    } else {
                        Log.e("OrderError", "Order ID không hợp lệ: " + orderId);
                        Toast.makeText(BuyandpaymentActivity.this, "Order ID không hợp lệ.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Log.e("OrderError", "Lỗi khi gọi API tạo đơn hàng.", t);
                Toast.makeText(BuyandpaymentActivity.this, "Lỗi khi gọi API tạo đơn hàng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void createOrderDetails(int orderId, int[] quantities, List<CartItemsDTO> productList) {
        ApiOrderDetail apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);

        if (!productList.isEmpty()) {
            for (int i = 0; i < productList.size(); i++) {
                CartItemsDTO cartItem = productList.get(i);
                int quantity = quantities[i];

                if (quantity <= 0) {
                    Log.e("OrderDetailsError", "Số lượng sản phẩm không hợp lệ: " + cartItem.getProductName());
                    Toast.makeText(this, "Số lượng sản phẩm không hợp lệ: " + cartItem.getProductName(), Toast.LENGTH_SHORT).show();
                    continue;
                }

                float totalMoney = cartItem.getPrice() * quantity;

                OrderDetailDTO orderDetailData = new OrderDetailDTO();
                orderDetailData.setOrderId(orderId);
                orderDetailData.setProductId(cartItem.getProductId());
                orderDetailData.setNumberOfProduct(quantity);
                orderDetailData.setPrice(cartItem.getPrice());
                orderDetailData.setTotalMoney(totalMoney);

                sendOrderDetailToApi(apiOrderDetail, orderDetailData, cartItem.getProductName());
            }
        } else {
            Log.e("OrderDetailsError", "Không có sản phẩm nào để tạo chi tiết đơn hàng.");
            Toast.makeText(this, "Không có sản phẩm nào để tạo chi tiết đơn hàng.", Toast.LENGTH_LONG).show();
        }
    }



    private void sendOrderDetailToApi(ApiOrderDetail apiOrderDetail, OrderDetailDTO orderDetailData, String productName) {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String token = preferenceManager.getToken();

        Call<String> callCreateOrderDetail = apiOrderDetail.createOrderdetail("Bearer " + token, orderDetailData);
        callCreateOrderDetail.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("OrderDetailSuccess", "Tạo chi tiết đơn hàng thành công! Sản phẩm: " + productName);
                    Toast.makeText(BuyandpaymentActivity.this, "Tạo chi tiết đơn hàng thành công! Sản phẩm: " + productName, Toast.LENGTH_SHORT).show();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("OrderDetailError", "Lỗi khi gọi API tạo chi tiết đơn hàng.", t);
                Toast.makeText(BuyandpaymentActivity.this, "Lỗi khi gọi API tạo chi tiết đơn hàng: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void handleErrorResponse(Response<?> response) {
        try {
            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Không rõ lỗi.";
            Log.e("OrderError", "Tạo đơn hàng thất bại: " + errorMessage);
            Toast.makeText(BuyandpaymentActivity.this, "Tạo đơn hàng thất bại: " + errorMessage, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("OrderError", "Không thể đọc lỗi từ API.", e);
            Toast.makeText(BuyandpaymentActivity.this, "Không thể đọc lỗi từ API.", Toast.LENGTH_LONG).show();
        }
    }

    private void calculateTotalCostForCurrentOrigin(int position, int updatedQuantity) {
        if ("cart".equals(origin)) {
            calculateTotalCostForCart();
        } else if ("chitietsanpham".equals(origin)) {
            calculateTotalCost();
        }
    }

    public void calculateTotalCostForCart() {
        float totalCost = 0f;
        for (CartItemsDTO cartItem : cartItemsList) {
            if(voucherPercent == 0) {
                totalCost += cartItem.getPrice() * cartItem.getQuantity();
            }else{
                totalCost += cartItem.getPrice() * cartItem.getQuantity() * voucherPercent;
            }
        }

        updateTotalCostView(totalCost);
    }

    private float calculateTotalCost() {
        float totalCost = 0;
        if (cartItemsList != null && !cartItemsList.isEmpty()) {
            for (CartItemsDTO cartItem : cartItemsList) {
                totalCost += cartItem.getPrice() * cartItem.getQuantity();
            }
        } else {
            for (ProductDTO product : productList) {
                totalCost += product.getPrice() * 1;
            }
        }
        return totalCost;
    }

    private void updateTotalCostView(float totalCost) {
        totalCostTextView.setText(String.format("%,.0f VNĐ", totalCost));

        String voucherText = txtVoucher.getText().toString().trim();
        if (voucherText.matches(".*\\d+%.*")) {
            float totalDiscount = totalCost * voucherPercent;
            float totalPayment = totalCost - totalDiscount;

            totalPaymentTextView.setText(String.format("%,.0f VNĐ (đã giảm %s)", totalPayment, voucherPercent*100));
            totalPaymentTextView1.setText(String.format("%,.0f VNĐ", totalPayment));
        } else {
            totalPaymentTextView.setText(String.format("%,.0f VNĐ", totalCost));
            totalPaymentTextView1.setText(String.format("%,.0f VNĐ", totalCost));
        }
    }

    private void deleteCartItem(int productId) {
        ApiCartItems apiCartItems = APIClient.getClient().create(ApiCartItems.class);
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String token = preferenceManager.getToken();
        int cartId = preferenceManager.getCartId();

        Call<Void> call = apiCartItems.deleteCartItem("Bearer " + token, cartId, productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BuyandpaymentActivity.this, "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BuyandpaymentActivity.this, "Lỗi xóa sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(BuyandpaymentActivity.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}