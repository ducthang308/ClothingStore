package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private String token;
    private ApiUsers apiUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_pass);
        ImageButton img_arrow = findViewById(R.id.arrow_changepass);

        apiUsers = APIClient.updatePass();
        initView();
    }

    private void initView() {
        // Ánh xạ các view từ layout
        ImageButton imgArrow = findViewById(R.id.arrow_changepass);
        EditText etCurrentPassword = findViewById(R.id.etCurrentPassword);
        EditText etNewPassword = findViewById(R.id.etNewPassword);
        EditText etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword);
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

            PreferenceManager preferenceManager = new PreferenceManager(this);
            String token = preferenceManager.getToken();
            int userId = preferenceManager.getUserId();

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