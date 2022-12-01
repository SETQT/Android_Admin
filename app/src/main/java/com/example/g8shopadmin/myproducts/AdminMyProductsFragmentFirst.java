package com.example.g8shopadmin.myproducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_my_products;

public class AdminMyProductsFragmentFirst extends Fragment{
    // this fragment shows a ListView
    activity_admin_my_products main;
    TextView admin_custom_my_products_option_con_hang, admin_custom_my_products_option_het_hang;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static AdminMyProductsFragmentFirst newInstance(String strArg) {
        AdminMyProductsFragmentFirst fragment = new AdminMyProductsFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_admin_my_products) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_my_products_fragment_first, null);

        admin_custom_my_products_option_con_hang  = (TextView) layout_first.findViewById(R.id.admin_custom_my_products_option_con_hang);
        admin_custom_my_products_option_het_hang  = (TextView) layout_first.findViewById(R.id.admin_custom_my_products_option_het_hang);

        admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_custom_my_products_option_con_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_custom_my_products_option_het_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "Con hang";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        admin_custom_my_products_option_het_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_my_products_option_het_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "Het hang";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

    }
}// class
