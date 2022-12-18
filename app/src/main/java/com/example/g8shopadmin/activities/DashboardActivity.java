package com.example.g8shopadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.databinding.ActivityDashboardBinding;
import com.example.g8shopadmin.databinding.ActivitySignInBinding;
import com.example.g8shopadmin.utilities.Constants;
import com.example.g8shopadmin.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class DashboardActivity extends activity_base implements View.OnClickListener {

    private ActivityDashboardBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager =  new PreferenceManager(getApplicationContext());

        setListeners();
        getToken();
    }

    // hàm lắng nghe các sự kiện trong layout
    private void setListeners() {
        binding.iconLogout.setOnClickListener(view -> signOut());
        binding.iconChat.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), activity_dashboard_chat.class));
        });
        binding.iconProductAdmin.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), activity_admin_my_products.class));
        });
        binding.adminWaitConfirm.setOnClickListener(this);
        binding.voucherAdmin.setOnClickListener(this);
        binding.adminWaitRate.setOnClickListener(this);
        binding.historyOrder.setOnClickListener(this);
        binding.revenueAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.adminWaitConfirm.getId()) {

        }

        if(view.getId() == binding.voucherAdmin.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_manage_voucher.class);
            startActivity(intent);
        }
        if(view.getId() == binding.adminWaitRate.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_evaluate.class);
            startActivity(intent);
        }
        if(view.getId() == binding.historyOrder.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_order.class);
            startActivity(intent);
        }
        if(view.getId() == binding.revenueAdmin.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_revenue.class);
            startActivity(intent);
        }
    }

    // Hàm show các thông báo
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token){
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                db.collection(Constants.KEY_COLLECTION_ADMIN).document(
                        preferenceManager.getString(Constants.KEY_ADMIN_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                //.addOnSuccessListener(unused -> showToast("Token updated"))
                .addOnFailureListener(e->showToast("Unable to update token"));
    }

    private void signOut(){
        showToast("Signing out ...");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                db.collection(Constants.KEY_COLLECTION_ADMIN).document(
                        preferenceManager.getString(Constants.KEY_ADMIN_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnCompleteListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->showToast("Đăng xuất không thành công"));
    }
}