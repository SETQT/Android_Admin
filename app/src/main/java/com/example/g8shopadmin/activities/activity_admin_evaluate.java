package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.evaluate.AdminEvaluateFragmentFirst;
import com.example.g8shopadmin.activities.evaluate.AdminEvaluateFragmentSecond;

public class activity_admin_evaluate extends FragmentActivity implements MainCallbacks, View.OnClickListener {

    View icon_back;
    FragmentTransaction ft;
    AdminEvaluateFragmentFirst firstFrag;
    AdminEvaluateFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_evaluate);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = AdminEvaluateFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.admin_evaluate_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = AdminEvaluateFragmentSecond.newInstance("");
        ft.replace(R.id.admin_evaluate_fragment_second, secondFrag);
        ft.commit();
        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra("state")) {
            String stateMyOrder = intent.getStringExtra("state");
            if (stateMyOrder == null) {
                firstFrag.onMsgFromMainToFragment("0");
                secondFrag.onMsgFromMainToFragment("0");
            } else {
                firstFrag.onMsgFromMainToFragment(stateMyOrder);
                secondFrag.onMsgFromMainToFragment(stateMyOrder);
            }
        }
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {

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

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(moveActivity);
        }
    }
}
