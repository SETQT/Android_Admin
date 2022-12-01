package com.example.g8shopadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.databinding.ActivityDashboardBinding;
import com.example.g8shopadmin.databinding.ActivitySignInBinding;
import com.example.g8shopadmin.utilities.PreferenceManager;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }

    // hàm lắng nghe các sự kiện trong layout
    private void setListeners() {
        binding.iconChat.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//            startActivity(intent);
//            finish();
        });
    }
}