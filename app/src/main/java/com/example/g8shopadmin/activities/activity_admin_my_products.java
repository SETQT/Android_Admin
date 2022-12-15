package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.myproducts.AdminMyProductsFragmentFirst;
import com.example.g8shopadmin.activities.myproducts.AdminMyProductsFragmentSecond;

public class activity_admin_my_products extends FragmentActivity implements MainCallbacks {
    // khai báo biến UI
    View icon_back, icon_chat;
    Button add;


    FragmentTransaction ft;
    AdminMyProductsFragmentFirst firstFrag;
    AdminMyProductsFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_my_products);


        add = (Button) findViewById(R.id.custom_rectangle_button_footer);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = AdminMyProductsFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.admin_my_products_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = AdminMyProductsFragmentSecond.newInstance("");
        ft.replace(R.id.admin_my_products_fragment_second, secondFrag);
        ft.commit();


        icon_back = (View) findViewById(R.id.icon_back);
        icon_chat = (View) findViewById(R.id.icon_chat);

        icon_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
//                moveActivity.putExtra("name_activity", "activity_dashboard");
//                startActivity(moveActivity);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), activity_admin_create_product.class));
            }
        });
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

