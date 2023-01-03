package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.order.AdminOrderFragmentFirst;
import com.example.g8shopadmin.activities.order.AdminOrderFragmentSecond;

public class activity_admin_order extends FragmentActivity implements MainCallbacks {
    // khai báo biến UI
    View icon_back;

    FragmentTransaction ft;
    AdminOrderFragmentFirst firstFrag;
    AdminOrderFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = AdminOrderFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.admin_order_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = AdminOrderFragmentSecond.newInstance("");
        ft.replace(R.id.admin_order_fragment_second, secondFrag);
        ft.commit();

        Intent intent = getIntent();

        if (intent.hasExtra("state")) {
            String stateMyOrder = intent.getStringExtra("state");
            if (stateMyOrder == null) {
                firstFrag.onMsgFromMainToFragment("1");
                secondFrag.onMsgFromMainToFragment("1");
            } else {
                firstFrag.onMsgFromMainToFragment(stateMyOrder);
                secondFrag.onMsgFromMainToFragment(stateMyOrder);
            }
        }


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
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                secondFrag.onMsgFromMainToFragment(strValue);
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
    }
}

