package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.io.IOException;
import java.time.LocalDateTime;

import DTO.UsersDTO;

import Interface.APIClient;
import Interface.ApiCart;
import Interface.ApiUsers;
import Interface.PreferenceManager;
import Model.Carts;
import Reponse.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPhone;
    private EditText edtPassword;
    private Button btnLogin;
    private ApiUsers apiService;
    private ApiCart apiCart;
    private int cart;
    private Carts newCart;
    private PreferenceManager preferenceManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        apiService = APIClient.getLoginService();
        apiCart = APIClient.getClient().create(ApiCart.class);

        TextView tvSignup = findViewById(R.id.tv_signup);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(view -> {
            String phoneNumber = edtPhone.getText().toString().trim();
            System.out.println(phoneNumber);
            String password = edtPassword.getText().toString().trim();

            if (!phoneNumber.isEmpty() && !password.isEmpty()) {
                loginUser(phoneNumber, password);
            } else {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginUser(String phoneNumber, String password) {
        UsersDTO usersDTO = new UsersDTO(phoneNumber, password);
        Call<LoginResponseDTO> call = apiService.login(usersDTO);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponseDTO loginResponse = response.body();
                    String token = loginResponse.getToken();
                    int userId = loginResponse.getUserId();
                    int roleId = loginResponse.getRoleId();
                    String name = loginResponse.getName();

                    String address = loginResponse.getAddress();

                    int conversationId = loginResponse.getConversationId();
                    int cartId = loginResponse.getCartId();



                    Log.d("Login", "Token: " + token);

                    preferenceManager = new PreferenceManager(LoginActivity.this);
                    preferenceManager.saveToken(token);
                    preferenceManager.saveUserId(userId);
                    preferenceManager.saveName(name);

                    preferenceManager.saveAddress(address);

                    preferenceManager.saveId(conversationId);

                    if (cartId == 0) {
                        newCart = new Carts(userId);
                        Call<String> callCart = apiCart.createCart("Bearer " + token, newCart);
                        callCart.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    String result = response.body();
                                    System.out.println("Cart created successfully: " + result);
                                    Call<String> getCart = apiCart.getCartByUserId("Bearer " + token, userId);
                                    getCart.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful() && response.body() != null) {
                                                String fetchedCartId = response.body();
                                                System.out.println("Fetched Cart ID: " + fetchedCartId);

                                                try {
                                                    int parsedCartId = Integer.parseInt(fetchedCartId);
                                                    preferenceManager.saveCartId(parsedCartId);
                                                    System.out.println("Cart ID saved: " + parsedCartId);
                                                } catch (Exception e) {
                                                    System.err.println("Failed to parse cart ID or user ID: " + e.getMessage());
                                                }
                                            } else {
                                                System.err.println("Error fetching cart ID: " + response.code());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            System.err.println("API call to fetch cart ID failed: " + t.getMessage());
                                        }
                                    });
                                } else {
                                    System.err.println("Error creating cart: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                System.err.println("API call to create cart failed: " + t.getMessage());
                            }
                        });
                    }else {
                        preferenceManager.saveCartId(cartId);
                    }


                    if (roleId == 2) {
                        Intent intent = new Intent(LoginActivity.this, mainpageActivity.class);
                        intent.putExtra("tabPosition", 2);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, mainpageAdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Log.e("Login", "Error - Status Code: " + response.code() + ", Message: " + response.message());
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Log.e("Login", "Failed: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Đăng nhập không thành công. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleErrorResponse(Response<LoginResponseDTO> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("Login", "Error Body: " + errorBody);
            Toast.makeText(LoginActivity.this, "Lỗi đăng nhập: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}