package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.ReviewAdapter;

import DTO.ProductDTO;

import DTO.CartItemsDTO;

import DTO.ReviewWithUserFullNameDTO;
import Interface.APIClient;
import Interface.ApiCartItems;
import Interface.ApiReview;
import Interface.PreferenceManager;
import Model.CartItems;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChitietsanphamActivity extends AppCompatActivity {
    private static final String TAG = "ChitietsanphamActivity";
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private TextView productNameTextView, productPriceTextView;
    private ImageView productImageView, shopping_cart;
    private Button btn_add_to_cart;
    private String token;
    private PreferenceManager preferenceManager;
    private int cartId;
    private ApiCartItems apiCartItems;
    private List<ReviewWithUserFullNameDTO> reviewList = new ArrayList<>();
    private ApiReview apiReview;
    private int productId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        initializeViews();

        apiCartItems = APIClient.getClient().create(ApiCartItems.class);
        preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        cartId = preferenceManager.getCartId();

        boolean isDataValid = getIntentData();
        if (!isDataValid) return;

        setupRecyclerView();

        loadProductDetails();
        loadReviews();

        setupButtons();

        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }

        });

        shopping_cart = findViewById(R.id.shopping_cart);
        shopping_cart.setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);
            startActivity(intent);
        });


    }

    private void addToCart() {
        if (cartId == -1 || productId == -1) {
            Toast.makeText(ChitietsanphamActivity.this, "Invalid Cart or Product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<CartItemsDTO>> call = apiCartItems.getAllCartItemsByCartId("Bearer " + token, cartId);
        call.enqueue(new Callback<List<CartItemsDTO>>() {
            @Override
            public void onResponse(Call<List<CartItemsDTO>> call, Response<List<CartItemsDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CartItemsDTO> cartItems = response.body();
                    CartItemsDTO existingCartItem = null;
                    for (CartItemsDTO item : cartItems) {
                        if (item.getProductId() == productId) {
                            existingCartItem = item;
                            break;
                        }
                    }
                    if (existingCartItem != null) {
                        updateCartItem(existingCartItem.getId(), existingCartItem.getQuantity() + 1);
                    } else {
                        createCartItem();
                    }
                } else {
                    Log.e(TAG, "Failed to fetch cart items: " + response.message());
                    Toast.makeText(ChitietsanphamActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItemsDTO>> call, Throwable t) {
                Log.e(TAG, "Error fetching cart items", t);
                Toast.makeText(ChitietsanphamActivity.this, "Error fetching cart items", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateCartItem(int itemId, int newQuantity) {
        CartItemsDTO cartItems = new CartItemsDTO();
        cartItems.setQuantity(newQuantity);

        Call<ResponseBody> call = apiCartItems.updateCartItem("Bearer " + token, itemId, cartItems);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChitietsanphamActivity.this, "Cart updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to update cart item: " + response.message());
                    Toast.makeText(ChitietsanphamActivity.this, "Failed to update cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error updating cart item", t);
                Toast.makeText(ChitietsanphamActivity.this, "Error updating cart item", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void createCartItem() {
        if (cartId == -1 || productId == -1) {
            Toast.makeText(ChitietsanphamActivity.this, "Invalid Cart or Product ID", Toast.LENGTH_SHORT).show();
            return;
        }
        CartItems newItem = new CartItems();
        newItem.setCartId(cartId);
        newItem.setProductId(productId);
        newItem.setQuantity(1);

        Log.d(TAG, "Creating CartItem: cartId=" + newItem.getCartId() + ", productId=" + newItem.getProductId() + ", quantity=" + newItem.getQuantity());

        Call<String> call = apiCartItems.createCartItem("Bearer " + token, newItem);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChitietsanphamActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to add item to cart: " + response.message());
                    Toast.makeText(ChitietsanphamActivity.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Error adding item to cart", t);
                Toast.makeText(ChitietsanphamActivity.this, "Error adding item to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initializeViews() {
        productNameTextView = findViewById(R.id.product_description);
        productPriceTextView = findViewById(R.id.product_price);
        productImageView = findViewById(R.id.product_image);
        reviewRecyclerView = findViewById(R.id.review_list);
    }

    public boolean getIntentData() {
        productId = getIntent().getIntExtra("productId", -1);
        String productName = getIntent().getStringExtra("productName");
        float productPrice = getIntent().getFloatExtra("productPrice", 0);
        ArrayList<String> productImages = getIntent().getStringArrayListExtra("productImage");

        if (productId == -1 || productName == null || productPrice <= 0.0) {
            Log.e(TAG, "Invalid product data.");
            Toast.makeText(this, "Invalid product data!", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        productNameTextView.setText(productName);
        productPriceTextView.setText(String.format("₫%,.0f", productPrice));

        if (productImages != null && !productImages.isEmpty()) {
            Glide.with(this)
                    .load(productImages.get(0))
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(productImageView);
        } else {
            productImageView.setImageResource(R.drawable.error);
        }

        return true;
    }

    private void setupRecyclerView() {
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Truyền context vào adapter
        reviewAdapter = new ReviewAdapter(this, reviewList);
        reviewRecyclerView.setAdapter(reviewAdapter);
    }

    private void loadProductDetails() {
        Log.d(TAG, "Product ID: " + productId);
    }

    private void loadReviews() {
        if (apiReview == null) {
            apiReview = APIClient.getClient().create(ApiReview.class);
        }
        apiReview.getAllReview(productId).enqueue(new Callback<List<ReviewWithUserFullNameDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewWithUserFullNameDTO>> call, Response<List<ReviewWithUserFullNameDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleReviewResponse(response.body());
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.message());
                    Toast.makeText(ChitietsanphamActivity.this, "No reviews available!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReviewWithUserFullNameDTO>> call, Throwable t) {
                Toast.makeText(ChitietsanphamActivity.this, "Error loading reviews!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error loading reviews: " + t.getMessage());
            }
        });
    }

    private void handleReviewResponse(List<ReviewWithUserFullNameDTO> reviewDTOs) {
        reviewList.clear();
        reviewList.addAll(reviewDTOs);
        reviewAdapter.notifyDataSetChanged();
    }

    private void setupButtons() {
        findViewById(R.id.btn_add_to_cart).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_buy_now).setOnClickListener(view -> {
            String productName = productNameTextView.getText().toString();
            String productPriceString = productPriceTextView.getText().toString().replace("₫", "").replace(",", "");
            Float productPrice = null;

            try {
                productPrice = Float.parseFloat(productPriceString);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price format!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (productPrice == null || productPrice <= 0.0f) {
                Toast.makeText(this, "Invalid product price!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> productImages = getIntent().getStringArrayListExtra("productImage");
            if (productImages == null || productImages.isEmpty()) {
                Toast.makeText(this, "Product images are missing!", Toast.LENGTH_SHORT).show();
                return;
            }


            int productId = getIntent().getIntExtra("productId", -1);
            if (productId == -1) {
                Toast.makeText(this, "Invalid product ID!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create ProductDTO object
            ProductDTO productDTO = new ProductDTO(productId, productName, productPrice, productImages);

            // Pass ProductDTO to BuyandpaymentActivity
            ArrayList<ProductDTO> productList = new ArrayList<>();
            productList.add(productDTO);

            Intent intent = new Intent(ChitietsanphamActivity.this, BuyandpaymentActivity.class);
            intent.putExtra("productList", productList);
            startActivity(intent);
        });

        findViewById(R.id.chat).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, chatUserActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.shopping_cart).setOnClickListener(view -> {
            Intent intent = new Intent(ChitietsanphamActivity.this, CartActivity.class);

            intent.putExtra("productId", productId);
            intent.putExtra("productName", productNameTextView.getText().toString());
            intent.putExtra("productPrice", Float.parseFloat(productPriceTextView.getText().toString()));
            intent.putExtra("productImage", getIntent().getStringArrayListExtra("productImage"));

            startActivity(intent);
        });
    }

}
