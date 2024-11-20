package com.example.salephone.entity;

public class User {
    public  int  user_id;

    public User() {
    }

    public  String  user_username;
    public  String  user_password;
    public  String  user_phone;
    public  String  user_role;
    public  String  user_create_at;

    public User(int user_id, String user_username, String user_password, String user_phone, String user_role, String user_create_at) {
        this.user_id = user_id;
        this.user_username = user_username;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_role = user_role;
        this.user_create_at = user_create_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_username() {
        return user_username;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_role() {
        return user_role;
    }

    public String getUser_create_at() {
        return user_create_at;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public void setUser_create_at(String user_create_at) {
        this.user_create_at = user_create_at;
    }
}
