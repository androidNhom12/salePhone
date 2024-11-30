package com.example.salephone.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.salephone.R;
import com.example.salephone.entity.CartItem;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        CartItem cartItem = cartItems.get(position);
        TextView productName = convertView.findViewById(R.id.productName);
        ImageView productImage = convertView.findViewById(R.id.productImage);
        TextView productQuantity = convertView.findViewById(R.id.productQuantity);
        TextView productPrice = convertView.findViewById(R.id.productPrice);

        productName.setText(cartItem.getProduct().getName());
        productImage.setImageURI(Uri.parse(cartItem.getProduct().getImage_url()));
        productPrice.setText("Giá: " + cartItem.getProduct().getPrice() + " VND");
        productQuantity.setText("Số lượng: " + cartItem.getQuantity());

        return convertView;
    }
}