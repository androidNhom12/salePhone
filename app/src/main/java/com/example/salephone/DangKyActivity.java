package com.example.salephone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.User;

public class DangKyActivity extends AppCompatActivity {
    EditText edtUserName;
    EditText edtPassWord;
    EditText edtRePassWord;
    Button btnRegister;
    Button btnRegisterBack;
    TextView tvBackToLogin;
    CreateDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtRePassWord = findViewById(R.id.edtRePassWord);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegisterBack = findViewById(R.id.btnRegisterBack);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);
        db = db = new CreateDatabase(this);

        btnRegister.setOnClickListener(view -> {
            User user = new User();

            String username = edtUserName.getText().toString().trim();
            String password = edtPassWord.getText().toString().trim();
            String rePassword = edtRePassWord.getText().toString().trim();

            //Validate input
            if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(rePassword)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.isUserExists(username)) {
                Toast.makeText(this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
            user.setUser_username(username);
            user.setUser_password(password);
            user.setUser_role("customer");

            // insert user into database
            long result = db.insertUser(user);
            if (result == -1) {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                // Chuyển sang trang đăng nhập
                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent);

                // Kết thúc trang đăng ký
                finish();
            }
        });
        tvBackToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
            startActivity(intent);
        });

    }

}