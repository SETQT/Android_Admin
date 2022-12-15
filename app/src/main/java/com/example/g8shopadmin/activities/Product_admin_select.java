package com.example.g8shopadmin.activities;

public class Product_admin_select {
    private String id;
    private String image;
    private String title;
    private Integer cost;
    private Integer amount;



    public Product_admin_select(String id, String image, String title, Integer cost, Integer amount) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.cost = cost;
        this.amount = amount;

    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getAmount() {
        return amount;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMinimumCost(Integer cost) {
        this.cost = cost;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
