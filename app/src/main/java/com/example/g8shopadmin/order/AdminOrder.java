package com.example.g8shopadmin.order;

public class AdminOrder {
    private int id;
    private Integer avatar;
    private String name_customer;
    private String name_option;
    private Integer picture;
    private String name;
    private String size;
    private String count;
    private String old_cost;
    private String new_cost;
    private String total;
    private String button_option;
    private String ma_don_hang;

    public AdminOrder(int id, Integer avatar, String name_customer, String name_option, Integer picture, String name, String size, String count, String old_cost, String new_cost, String total, String button_option, String ma_don_hang) {
        this.id = id;
        this.avatar = avatar;
        this.name_customer = name_customer;
        this.name_option = name_option;
        this.picture = picture;
        this.name = name;
        this.size = size;
        this.count = count;
        this.old_cost = old_cost;
        this.new_cost = new_cost;
        this.total = total;
        this.button_option = button_option;
        this.ma_don_hang = ma_don_hang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public String getName_option() {
        return name_option;
    }

    public void setName_option(String name_option) {
        this.name_option = name_option;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getOld_cost() {
        return old_cost;
    }

    public void setOld_cost(String old_cost) {
        this.old_cost = old_cost;
    }

    public String getNew_cost() {
        return new_cost;
    }

    public void setNew_cost(String new_cost) {
        this.new_cost = new_cost;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getButton_option() {
        return button_option;
    }

    public void setButton_option(String button_option) {
        this.button_option = button_option;
    }

    public String getMa_don_hang() {
        return ma_don_hang;
    }

    public void setMa_don_hang(String ma_don_hang) {
        this.ma_don_hang = ma_don_hang;
    }
}



