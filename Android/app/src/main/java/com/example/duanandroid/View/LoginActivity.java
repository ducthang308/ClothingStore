package com.example.duanandroid.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPhone;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        setupPasswordVisibilityToggle(edtPassword);
        TextView tvSignup = findViewById(R.id.tv_signup);
        tvSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {
            String phoneNumber = edtPhone.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (!phoneNumber.isEmpty() && !password.isEmpty()) {
                loginUser(phoneNumber, password);
            } else {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPasswordVisibilityToggle(EditText editText) {
        // Save the initial drawables (left, top, bottom) and set initial right drawable to eye_close
        Drawable drawableLeft = editText.getCompoundDrawables()[0];
        Drawable drawableTop = editText.getCompoundDrawables()[1];
        Drawable drawableBottom = editText.getCompoundDrawables()[3];
        Drawable eyeCloseDrawable = getResources().getDrawable(R.drawable.eye_close);
        Drawable eyeOpenDrawable = getResources().getDrawable(R.drawable.eye_open);

        editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeCloseDrawable, drawableBottom);

        editText.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                    // Toggle password visibility
                    if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                        // Show password
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeOpenDrawable, drawableBottom);
                    } else {
                        // Hide password
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeCloseDrawable, drawableBottom);
                    }
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }

    public void loginUser(String phoneNumber, String password) {
        if ((phoneNumber.equals("user") && password.equals("1234")) || (phoneNumber.equals("admin") && password.equals("1235"))) {
            String token = "dummyToken";
            int roleId;

            if (phoneNumber.equals("user")) {
                roleId = 2;
            } else {
                roleId = 1;
            }


            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", token);
            editor.apply();


            Intent intent;
            if (roleId == 2) {
                intent = new Intent(LoginActivity.this, mainpageActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, mainpageAdminActivity.class);
            }
            intent.putExtra("tabPosition", 1);
            startActivity(intent);
            finish();
        } else {

            Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}
