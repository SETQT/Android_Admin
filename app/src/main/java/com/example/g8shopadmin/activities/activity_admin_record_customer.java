package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.databinding.ActivityAdminRecordCustomerBinding;

public class activity_admin_record_customer extends Activity implements View.OnClickListener {
    private ActivityAdminRecordCustomerBinding binding;
    View icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRecordCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        icon_back = (View) findViewById(R.id.admin_record_ic_back);
        icon_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_list_customer.class);
            startActivity(moveActivity);
        }
    }
}