package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duanandroid.R;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import DTO.UsersDTO;

import Interface.APIClient;
import Interface.ApiUsers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtFullName, edtDateOfBirth, edtPhone, edtEmail, edtAddress, edtPassword, edtRetypePassword;
    private Button btnRegister;
    private ApiUsers apiServiceRegister;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        apiServiceRegister = APIClient.getRegisterService(getApplicationContext());

        btnRegister.setOnClickListener(view -> handleRegistration());

        TextView tvSignin = findViewById(R.id.tv_signin);
        tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    private void initViews() {
        edtFullName = findViewById(R.id.edt_name);
        edtDateOfBirth = findViewById(R.id.edt_birth);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtPassword = findViewById(R.id.edt_password);
        edtRetypePassword = findViewById(R.id.edt_confirm);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void handleRegistration() {
        String fullName = edtFullName.getText().toString().trim();
        String dateOfBirthStr = edtDateOfBirth.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String retypePass = edtRetypePassword.getText().toString().trim();

        Date dateOfBirth = parseDate(dateOfBirthStr);

        if (validateInput(fullName, dateOfBirth, phoneNumber, email, address, password, retypePass)) {
            // Format date as String in yyyy-MM-dd format
            String formattedDateOfBirth = dateOfBirth != null ? dateFormat.format(dateOfBirth) : null;

            UsersDTO usersDTO = new UsersDTO(
                    fullName, phoneNumber, address, password, email, retypePass, formattedDateOfBirth
            );

            registerUser(usersDTO);
        }
    }

    private Date parseDate(String dateOfBirthStr) {
        try {
            dateFormat.setLenient(false);
            return dateFormat.parse(dateOfBirthStr);
        } catch (ParseException e) {
            Toast.makeText(this, "Định dạng ngày sinh không hợp lệ (yyyy-MM-dd)", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private boolean validateInput(String fullName, Date dateOfBirth, String phoneNumber, String email, String address, String password, String retypePass) {
        if (fullName.isEmpty() || dateOfBirth == null || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || password.isEmpty() || retypePass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Mật khẩu cần ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.d("RegisterActivity", "Password: '" + password + "' Retype Password: '" + retypePass + "'");
        if (!password.trim().equals(retypePass.trim())) {
            Toast.makeText(this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        return phoneNumber.length() == 10 && phoneNumber.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    private void registerUser(UsersDTO usersDTO) {
        apiServiceRegister.register(usersDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Register", "Failed: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponse(Response<String> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("Register", "Error Code: " + response.code() + ", Error Body: " + errorBody);
            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Register", "Error parsing error response", e);
            Toast.makeText(this, "Lỗi khi xử lý phản hồi. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }
    }
}