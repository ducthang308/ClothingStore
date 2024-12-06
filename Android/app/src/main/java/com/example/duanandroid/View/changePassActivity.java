package com.example.duanandroid.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import DTO.UpdatePassDTO;
import Fragment.AccountUserFragment;
import Interface.APIClient;
import Interface.ApiUsers;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class changePassActivity extends AppCompatActivity {
    private ApiUsers apiUsers;
    private PreferenceManager preferenceManager;
    private String token, userName;
    private int userId;
    private EditText etCurrentPassword, etNewPassword, etConfirmNewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ImageButton img_arrow = findViewById(R.id.arrow_changepass);

        apiUsers = APIClient.updatePass();
        initView();

        preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        userName = preferenceManager.getName();
        userId = preferenceManager.getUserId();



        setupPasswordVisibilityToggle(etCurrentPassword);
        setupPasswordVisibilityToggle(etNewPassword);
        setupPasswordVisibilityToggle(etConfirmNewPassword);

        TextView tvUserName = findViewById(R.id.tvUserName);
        if (userName != null && !userName.isEmpty()) {
            tvUserName.setText(userName);
        }

        initView();
    }

    private void setupPasswordVisibilityToggle(EditText editText) {
        Drawable drawableLeft = editText.getCompoundDrawables()[0];
        Drawable drawableTop = editText.getCompoundDrawables()[1];
        Drawable drawableBottom = editText.getCompoundDrawables()[3];
        Drawable eyeCloseDrawable = getResources().getDrawable(R.drawable.hide);
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
    private void initView() {
        ImageButton imgArrow = findViewById(R.id.arrow_changepass);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
        Button btnSave = findViewById(R.id.btnSave);

        imgArrow.setOnClickListener(view -> finish());

        btnSave.setOnClickListener(view -> {
            String currentPassword = etCurrentPassword.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmNewPassword = etConfirmNewPassword.getText().toString().trim();
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(this, "Mật khẩu mới không khớp.", Toast.LENGTH_SHORT).show();
                return;
            }
            UpdatePassDTO updatePassDTO = new UpdatePassDTO(currentPassword, newPassword, confirmNewPassword);


            if (token == null || token.isEmpty() || userId == -1) {
                Toast.makeText(this, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(token, userId, updatePassDTO);
        });


    }




    private void changePassword(String token, int userId, UpdatePassDTO updatePassDTO) {
        if (updatePassDTO.getPassword() == null || updatePassDTO.getPassword().isEmpty() ||
                updatePassDTO.getNewPass() == null || updatePassDTO.getNewPass().isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<String> call = apiUsers.updatePass("Bearer " + token, userId, updatePassDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(changePassActivity.this, "Thay đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(changePassActivity.this, AccountUserFragment.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(changePassActivity.this, "Thay đổi mật khẩu thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(changePassActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}