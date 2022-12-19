package com.example.g8shopadmin.activities;

public class Product_Promotions {

    private int id;
    private String name;
    private String cost;
    private String quantity;
    private String percent_decrease;
    private Integer image;


    public Product_Promotions(int id, String name, String cost,String quantity,String percent_decrease,Integer img) {
        this.setId(id);
        this.setName(name);
        this.setCost(cost);
        this.setQuantity(quantity);
        this.setPercent(percent_decrease);
        this.setImage(img);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPercent() {
        return percent_decrease;
    }

    public void setPercent(String percent) {
        this.percent_decrease = percent;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}