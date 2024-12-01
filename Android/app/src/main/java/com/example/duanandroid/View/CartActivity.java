package com.example.duanandroid.View;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duanandroid.R;
import java.util.ArrayList;
import java.util.List;
import Adapter.CartAdapter;
import DTO.CartItemsDTO;
import Interface.APIClient;
import Interface.ApiCartItems;
import Interface.PreferenceManager;
import Model.CartItems;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ListView listCartItems;
    private TextView tv_total_price;
    private List<CartItemsDTO> cartItems = new ArrayList<>();
    private CartAdapter cartAdapter;
    private int cartId;
    private String token;
    private ApiCartItems apiCartItems;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listCartItems = findViewById(R.id.list_cart_items);
        tv_total_price = findViewById(R.id.tv_total_price);

        preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        cartId = preferenceManager.getCartId();

        if (token == null || token.isEmpty() || cartId <= 0) {
            Toast.makeText(this, "Lỗi xác thực. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiCartItems = APIClient.getClient().create(ApiCartItems.class);
        cartAdapter = new CartAdapter(this, cartItems, apiCartItems, token, cartId);
        listCartItems.setAdapter(cartAdapter);

        loadCartItems();

        Button btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(view -> {
            Intent checkoutIntent = new Intent(CartActivity.this, BuyandpaymentActivity.class);
            checkoutIntent.putExtra("origin", "cart");
            startActivity(checkoutIntent);
        });

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> finish());

        TextView btnDelete = findViewById(R.id.edit);
        btnDelete.setOnClickListener(view -> {
            List<Integer> selectedItems = cartAdapter.getSelectedItems();  // Lấy các sản phẩm đã chọn
            for (int productId : selectedItems) {
                deleteCartItem(cartId, productId);
                // Xóa các item khỏi danh sách giỏ hàng
                for (int i = cartItems.size() - 1; i >= 0; i--) {
                    if (cartItems.get(i).getProductId() == productId) {
                        cartItems.remove(i);
                    }
                }
            }
            cartAdapter.notifyDataSetChanged();  // Cập nhật lại ListView
        });

    }

    private void deleteCartItem(int cartId, int productId) {
        Call<Void> call = apiCartItems.deleteCartItem("Bearer " + token, cartId, productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Lỗi xóa sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCartItems() {
        Call<List<CartItemsDTO>> call = apiCartItems.getAllCartItemsByCartId("Bearer " + token, cartId);
        call.enqueue(new Callback<List<CartItemsDTO>>() {
            @Override
            public void onResponse(Call<List<CartItemsDTO>> call, Response<List<CartItemsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cartItems.clear();
                    cartItems.addAll(response.body());
                    cartAdapter.notifyDataSetChanged();

                    // Tính tổng tiền
                    double totalPrice = 0;
                    for (CartItemsDTO item : cartItems) {
                        totalPrice += item.getPrice() * item.getQuantity();
                    }
                    tv_total_price.setText(String.format("₫%,.0f", totalPrice));
                } else {
                    Toast.makeText(CartActivity.this, "Không tải được dữ liệu giỏ hàng.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItemsDTO>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Lỗi kết nối. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}