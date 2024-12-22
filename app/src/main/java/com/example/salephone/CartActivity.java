package com.example.salephone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salephone.adapter.CartAdapter;
import com.example.salephone.entity.CartItem;
import com.example.salephone.entity.Product;

public class CartActivity extends AppCompatActivity {
    ImageView iconAccount, iconHome, iconPhone, backIcon;
    private RecyclerView recyclerView;
    private TextView totalAmount;
    private Button checkoutButton, deleteButton;
    private Cart cart;
    private CheckBox selectAllCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);

        // Ánh xạ các view từ layout
        recyclerView = findViewById(R.id.cart_recycler_view);
        totalAmount = findViewById(R.id.total_amount);
        checkoutButton = findViewById(R.id.checkout_button);
        deleteButton = findViewById(R.id.delete_button);
        selectAllCheckbox = findViewById(R.id.select_all_checkbox);
        backIcon = findViewById(R.id.back_icon);

        backIcon.setOnClickListener(v -> {
            // Trở về màn hình trước
            finish();
        });

        // Khởi tạo giỏ hàng (lấy từ database hoặc singleton)
        cart = new Cart();

        addDefaultItemsToCart();

        // Set LayoutManager cho RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hiển thị danh sách sản phẩm trong giỏ hàng
        CartAdapter adapter = new CartAdapter(this, cart.getCartItems(), this::updateTotalAmount);
        recyclerView.setAdapter(adapter);

        // Hiển thị tổng giá
        updateTotalAmount();

        // Xử lý sự kiện chọn tất cả
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CartItem item : cart.getCartItems()) {
                item.setSelected(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateTotalAmount();
        });

        // Xử lý sự kiện xóa sản phẩm đã chọn
        deleteButton.setOnClickListener(v -> {
            cart.removeSelectedItems();
            adapter.notifyDataSetChanged();
            updateTotalAmount();
        });

        // Xử lý sự kiện thanh toán
        checkoutButton.setOnClickListener(v -> {
            if (cart.getCartItems().stream().anyMatch(CartItem::isSelected)) {
                // Thực hiện thanh toán (ví dụ chuyển sang màn hình thanh toán)
                Toast.makeText(this, "Đang xử lý", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Chọn ít nhất 1 sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        // Cập nhật trạng thái nút "Chọn tất cả" khi giỏ hàng thay đổi
        updateSelectAllCheckboxVisibility();
    }

    // Cập nhật tổng tiền và trạng thái của nút thanh toán
    public void updateTotalAmount() {
        double total = cart.getTotalPrice();
        totalAmount.setText("Tổng: " + total + " K");

        // Kiểm tra xem có sản phẩm nào được chọn để kích hoạt nút thanh toán
        boolean anySelected = cart.getCartItems().stream().anyMatch(CartItem::isSelected);
        checkoutButton.setEnabled(anySelected);
        checkoutButton.setAlpha(anySelected ? 1.0f : 0.5f);

        // Cập nhật trạng thái nút "Chọn tất cả"
        updateSelectAllCheckboxVisibility();
    }

    // Kiểm tra và ẩn/hiện nút "Chọn tất cả" khi giỏ hàng có sản phẩm
    private void updateSelectAllCheckboxVisibility() {
        if (cart.getCartItems().isEmpty()) {
            selectAllCheckbox.setVisibility(View.GONE); // Ẩn nếu không có sản phẩm
        } else {
            selectAllCheckbox.setVisibility(View.VISIBLE); // Hiện nếu có ít nhất 1 sản phẩm
        }
    }

    // Thêm 5 sản phẩm mặc định vào giỏ hàng
    private void addDefaultItemsToCart() {
        // Tạo sản phẩm 1
        Product product1 = new Product(1, "Samsung", "145000", "abc",
                "samsungs22ultra");
        Product product2 = new Product(2, "Iphone", "180000", "abc",
                "iphone13");
        Product product3 = new Product(5, "Iphone", "180000", "abc",
                "iphone11");

        cart.addToCart(product1, 2);
        cart.addToCart(product2, 1);
        cart.addToCart(product3, 2);

    }

//    public void navbarClick(){
//        // Xem thông tin tài khoản
//        iconAccount.setOnClickListener(view -> {
//            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
//            String username = sharedPreferences.getString("username", null);
//
//            if(username == null) {
//                // Chưa đăng nhập
//                Intent intent = new Intent(HomeActivity.this, DangNhapActivity.class);
//                startActivity(intent);
//            } else {
//                // Đã đăng nhập
//                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Giỏ hàng
//        iconHome.setOnClickListener(view -> {
//            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
//            startActivity(intent);
//        });
//    }
}
