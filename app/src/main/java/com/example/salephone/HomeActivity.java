package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.UrlResponseInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ImageView iconAccount;
    EditText edtSearch;
    GridLayout gridLayoutProducts; // Khai báo GridLayout
    private ViewFlipper viewFlipper;

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

        CreateDatabase db = new CreateDatabase(this);
        //db.addProduct("iPhone 15 Pro", 39990000, "Smartphone from Apple with ProMotion display", "https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/g/a/galaxy-a15-xanh-01.png");

        //link elements
        getWidget();

        // Bắt đầu tự động chuyển đổi giữa các ảnh
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.startFlipping();

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> popularProducts = createDatabase.getAllProduct();

        // Thêm sản phẩm vào GridLayout
        for (Product product : popularProducts) {
            // Tạo view cho mỗi sản phẩm
            View productView = getLayoutInflater().inflate(R.layout.layout_product, null);

            // Thiết lập thông tin cho sản phẩm
            TextView productName = productView.findViewById(R.id.productName);
            TextView productPrice = productView.findViewById(R.id.productPrice);
            ImageView productImage = productView.findViewById(R.id.productImage);

            productName.setText(product.getName());
            productPrice.setText("Giá: " + product.getPrice() + " VND");
            //productImage.setImageResource();

            // Thêm view sản phẩm vào GridLayout
            gridLayoutProducts.addView(productView);
        }


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

    public void getWidget(){
        iconAccount = findViewById(R.id.iconAccount);
        edtSearch = findViewById(R.id.edtSearch);
        gridLayoutProducts = findViewById(R.id.gridLayoutProducts);
        viewFlipper = findViewById(R.id.viewFlipper);
    }
}
