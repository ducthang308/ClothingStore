package com.example.duanandroid.View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

public class changePassActivity extends AppCompatActivity {
    private EditText etCurrentPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        btnSave = findViewById(R.id.btnSave);

        setupPasswordVisibilityToggle(etCurrentPassword);
        setupPasswordVisibilityToggle(etNewPassword);
        setupPasswordVisibilityToggle(etConfirmNewPassword);
    }

    private void setupListeners() {
        ImageButton imgArrow = findViewById(R.id.arrow_changepass);
        imgArrow.setOnClickListener(view -> finish());

        btnSave.setOnClickListener(view -> {
            String currentPassword = etCurrentPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();

            if (validateInput(currentPassword, newPassword, confirmNewPassword)) {
                saveNewPassword(newPassword);
            }
        });
    }

    private void setupPasswordVisibilityToggle(EditText editText) {
        Drawable drawableLeft = editText.getCompoundDrawables()[0];
        Drawable drawableTop = editText.getCompoundDrawables()[1];
        Drawable drawableBottom = editText.getCompoundDrawables()[3];
        Drawable eyeCloseDrawable = getResources().getDrawable(R.drawable.eye_close);
        Drawable eyeOpenDrawable = getResources().getDrawable(R.drawable.eye_open);

        editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeCloseDrawable, drawableBottom);

        editText.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width())) {
                    togglePasswordVisibility(editText, drawableLeft, drawableTop, drawableBottom, eyeOpenDrawable, eyeCloseDrawable);
                    return true;
                }
            }
            return false;
        });
    }

    private void togglePasswordVisibility(EditText editText, Drawable drawableLeft, Drawable drawableTop, Drawable drawableBottom, Drawable eyeOpenDrawable, Drawable eyeCloseDrawable) {
        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeOpenDrawable, drawableBottom);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, eyeCloseDrawable, drawableBottom);
        }
        editText.setSelection(editText.getText().length());
    }

    private boolean validateInput(String currentPassword, String newPassword, String confirmNewPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveNewPassword(String newPassword) {
        Toast.makeText(this, "Mật khẩu đã được thay đổi thành công", Toast.LENGTH_SHORT).show();
        finish();
    }
}
