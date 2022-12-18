package com.example.g8shopadmin.activities.revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_revenue;

public class AdminRevenueFragmentFirst extends Fragment {
    activity_admin_revenue main;
    TextView admin_revenue_option_chua_thanh_toan, admin_revenue_option_da_thanh_toan;

    public static AdminRevenueFragmentFirst newInstance(String strArg) {
        AdminRevenueFragmentFirst fragment = new AdminRevenueFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_admin_revenue) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_revenue_fragment_first, null);

        admin_revenue_option_chua_thanh_toan  = (TextView) layout_first.findViewById(R.id.admin_revenue_option_chua_thanh_toan);
        admin_revenue_option_da_thanh_toan  = (TextView) layout_first.findViewById(R.id.admin_revenue_option_da_thanh_toan);

        admin_revenue_option_da_thanh_toan.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_revenue_option_chua_thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_revenue_option_chua_thanh_toan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_revenue_option_da_thanh_toan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "chua";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        admin_revenue_option_da_thanh_toan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_revenue_option_chua_thanh_toan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_revenue_option_da_thanh_toan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "da";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});



        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

    }
}
