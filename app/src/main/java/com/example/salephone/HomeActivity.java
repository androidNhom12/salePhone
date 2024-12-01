package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ImageView iconAccount, iconCart, iconPhone;
    ImageView logoIphone, logoSamsung, logoXiaomi, logoVivo, logoOppo;
    EditText edtSearch;
    GridLayout gridLayoutProducts; // Khai báo GridLayout
    private ViewFlipper viewFlipper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //link elements
        getWidget();

        //create database and get data to gridview
        getData();

        bannerFlipper();

        navbarClick();
        logoClick();
    }

    public void getData(){
        // Mở cơ sở dữ liệu
        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();
        // Đảm bảo tài khoản admin tồn tại
        createDatabase.ensureAdminAccountExists();

        CreateDatabase db = new CreateDatabase(this);

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
            // Dùng Picasso để tải hình ảnh từ URL
            String imageUrl = product.getImage_url(); // Lấy URL ảnh từ đối tượng Product
            Glide.with(this)
                    .load(imageUrl) // URL ảnh
                    .placeholder(R.drawable.iphone_logo) // Hình ảnh mặc định khi tải
                    .error(R.drawable.samsung_logo) // Hình ảnh lỗi nếu không tải được
                    .into(productImage); // Set vào ImageView

            // Thêm view sản phẩm vào GridLayout
            gridLayoutProducts.addView(productView);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void bannerFlipper(){
        // Bắt đầu tự động chuyển đổi giữa các ảnh
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.startFlipping();

        //chạm thì chuyển thủ công
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            private float startX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        if (startX > endX) {
                            viewFlipper.showNext();
                        } else if (startX < endX) {
                            viewFlipper.showPrevious();
                        }
                        return true;
                }
                return false;
            }
        });

    }

    public void navbarClick(){
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

        // Giỏ hàng
        iconCart.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    //logo click
    public void logoClick(){
        //logo phone click
        logoIphone.setOnClickListener(view -> {
            //Intent intent = new Intent(HomeActivity.this, AppleProductsActivity.class);
            //startActivity(intent);
        });

        logoSamsung.setOnClickListener(view -> {
            //Intent intent = new Intent(HomeActivity.this, AppleProductsActivity.class);
            //startActivity(intent);
        });

        logoXiaomi.setOnClickListener(view -> {
            //Intent intent = new Intent(HomeActivity.this, AppleProductsActivity.class);
            //startActivity(intent);
        });

        logoVivo.setOnClickListener(view -> {
            //Intent intent = new Intent(HomeActivity.this, AppleProductsActivity.class);
            //startActivity(intent);
        });

        logoOppo.setOnClickListener(view -> {
            //Intent intent = new Intent(HomeActivity.this, AppleProductsActivity.class);
            //startActivity(intent);
        });
    }

    public void getWidget(){
        iconAccount = findViewById(R.id.iconAccount);
        iconCart = findViewById(R.id.iconCart);
        iconPhone = findViewById(R.id.iconPhone);
        edtSearch = findViewById(R.id.edtSearch);
        gridLayoutProducts = findViewById(R.id.gridLayoutProducts);
        viewFlipper = findViewById(R.id.viewFlipper);
        logoIphone = findViewById(R.id.logoIphone);
        logoSamsung = findViewById(R.id.logoSamsung);
        logoXiaomi = findViewById(R.id.logoXiaomi);
        logoVivo = findViewById(R.id.logoVivo);
        logoOppo = findViewById(R.id.logoOppo);
    }
}
