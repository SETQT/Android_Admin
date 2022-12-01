package com.example.g8shopadmin.activities.myproducts;

public class AdminMyProducts {
    private int id;
    private Integer picture;
    private String name;
    private String cost;
    private String text_kho_hang;
    private String text_da_ban;
    private String text_thich;
    private String text_luot_xem;

    public AdminMyProducts(int id, Integer picture, String name, String cost, String text_kho_hang, String text_da_ban, String text_thich, String text_luot_xem) {
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.cost = cost;
        this.text_kho_hang = text_kho_hang;
        this.text_da_ban = text_da_ban;
        this.text_thich = text_thich;
        this.text_luot_xem = text_luot_xem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getText_kho_hang() {
        return text_kho_hang;
    }

    public void setText_kho_hang(String text_kho_hang) {
        this.text_kho_hang = text_kho_hang;
    }

    public String getText_da_ban() {
        return text_da_ban;
    }

    public void setText_da_ban(String text_da_ban) {
        this.text_da_ban = text_da_ban;
    }

    public String getText_thich() {
        return text_thich;
    }

    public void setText_thich(String text_thich) {
        this.text_thich = text_thich;
    }

    public String getText_luot_xem() {
        return text_luot_xem;
    }

    public void setText_luot_xem(String text_luot_xem) {
        this.text_luot_xem = text_luot_xem;
    }
}



