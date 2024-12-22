package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AccountActivity extends AppCompatActivity {
    TextView tvUserName;
    ImageView iconHome;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manageraccount);

        tvUserName = findViewById(R.id.tvUserName);
        iconHome = findViewById(R.id.iconUser);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs",MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "user");
        String role = sharedPreferences.getString("role","customer");

        // Đặt giá trị username vào TextView
        tvUserName.setText(username);

        ConstraintLayout constraintLayout = findViewById(R.id.layoutProductManager);

        if ("admin".equals(role)) {
            constraintLayout.setVisibility(View.VISIBLE);
        } else {
            constraintLayout.setVisibility(View.GONE);
        }

        constraintLayout.setOnClickListener(view -> {
            Intent intent = new Intent(this,ManagerProduct.class);
            startActivity(intent);
        });

        iconHome.setOnClickListener(view -> {
            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //Feature logout

        ConstraintLayout logout = findViewById(R.id.layoutLogout);
        logout.setOnClickListener(view -> {
            // Xóa SharedPreferences
            SharedPreferences sharedPreferences1 = getSharedPreferences("user_prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.clear();
            editor.apply();

            // Chuyển đến màn hình đăng nhập
            Intent intent = new Intent(this, DangNhapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack hiện tại
            startActivity(intent);
            finish(); // Kết thúc activity hiện tại
        });



    }
}
