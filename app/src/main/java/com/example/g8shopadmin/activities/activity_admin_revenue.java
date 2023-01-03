package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.revenue.AdminRevenueFragmentFirst;
import com.example.g8shopadmin.activities.revenue.AdminRevenueFragmentSecond;
;

public class activity_admin_revenue extends FragmentActivity implements MainCallbacks {
    View icon_back;

    FragmentTransaction ft; AdminRevenueFragmentFirst firstFrag; AdminRevenueFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_revenue);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = AdminRevenueFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.admin_revenue_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = AdminRevenueFragmentSecond.newInstance("");
        ft.replace(R.id.admin_revenue_fragment_second, secondFrag);
        ft.commit();


        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(moveActivity);
            }
        });

    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
//        Toast.makeText(getApplication(), " MAIN GOT>> " + sender + "\n" + strValue, Toast.LENGTH_LONG).show();
        if (sender.equals("RED-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                firstFrag.onMsgFromMainToFragment(strValue);
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
