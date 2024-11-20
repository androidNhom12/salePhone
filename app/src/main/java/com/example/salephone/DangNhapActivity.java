package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.User;

public class DangNhapActivity extends AppCompatActivity {
    EditText edtLoginUsername;
    EditText edtLoginPassword;
    Button btnLogin;
    Button btnLoginBack;
    TextView tvBackToRegister;
    CreateDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginBack = findViewById(R.id.btnLoginBack);
        tvBackToRegister = findViewById(R.id.tvBackToRegister);
        db = db = new CreateDatabase(this);


        tvBackToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
            startActivity(intent);
        });


        btnLogin.setOnClickListener(view -> {
            String username = edtLoginUsername.getText().toString().trim();
            String password = edtLoginPassword.getText().toString().trim();
            User user = new User();
            user.setUser_username(username);
            user.setUser_password(password);
//            Kiểm tra thông tin đăng nhập
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên người dùng và mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (db.isValidUser(user)) {
                User res = db.login(user);
                // Lưu thông tin người dùng vào SharedPreferences
                saveUserInfo(res);
                // Chuyển hướng đến màn hình chính
                Intent intent = new Intent(DangNhapActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();  // Đóng màn hình đăng nhập
            } else {
                Toast.makeText(this, "Sai tên người dùng hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void saveUserInfo(User user) {
        // Lưu thông tin người dùng vào SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lưu các thông tin vào SharedPreferences
        editor.putString("username", user.user_username);
        editor.putString("password", user.user_password);  // Mật khẩu cũng được lưu, tuy nhiên cần lưu ý về bảo mật
        editor.putString("phone_number", user.user_phone);
        editor.putString("role", user.user_role);
        editor.putString("created_at", user.user_create_at);

        // Lưu thay đổi
        editor.apply();
    }




}
