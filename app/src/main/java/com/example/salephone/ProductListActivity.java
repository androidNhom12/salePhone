package com.example.salephone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.adapter.ListAdapter;
import com.example.salephone.adapter.ProductAdapter;
import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private ListAdapter productListAdapter;
    private CreateDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Khởi tạo các thành phần trong layout
        listViewProducts = findViewById(R.id.listViewProducts);
        database = new CreateDatabase(this);

        // Xóa tất cả sản phẩm hiện có
        // database.deleteAllProducts();

        // Thêm sản phẩm mẫu vào cơ sở dữ liệu

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        List<Product> productList = database.getAllProduct();

        // Gắn adapter vào ListView để hiển thị sản phẩm
        productListAdapter = new ListAdapter(this, productList);
        listViewProducts.setAdapter(productListAdapter);

        // Thiết lập sự kiện click vào item trong ListView
        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy sản phẩm đã chọn
            Product selectedProduct = productList.get(position);

            // Tạo Intent để chuyển sang màn hình chi tiết sản phẩm
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);

            // Truyền thông tin sản phẩm vào Intent
            intent.putExtra("product_name", selectedProduct.getName()); // Tên sản phẩm
            intent.putExtra("product_price", selectedProduct.getPrice()); // Giá sản phẩm
            intent.putExtra("product_description", selectedProduct.getDescription()); // Mô tả sản phẩm
            intent.putExtra("productImage", selectedProduct.getImage_url() != null ? selectedProduct.getImage_url() : ""); // Kiểm tra null với URL ảnh
            // Bắt đầu Activity mới để hiển thị chi tiết sản phẩm
            startActivity(intent);
        });
    }

}


