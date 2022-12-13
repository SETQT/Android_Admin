package com.example.g8shopadmin.activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;


public class selectProductFragmentFirst extends Fragment implements View.OnClickListener {
    // this fragment shows a ListView
    activity_admin_select_product main;

    // khai báo biến UI
    EditText edittext_select_product;
    View icon_search;


    public static selectProductFragmentFirst newInstance(String strArg) {
        selectProductFragmentFirst fragment = new selectProductFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_admin_select_product)getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_select_product_fragment_first, null);

        edittext_select_product = (EditText) layout_first.findViewById(R.id.edittext_select_product);
        icon_search = (View) layout_first.findViewById(R.id.icon_search_select_product);

        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataSend = edittext_select_product.getText().toString();
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
