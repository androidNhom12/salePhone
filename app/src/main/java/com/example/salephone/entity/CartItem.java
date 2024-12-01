package com.example.salephone.entity;

public class CartItem {
    private Product product;
    private int quantity;
    private boolean selected;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.selected = false;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getTotalPrice() {
        return Double.parseDouble(product.getPrice()) * quantity;
    }
}
