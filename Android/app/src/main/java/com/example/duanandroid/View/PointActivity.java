package com.example.duanandroid.View;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

public class PointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        // Ánh xạ nút "Sử dụng điểm"
        Button btnUsePoints = findViewById(R.id.btn_use_points);
        btnUsePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardsDialog();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView btn_back = findViewById(R.id.tichdiem_arrow_user);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showRewardsDialog() {

        List<String> rewards = new ArrayList<>();
        rewards.add("Cốc giữ nhiệt - 100 điểm");
        rewards.add("Túi vải - 200 điểm");
        rewards.add("Voucher 50k - 300 điểm");
        rewards.add("Balo thời trang - 500 điểm");

        // Inflate layout Dialog
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_point, null);

        // Ánh xạ các thành phần trong Dialog
        RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group_rewards);
        Button btnConfirm = dialogView.findViewById(R.id.btn_confirm_rewards);

        // Thêm các RadioButton vào RadioGroup
        for (String reward : rewards) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(reward);
            radioButton.setTextSize(16);
            radioGroup.addView(radioButton);
        }

        // Tạo AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // Xử lý sự kiện khi nhấn nút "Xác nhận"
        btnConfirm.setOnClickListener(v -> {
            // Lấy lựa chọn của người dùng
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = dialogView.findViewById(selectedId);
                String selectedReward = selectedRadioButton.getText().toString();

                // Hiển thị Dialog thông báo đổi quà thành công
                showSuccessDialog(selectedReward);

                // Đóng Dialog hiện tại
                dialog.dismiss();
            } else {
                // Hiển thị thông báo yêu cầu chọn quà
                showErrorDialog();
            }
        });

        // Hiển thị Dialog
        dialog.show();
    }

    /**
     * Hiển thị Dialog thông báo đổi quà thành công
     */
    private void showSuccessDialog(String reward) {
        AlertDialog successDialog = new AlertDialog.Builder(this)
                .setTitle("Đổi quà thành công")
                .setMessage("Bạn đã đổi quà: " + reward)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create();
        successDialog.show();
    }

    /**
     * Hiển thị Dialog yêu cầu chọn quà
     */
    private void showErrorDialog() {
        AlertDialog errorDialog = new AlertDialog.Builder(this)
                .setTitle("Lỗi")
                .setMessage("Vui lòng chọn một quà tặng!")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create();
        errorDialog.show();
    }
}
