package com.example.salephone;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salephone.adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView totalAmount;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cart);

        recyclerView = findViewById(R.id.cart_recycler_view);
        totalAmount = findViewById(R.id.total_amount);

        cart = new Cart(); // Lấy giỏ hàng từ nơi bạn đã lưu trữ

        // Hiển thị danh sách sản phẩm trong giỏ hàng
        CartAdapter adapter = new CartAdapter(this, cart.getCartItems());
        //recyclerView.setAdapter(adapter);

        // Hiển thị tổng giá
        double totalPrice = cart.getTotalPrice();
        totalAmount.setText("Tổng: " + totalPrice + " VND");
    }
}
