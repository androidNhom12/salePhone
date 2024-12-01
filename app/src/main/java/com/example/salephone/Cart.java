package com.example.salephone;

import com.example.salephone.entity.CartItem;
import com.example.salephone.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProduct_id() == product.getProduct_id()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(Product product) {
        cartItems.removeIf(item -> item.getProduct().getProduct_id() == product.getProduct_id());
    }

    // Lấy danh sách các sản phẩm trong giỏ hàng
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    // Tính tổng tiền của giỏ hàng
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            if (item.isSelected()) { // Chỉ cộng tiền nếu sản phẩm được chọn
                total += item.getTotalPrice();
            }
        }
        return total;
    }


    // Xóa tất cả các sản phẩm trong giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }

    // Đánh dấu tất cả các sản phẩm là đã chọn
    public void selectAllItems(boolean isSelected) {
        for (CartItem item : cartItems) {
            item.setSelected(isSelected);
        }
    }

    // Xóa các sản phẩm đã được chọn
    public void removeSelectedItems() {
        cartItems.removeIf(CartItem::isSelected);
    }

    // Lấy các sản phẩm đã được chọn
    public List<CartItem> getSelectedItems() {
        List<CartItem> selectedItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }
}
