package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.managevoucher.AdminManageVoucherFragmentFirst;
import com.example.g8shopadmin.activities.managevoucher.AdminManageVoucherFragmentSecond;
import com.example.g8shopadmin.databinding.ActivityAdminManageVoucherBinding;

public class activity_admin_manage_voucher extends FragmentActivity implements MainCallbacks, View.OnClickListener {
    FragmentTransaction ft; AdminManageVoucherFragmentFirst firstFrag; AdminManageVoucherFragmentSecond secondFrag;

    private ActivityAdminManageVoucherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminManageVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = AdminManageVoucherFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.admin_manage_voucher_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = AdminManageVoucherFragmentSecond.newInstance("");
        ft.replace(R.id.admin_manage_voucher_fragment_second, secondFrag);
        ft.commit();

        binding.iconBack.setOnClickListener(this);
        binding.adminManageVoucherButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.iconBack.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(moveActivity);
        }

        if(view.getId() == binding.adminManageVoucherButton.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_create_voucher.class);
            startActivity(moveActivity);
        }
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
        if (sender.equals("RED-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                firstFrag.onMsgFromMainToFragment( strValue);
            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                secondFrag.onMsgFromMainToFragment( strValue);
            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
    }
}

