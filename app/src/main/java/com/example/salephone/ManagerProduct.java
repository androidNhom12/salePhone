package com.example.salephone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.salephone.adapter.ProductAdapter;
import com.example.salephone.database.CreateDatabase;
import com.example.salephone.entity.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManagerProduct extends AppCompatActivity {
    private ActivityResultLauncher<Intent> mGetImageLauncher;
    private ImageView productImageView;
    ImageView backToHome;
    List<Product> productList;
    CreateDatabase db;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_managerproduct);

        db = db = new CreateDatabase(this);

        productList = db.getAllProduct();

        ProductAdapter adapter = new ProductAdapter(this,productList);
        ListView listView = findViewById(R.id.lvProductManager);
        listView.setAdapter(adapter);

        mGetImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            productImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        backToHome = findViewById(R.id.iconHome);
        backToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerProduct.this, HomeActivity.class);
            startActivity(intent);
        });

        //Handle add product
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> {
                // Pop up dialog add product
            AlertDialog.Builder builder = new AlertDialog.Builder(ManagerProduct.this);

            View modalView = getLayoutInflater().inflate(R.layout.add_product_modal,null);

            EditText productNameInput = modalView.findViewById(R.id.productNameInput);
            EditText productPriceInput = modalView.findViewById(R.id.productPriceInput);
            EditText productDescriptionInput = modalView.findViewById(R.id.productDescriptionInput);
            Button btnSaveProduct = modalView.findViewById(R.id.btnSaveProduct);
            Button btnCancelProduct = modalView.findViewById(R.id.btnCancelProduct);

            productImageView = modalView.findViewById(R.id.productImageView);
            Button btnAddImage = modalView.findViewById(R.id.btnAddImage);



            btnAddImage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mGetImageLauncher.launch(intent);
            });

            builder.setView(modalView);

            AlertDialog alertDialog = builder.create();
            //Add san pham
            btnSaveProduct.setOnClickListener(v -> {
                String productName = productNameInput.getText().toString();
                String productPrice = productPriceInput.getText().toString();
                String productDescription = productDescriptionInput.getText().toString();

                // Thực hiện hành động lưu sản phẩm
                if (!productName.isEmpty() && !productPrice.isEmpty() && !productDescription.isEmpty()) {
                    Product product = new Product();
                    product.setDescription(productDescription);
                    product.setName(productName);
                    product.setPrice(productPrice);

                    boolean check = db.addProduct(product);
                    if(!check) {
                        Toast.makeText(this, "Lưu sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    } else {
                        productList.clear();  // Xóa các sản phẩm cũ trong danh sách
                        productList.addAll(db.getAllProduct());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Sản phẩm đã được lưu", Toast.LENGTH_SHORT).show();
                    }
                    // Đóng Dialog sau khi lưu
                    alertDialog.dismiss();
                } else {
                    // Hiển thị thông báo nếu các trường bắt buộc không được nhập
                    Toast.makeText(ManagerProduct.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            });


            // Đóng dialog
            btnCancelProduct.setOnClickListener(v -> {
                alertDialog.dismiss();
            });

            // Hiển thị Dialog
            alertDialog.show();
        });
        //Update product
        Button btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            List<Product> selectedProducts = adapter.getSelectedProducts();
            if (selectedProducts.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn 1 sản phẩm để sửa", Toast.LENGTH_SHORT).show();
                return;
            }
            // Sửa sản phẩm đầu tiên (hoặc bạn có thể sửa tất cả sản phẩm được chọn)
            Product productToEdit = selectedProducts.get(0);

            //Pop up edit modal

            AlertDialog.Builder builder1 = new AlertDialog.Builder(ManagerProduct.this);

            View modalViewEdit = getLayoutInflater().inflate(R.layout.edit_product_modal,null);

            EditText productEditNameInput = modalViewEdit.findViewById(R.id.productEditNameInput);
            EditText productEditPriceInput = modalViewEdit.findViewById(R.id.productEditPriceInput);
            EditText productEditDescriptionInput = modalViewEdit.findViewById(R.id.productEditDescriptionInput);
            Button btnEditProduct = modalViewEdit.findViewById(R.id.btnEditProduct);
            Button btnCancelEditProduct = modalViewEdit.findViewById(R.id.btnCancelEditProduct);

            productEditNameInput.setText(productToEdit.getName());
            productEditPriceInput.setText(productToEdit.getPrice());
            productEditDescriptionInput.setText(productToEdit.getDescription());

            builder1.setView(modalViewEdit);

            AlertDialog alertDialog = builder1.create();
            alertDialog.show(); // Thêm dòng này để hiển thị dialog

            btnEditProduct.setOnClickListener(view -> {
                String name = productEditNameInput.getText().toString();
                String price = productEditPriceInput.getText().toString();
                String description = productEditDescriptionInput.getText().toString();
                if(!name.isEmpty() && !price.isEmpty() && !description.isEmpty()) {
                  productToEdit.setName(name);
                  productToEdit.setPrice(price);
                  productToEdit.setDescription(description);
                  boolean isUpdated = db.updateProduct(productToEdit);
                    if (isUpdated) {
                        Toast.makeText(this, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();

                        adapter.notifyDataSetChanged(); // Cập nhật giao diện
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                } else {
                    // Hiển thị thông báo nếu các trường bắt buộc không được nhập
                    Toast.makeText(ManagerProduct.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }


            });

            // Đóng dialog
            btnCancelEditProduct.setOnClickListener(view -> {
                alertDialog.dismiss();
            });
        });


        // Delete product
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> {
            List<Product> productList1 = adapter.getSelectedProducts();
            if (productList1.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn sản phẩm để xóa", Toast.LENGTH_SHORT).show();
                return;
            }
            // Xác nhận trước khi xóa
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa các sản phẩm đã chọn?");
            builder.setPositiveButton("Xóa", (dialog, which) -> {
                boolean isDeleted = db.deleteSelectedProducts(productList1);
                if (isDeleted) {
                    Toast.makeText(this, "Các sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();

                    // Cập nhật giao diện sau khi xóa
                    productList.removeAll(productList1);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
    }



}
