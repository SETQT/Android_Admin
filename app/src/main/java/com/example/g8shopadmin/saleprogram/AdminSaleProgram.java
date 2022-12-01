package com.example.g8shopadmin.saleprogram;

public class AdminSaleProgram {
    private Integer image;
    private String title;
    private String time;

    public AdminSaleProgram(Integer image, String title, String time) {
        this.image = image;
        this.title = title;
        this.time = time;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
