package com.example.g8shopadmin.activities;


import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.ListCustomerFragmentSecond;
import com.example.g8shopadmin.activities.activity_admin_create_promotions;
import com.example.g8shopadmin.activities.ListCustomerFragmentFirst;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class activity_list_customer extends FragmentActivity implements MainCallbacks, View.OnClickListener {
    FragmentTransaction ft; ListCustomerFragmentFirst firstFrag; ListCustomerFragmentSecond secondFrag;
    View icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_customer);

        icon_back = (View) findViewById(R.id.icon_back_list_customer);
        icon_back.setOnClickListener(this);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = ListCustomerFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.list_customer_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = ListCustomerFragmentSecond.newInstance("");
        ft.replace(R.id.list_customer_fragment_second, secondFrag);
        ft.commit();
    }
    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
//        Toast.makeText(getApplication(), " MAIN GOT>> " + sender + "\n" + strValue, Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View view) {
        if(view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_create_promotions.class);
            startActivity(moveActivity);
        }

    }
}