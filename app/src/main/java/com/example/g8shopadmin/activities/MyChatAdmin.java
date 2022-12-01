package com.example.g8shopadmin.activities;

public class MyChatAdmin {

    private int id;
    private String username;
    private String last_message;
    private String last_time;
    private Integer image;


    public MyChatAdmin(int id, String username,String last_message,String last_time,Integer img) {
        this.setId(id);
        this.setUserName(username);
        this.setLast_message(last_message);
        this.setLast_time(last_time);
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

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}

