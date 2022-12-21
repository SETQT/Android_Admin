package com.example.g8shopadmin.activities.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_order;

public class AdminOrderFragmentFirst extends Fragment {
    // this fragment shows a ListView
    activity_admin_order main;
    TextView admin_order_option_cho_xac_nhan, admin_order_option_dang_giao;
    TextView admin_order_option_da_giao;

    String stateMyOder;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static AdminOrderFragmentFirst newInstance(String strArg) {
        AdminOrderFragmentFirst fragment = new AdminOrderFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_admin_order) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    public void onMsgFromMainToFragment(String strValue) {
        stateMyOder = strValue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_order_fragment_first, null);

        admin_order_option_cho_xac_nhan = (TextView) layout_first.findViewById(R.id.admin_order_option_cho_xac_nhan);
        admin_order_option_dang_giao = (TextView) layout_first.findViewById(R.id.admin_order_option_dang_giao);
        admin_order_option_da_giao = (TextView) layout_first.findViewById(R.id.admin_order_option_da_giao);

        admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_order_option_cho_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "1";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });


        admin_order_option_dang_giao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "2";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        admin_order_option_da_giao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "3";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        if(stateMyOder != null) {
            switch (stateMyOder) {
                case "1":
                    admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    break;
                case "2":
                    admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    break;
                case "3":
                    admin_order_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    admin_order_option_dang_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    admin_order_option_da_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    break;
                default:
                    break;
            }
        }


        return layout_first;
    }// onCreateView

}// class
