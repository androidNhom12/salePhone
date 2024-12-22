package com.example.salephone.adapter;



import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salephone.R;
import com.example.salephone.entity.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;
    private UpdateTotalAmountListener updateTotalAmountListener;

    // Constructor nhận vào callback từ Activity để cập nhật tổng tiền
    public CartAdapter(Context context, List<CartItem> cartItems, UpdateTotalAmountListener updateTotalAmountListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.updateTotalAmountListener = updateTotalAmountListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.productName.setText(cartItem.getProduct().getName());
        int imageResId = context.getResources().getIdentifier(cartItem.getProduct().getImage_url(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            holder.productImage.setImageResource(imageResId);
        } else {
            holder.productImage.setImageResource(R.drawable.iphone); // Ảnh mặc định
        }
        holder.productPrice.setText("Giá: " + cartItem.getProduct().getPrice() + " K");
        holder.productQuantity.setText("Số lượng: " + cartItem.getQuantity());

        // Nút tăng giảm số lượng
        holder.increaseQuantity.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            notifyItemChanged(position);
            updateTotalAmountListener.updateTotalAmount();
        });

        holder.decreaseQuantity.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                notifyItemChanged(position);
                updateTotalAmountListener.updateTotalAmount();
            }
        });

        // Checkbox chọn sản phẩm
        holder.cbProduct.setChecked(cartItem.isSelected());
        holder.cbProduct.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cartItem.setSelected(isChecked);
            updateTotalAmountListener.updateTotalAmount();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder class để giữ view
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productQuantity, productPrice;
        ImageView productImage;
        CheckBox cbProduct;
        Button increaseQuantity, decreaseQuantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
            productQuantity = itemView.findViewById(R.id.productQuantity);
            productPrice = itemView.findViewById(R.id.productPrice);
            cbProduct = itemView.findViewById(R.id.cbProduct);
            increaseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantity = itemView.findViewById(R.id.decreaseQuantity);
        }
    }

    // Interface để cập nhật tổng tiền
    public interface UpdateTotalAmountListener {
        void updateTotalAmount();
    }
}
