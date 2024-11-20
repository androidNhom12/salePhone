package com.example.salephone.entity;

public class Product {
    private int product_id;
    private String name;
    private String price;
    private String description;
    private String image_url;

    public Product() {
    }

    public Product(int product_id, String name, String price, String description, String image_url) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image_url = image_url;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
