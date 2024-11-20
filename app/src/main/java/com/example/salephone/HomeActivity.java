package com.example.salephone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.database.CreateDatabase;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        // Mở cơ sở dữ liệu
        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();
        // Đảm bảo tài khoản admin tồn tại
        createDatabase.ensureAdminAccountExists();
    }
}
