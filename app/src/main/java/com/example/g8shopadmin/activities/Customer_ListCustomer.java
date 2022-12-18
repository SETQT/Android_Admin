package com.example.g8shopadmin.activities;

public class Customer_ListCustomer {
    private int id;
    private String username;
    private Integer image;


    public Customer_ListCustomer(int id, String username,Integer img) {
        this.setId(id);
        this.setUserName(username);
        this.setImage(img);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }


    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
