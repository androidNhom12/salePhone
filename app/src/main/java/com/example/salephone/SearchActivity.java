package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.adapter.ListAdapter;
import com.example.salephone.adapter.ProductAdapter;
import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText edtSearchQuery;
    ListView lvSearchResults;
    CreateDatabase createDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        edtSearchQuery = findViewById(R.id.edtSearchQuery);
        lvSearchResults = findViewById(R.id.lvSearchResults);

        createDatabase = new CreateDatabase(this);
        createDatabase.open();
        loadAllProducts();

        edtSearchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                performSearch(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });
    }

    private void performSearch(String query) {
        List<Product> results;

        if (query.isEmpty()) {
            // Nếu ô tìm kiếm trống, hiển thị toàn bộ sản phẩm
            results = getAllProducts();
        } else {
            // Nếu có từ khóa tìm kiếm, thực hiện tìm kiếm
            results = searchProducts(query);
        }

        if (results != null && !results.isEmpty()) {
            // Hiển thị kết quả với adapter
            ListAdapter adapter = new ListAdapter(SearchActivity.this, results);
            lvSearchResults.setAdapter(adapter);

            // Lắng nghe sự kiện nhấn vào một sản phẩm trong ListView
            lvSearchResults.setOnItemClickListener((parent, view, position, id) -> {
                Product selectedProduct = results.get(position); // Lấy sản phẩm được chọn

                // Tạo một Intent để chuyển đến ProductDetailActivity với thông tin sản phẩm
                Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_name", selectedProduct.getName()); // Tên sản phẩm
                intent.putExtra("product_price", selectedProduct.getPrice()); // Giá sản phẩm
                intent.putExtra("product_description", selectedProduct.getDescription()); // Mô tả sản phẩm
                intent.putExtra("productImage", selectedProduct.getImage_url() != null ? selectedProduct.getImage_url() : ""); // Kiểm tra null với URL ảnh
                startActivity(intent); // Mở màn hình mới
            });
        } else {
            // Nếu không có sản phẩm, làm rỗng ListView
            lvSearchResults.setAdapter(null);
            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show(); // Thông báo nếu không có kết quả
        }
    }

    @SuppressLint("Range")
    private List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        Cursor cursor = createDatabase.getReadableDatabase().rawQuery(sql, new String[]{"%" + query + "%"});

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProduct_id(cursor.getInt(cursor.getColumnIndexOrThrow("product_id")));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow("price")));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setImage_url(cursor.getString(cursor.getColumnIndexOrThrow("image_url"))); // Nếu cần ảnh
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    private void loadAllProducts() {
        List<Product> allProducts = getAllProducts();
        if (allProducts != null && !allProducts.isEmpty()) {
            ListAdapter adapter = new ListAdapter(SearchActivity.this, allProducts);
            lvSearchResults.setAdapter(adapter);
            lvSearchResults.setOnItemClickListener((parent, view, position, id) -> {
                Product selectedProduct = allProducts.get(position);

                // Chuyển đến ProductDetailActivity với thông tin sản phẩm
                Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", selectedProduct.getProduct_id());
                intent.putExtra("product_name", selectedProduct.getName());
                intent.putExtra("product_price", selectedProduct.getPrice());
                intent.putExtra("product_description", selectedProduct.getDescription());
                startActivity(intent);
            });
        }
    }

    @SuppressLint("Range")
    private List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        Cursor cursor = createDatabase.getReadableDatabase().rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProduct_id(cursor.getInt(cursor.getColumnIndexOrThrow("product_id")));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow("price")));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setImage_url(cursor.getString(cursor.getColumnIndexOrThrow("image_url"))); // Nếu cần ảnh
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (createDatabase != null) {
            createDatabase.close(); // Đảm bảo cơ sở dữ liệu được đóng khi Activity bị hủy
        }
    }
}
