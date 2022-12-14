package com.example.g8shopadmin.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.g8shopadmin.R;

import java.util.ArrayList;

public class selectProductFragmentSecond extends Fragment implements FragmentCallbacks {
    // khai báo biến UI
    activity_admin_select_product main;
    ListView select_product_listview;


    ArrayList<Product_admin_select> ProductArray = new ArrayList<Product_admin_select>();

    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> cost = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> amount = new ArrayList<>();



    public static selectProductFragmentSecond newInstance(String strArg1) {
        selectProductFragmentSecond fragment = new selectProductFragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }

        main = (activity_admin_select_product) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_select_product_fragment_second, null);

        select_product_listview = (ListView) layout_second.findViewById(R.id.select_product_listview);


        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }
        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);
        Toast.makeText(main, "data_send:" + strValue, Toast.LENGTH_SHORT).show();


    }
}
