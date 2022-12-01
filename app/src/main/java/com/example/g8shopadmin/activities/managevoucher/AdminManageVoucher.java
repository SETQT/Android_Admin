package com.example.g8shopadmin.activities.managevoucher;

public class AdminManageVoucher {
    private Integer image;
    private String time;
    private String cost_sale;
    private String min_cost;
    private String text_da_su_dung;
    private String text_so_luong;
    private String btn1;

    public AdminManageVoucher(Integer image, String time, String cost_sale, String min_cost, String text_da_su_dung, String text_so_luong, String btn1) {
        this.image = image;
        this.time = time;
        this.cost_sale = cost_sale;
        this.min_cost = min_cost;
        this.text_da_su_dung = text_da_su_dung;
        this.text_so_luong = text_so_luong;
        this.btn1 = btn1;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost_sale() {
        return cost_sale;
    }

    public void setCost_sale(String cost_sale) {
        this.cost_sale = cost_sale;
    }

    public String getMin_cost() {
        return min_cost;
    }

    public void setMin_cost(String min_cost) {
        this.min_cost = min_cost;
    }

    public String getText_da_su_dung() {
        return text_da_su_dung;
    }

    public void setText_da_su_dung(String text_da_su_dung) {
        this.text_da_su_dung = text_da_su_dung;
    }

    public String getText_so_luong() {
        return text_so_luong;
    }

    public void setText_so_luong(String text_so_luong) {
        this.text_so_luong = text_so_luong;
    }

    public String getBtn1() {
        return btn1;
    }

    public void setBtn1(String btn1) {
        this.btn1 = btn1;
    }
}
