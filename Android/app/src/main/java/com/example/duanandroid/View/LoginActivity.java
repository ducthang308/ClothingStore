package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duanandroid.R;

import java.util.List;

//import Interface.APIservice;
import Interface.APIservice;
import Model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername = findViewById(R.id.edt_email);
    private EditText edtPassword = findViewById(R.id.edt_password);
    private TextView btnLogin = findViewById(R.id.btn_login);
    private List<User> userList;
    private User muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Các trường nhập liệu


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra điều kiện đăng nhập
                if ("user".equals(username) && "1234".equals(password)) {
                    // Đăng nhập thành công cho user
                    Intent intent = new Intent(LoginActivity.this, mainpageActivity.class);
                    startActivity(intent);
                } else if ("admin".equals(username) && "1235".equals(password)) {
                    // Đăng nhập thành công cho admin
                    Intent intent = new Intent(LoginActivity.this, mainpageAdminActivity.class);
                    startActivity(intent);
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView tvSignup = findViewById(R.id.tv_signup);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


//    private void clickLogin(){
//        String username = edtUsername.getText().toString().trim();
//        String password = edtPassword.getText().toString().trim();
//        if(userList == null ||userList.isEmpty()){
//            return;
//        }
//        boolean isHasUser=false;
//
//        for (User user :userList){
//            if(username.equals(user.getUserName()) && password.equals(user.getPassword()))
//            {
//               isHasUser= true;
//                muser =  user;
//               break;
//
//            }
//        }
//        if(isHasUser){
//            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//            Bundle bundle= new Bundle();
//            bundle.putSerializable("object_user",muser);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//        else {
//            Toast.makeText(LoginActivity.this  ,"Tài khoản hoặc mật khẩu không hợp lệ",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void getListuser(){
//        APIservice.apiservice.getListuser("")
//        .enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                 userList = response.body();
//                Log.e("userList" , userList.size()+"");
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(LoginActivity.this  ,"call api failed",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
