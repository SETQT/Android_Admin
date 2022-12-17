package com.example.g8shopadmin.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;

public class ListCustomerFragmentFirst extends Fragment implements View.OnClickListener {
    activity_list_customer main;

    // khai báo biến UI
    EditText edittext_list_customer;
    View icon_search;


    public static ListCustomerFragmentFirst newInstance(String strArg) {
        ListCustomerFragmentFirst fragment = new ListCustomerFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_list_customer)getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_list_customer_fragment_first, null);

        edittext_list_customer = (EditText) layout_first.findViewById(R.id.edittext_list_customer);
        icon_search = (View) layout_first.findViewById(R.id.icon_search_list_customer);

        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataSend = edittext_list_customer.getText().toString();
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });


        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }

    @Override
    public void onClick(View view) {

//
    }
}
