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
import com.example.g8shopadmin.activities.activity_list_customer;

import java.util.ArrayList;

public class ListCustomerFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_list_customer main;
    ListView list_customer_listview;


    ArrayList<Customer_ListCustomer> ProductArray = new ArrayList<Customer_ListCustomer>();

    ArrayList<String> username = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();



    public static ListCustomerFragmentSecond newInstance(String strArg1) {
        ListCustomerFragmentSecond fragment = new ListCustomerFragmentSecond();
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

        main = (activity_list_customer) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_list_customer_fragment_second, null);

        list_customer_listview = (ListView) layout_second.findViewById(R.id.list_customer_listview);


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
