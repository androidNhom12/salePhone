package com.example.salephone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.salephone.R;
import com.example.salephone.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> productList;
    private List<Boolean> selectedStates; // Lưu trạng thái CheckBox

    public ListAdapter(Context context, List<Product> productList) {
        super(context, R.layout.list_item, productList);
        this.context = context;
        this.productList = productList;

        // Khởi tạo danh sách trạng thái, mặc định tất cả đều là "false"
        selectedStates = new ArrayList<>(Collections.nCopies(productList.size(), false));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }
        Product product = productList.get(position);

        // Set Image
        ImageView productImage = convertView.findViewById(R.id.productImage);
        int imageResId = context.getResources().getIdentifier(product.getImage_url(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            productImage.setImageResource(imageResId);
        } else {
            productImage.setImageResource(R.drawable.iphone); // Ảnh mặc định
        }

        // Set Name
        TextView productName = convertView.findViewById(R.id.productName);
        productName.setText(product.getName());

        // Set Price
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        productPrice.setText("Giá: " + product.getPrice() + " K");

        TextView productQuantity = convertView.findViewById(R.id.productDes);
        productQuantity.setText(product.getDescription());

        return convertView;
    }
    public void updateProductList(List<Product> newProductList) {
        this.productList.clear();
        this.productList.addAll(newProductList);
        notifyDataSetChanged();
    }

    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if (selectedStates.get(i)) {
                selectedProducts.add(productList.get(i));
            }
        }
        return selectedProducts;
    }

}