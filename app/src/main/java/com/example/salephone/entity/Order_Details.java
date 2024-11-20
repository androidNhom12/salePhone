package com.example.salephone.entity;

public class Order_Details {
    private int order_details_id;
    private int order_id;
    private int product_id;
    private int quantity;
    private double price;

    public Order_Details() {
    }

    public Order_Details(int order_details_id, int order_id, int product_id, int quantity, double price) {
        this.order_details_id = order_details_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(int order_details_id) {
        this.order_details_id = order_details_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
