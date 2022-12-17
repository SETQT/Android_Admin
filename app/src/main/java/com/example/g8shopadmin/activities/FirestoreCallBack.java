package com.example.g8shopadmin.activities;

import com.example.g8shopadmin.models.User;

import java.util.List;

public interface FirestoreCallBack {
    void onCallBack(List<User> list);
}