package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.database.CreateDatabase;

public class HomeActivity extends AppCompatActivity {
    ImageView iconAccount;
    EditText edtSearch;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        // Mở cơ sở dữ liệu
        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();
        // Đảm bảo tài khoản admin tồn tại
        createDatabase.ensureAdminAccountExists();

        iconAccount = findViewById(R.id.iconAccount);
        edtSearch = findViewById(R.id.edtSearch);


        // Xem thông tin tài khoản
        iconAccount.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            if(username == null) {
                // Chưa đăng nhập
                Intent intent = new Intent(HomeActivity.this, DangNhapActivity.class);
                startActivity(intent);
            } else {
                // Đã đăng nhập
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });


    }
}
