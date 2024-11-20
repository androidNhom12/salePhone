package com.example.salephone.entity;

public class Order {
    private int order_id;
    private int user_id;
    private String date;
    private String status;
    private double amount;

    public Order() {
    }

    public Order(int order_id, int user_id, String date, String status, double amount) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.date = date;
        this.status = status;
        this.amount = amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
