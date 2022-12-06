package com.example.g8shopadmin.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.example.g8shopadmin.databinding.ActivitySignInBinding;
import com.example.g8shopadmin.utilities.Constants;
import com.example.g8shopadmin.utilities.PreferenceManager;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext()); // lưu trữ các dữ liệu key-value dựng sẵn android

        // Khi thoát app dô lại không cần đăng nhập nữa
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN )){
            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    // hàm lắng nghe các sự kiện trong layout
    private void setListeners() {
        binding.btnDangnhap.setOnClickListener(view -> {
            if (isValidSignInDetails()) {
                signIn();
            }
        });
    }

    private void signIn() {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ADMIN)
                .whereEqualTo(Constants.KEY_ADMIN_NAME, binding.inputAdminName.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.inputAdminPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    //Log.d("DOC", "sldoc: " + task.getResult().getDocuments().size());
                    if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true); // đã đăng nhập
                        preferenceManager.putString(Constants.KEY_ADMIN_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_ADMIN_NAME, documentSnapshot.getString(Constants.KEY_ADMIN_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        loading(false);
                        showToast("Đăng nhập không thành công");
                    }
                });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.btnDangnhap.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.btnDangnhap.setVisibility(View.VISIBLE);
        }
    }


    // Hàm show các thông báo
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Hàm check valid tài khoản admin
    private Boolean isValidSignInDetails() {
        if (binding.inputAdminName.getText().toString().trim().isEmpty()) {
            showToast("Nhập tên tài khoản");
            return false;
        } else if (binding.inputAdminPassword.getText().toString().trim().isEmpty()) {
            showToast("Nhập mật khẩu");
            return false;
        } else {
            return true;
        }
    }
}