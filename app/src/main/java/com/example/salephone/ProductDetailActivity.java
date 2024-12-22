package com.example.salephone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ các Views
        Button btn = findViewById(R.id.button);
        TextView textViewProductName = findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = findViewById(R.id.textViewProductPrice);
        TextView textViewProductDescription = findViewById(R.id.textViewProductDescription);
        ImageView imageViewProduct = findViewById(R.id.imageViewProduct);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String productName = intent.getStringExtra("product_name");
        String productPrice = intent.getStringExtra("product_price");
        String productDescription = intent.getStringExtra("product_description");
        String productImageUrl = intent.getStringExtra("productImage");

// Kiểm tra null để tránh lỗi
        if (productName == null) productName = "Tên sản phẩm không khả dụng";
        if (productPrice == null) productPrice = "0";
        if (productDescription == null) productDescription = "Mô tả không khả dụng";
        if (productImageUrl == null) productImageUrl = ""; // Chuỗi rỗng nếu không có ảnh
        // Đảm bảo có khóa đúng

        // Hiển thị thông tin sản phẩm
        textViewProductName.setText(productName);
        textViewProductPrice.setText("Giá "+ productPrice +" VNĐ");
        textViewProductDescription.setText(productDescription);


        // Set Image
        // Lấy ảnh từ tài nguyên drawable bằng cách sử dụng getIdentifier
        if (!productImageUrl.isEmpty()) {
            int imageResId = getResources().getIdentifier(productImageUrl, "drawable", getPackageName());
            if (imageResId != 0) {
                imageViewProduct.setImageResource(imageResId);
            } else {
                imageViewProduct.setImageResource(R.drawable.iphone); // Ảnh mặc định nếu không tìm thấy
            }
        } else {
            imageViewProduct.setImageResource(R.drawable.iphone); // Ảnh mặc định nếu không có URL
        }

//        //Bấm để thêm sp vaào giỏ hàng
//        btn.setOnClickListener();
    }
}



