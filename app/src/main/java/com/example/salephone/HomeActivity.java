package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ImageView iconAccount, iconCart, iconPhone;
    ImageView logoIphone, logoSamsung, logoXiaomi, logoVivo, logoOppo;
    EditText edtSearch;
    GridLayout gridLayoutProducts; // Khai báo GridLayout
    private ViewFlipper viewFlipper;
    private List<Product> allProducts;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //link elements
        getWidget();

        //setupMenu();

        //create database and get data to gridview
        getData();


        bannerFlipper();

        navbarClick();
        logoClick();
    }


//    private void openProductDetail(Product product) {
//        // Chuyển đến màn hình chi tiết sản phẩm
//        Intent intent = new Intent(this, ProductDetailActivity.class);
//        intent.putExtra("product_id", product.getId());
//        startActivity(intent);
//    }

    public void getData() {
        // Mở cơ sở dữ liệu
        CreateDatabase createDatabase = new CreateDatabase(this);
        createDatabase.open();
        createDatabase.ensureAdminAccountExists();

        CreateDatabase db = new CreateDatabase(this);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> popularProducts = createDatabase.getAllProduct();
        allProducts = createDatabase.getAllProduct();

        if (allProducts == null) {
            allProducts = new ArrayList<>();
        }

        // Xóa toàn bộ view cũ trước khi thêm mới
        gridLayoutProducts.removeAllViews();

        // Thêm sản phẩm vào GridLayout
        for (Product product : popularProducts) {
            // Tạo view cho mỗi sản phẩm
            View productView = getLayoutInflater().inflate(R.layout.layout_product, null);

            // Thiết lập thông tin cho sản phẩm
            TextView productName = productView.findViewById(R.id.productName);
            TextView productPrice = productView.findViewById(R.id.productPrice);
            ImageView productImage = productView.findViewById(R.id.productImage);

            productName.setText(product.getName());
            productPrice.setText("Giá: " + product.getPrice() + " K");

            // Dùng tài nguyên drawable thay vì Glide
            int imageResId = getResources().getIdentifier(product.getImage_url(), "drawable", getPackageName());
            if (imageResId != 0) {
                productImage.setImageResource(imageResId);
            } else {
                productImage.setImageResource(R.drawable.iphone); // Ảnh mặc định
            }

            // Thiết lập kích thước cho mỗi item
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels / 2 - 24; // Mỗi item chiếm 1/2 màn hình
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(8, 8, 8, 8); // Căn lề
            productView.setLayoutParams(params);

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

    /*private void setupMenu() {
        edtSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getX() <= (edtSearch.getCompoundDrawables()[0].getBounds().width() + edtSearch.getPaddingLeft())) {
                    // Icon menu được click
                    if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                        drawerLayout.closeDrawer(GravityCompat.END);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.END);
                    }
                    return true;
                }
            }
            return false;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_close) {
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
            return false;
        });
    }*/


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
//        drawerLayout = findViewById(R.id.drawerLayout);
//        navigationView = findViewById(R.id.navigationView);
    }
}
