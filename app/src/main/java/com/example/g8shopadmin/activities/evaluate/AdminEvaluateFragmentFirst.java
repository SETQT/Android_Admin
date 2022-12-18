package com.example.g8shopadmin.activities.evaluate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_evaluate;


public class AdminEvaluateFragmentFirst extends Fragment {
    // this fragment shows a ListView
    activity_admin_evaluate main;
    TextView admin_custom_evaluate_option_tat_ca, admin_custom_evaluate_option_chua_phan_hoi;

    public static AdminEvaluateFragmentFirst newInstance(String strArg) {
        AdminEvaluateFragmentFirst fragment = new AdminEvaluateFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            main = (activity_admin_evaluate) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_evaluate_fragment_first, null);

        admin_custom_evaluate_option_tat_ca  = (TextView) layout_first.findViewById(R.id.admin_custom_evaluate_option_tat_ca);
        admin_custom_evaluate_option_chua_phan_hoi  = (TextView) layout_first.findViewById(R.id.admin_custom_evaluate_option_chua_phan_hoi);
        admin_custom_evaluate_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_custom_evaluate_option_tat_ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_evaluate_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_custom_evaluate_option_chua_phan_hoi.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "0";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        admin_custom_evaluate_option_chua_phan_hoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_evaluate_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_evaluate_option_chua_phan_hoi.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "1";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }
}// class

