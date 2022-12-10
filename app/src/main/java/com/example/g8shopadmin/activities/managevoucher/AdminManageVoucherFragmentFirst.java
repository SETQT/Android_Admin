package com.example.g8shopadmin.activities.managevoucher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_manage_voucher;
import com.example.g8shopadmin.databinding.ActivityAdminManageVoucherBinding;
import com.example.g8shopadmin.utilities.PreferenceManager;

public class AdminManageVoucherFragmentFirst extends Fragment {
    // this fragment shows a ListView
    activity_admin_manage_voucher main;
    TextView admin_custom_manage_voucher_option_dang_hoat_dong, admin_custom_manage_voucher_option_da_ket_thuc;
    TextView admin_custom_manage_voucher_option_sap_dien_ra;

    public static AdminManageVoucherFragmentFirst newInstance(String strArg) {
        AdminManageVoucherFragmentFirst fragment = new AdminManageVoucherFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            main = (activity_admin_manage_voucher) getActivity();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_manage_voucher_fragment_first, null);

        admin_custom_manage_voucher_option_dang_hoat_dong  = (TextView) layout_first.findViewById(R.id.admin_custom_manage_voucher_option_dang_hoat_dong);
        admin_custom_manage_voucher_option_da_ket_thuc  = (TextView) layout_first.findViewById(R.id.admin_custom_manage_voucher_option_da_ket_thuc);
        admin_custom_manage_voucher_option_sap_dien_ra = (TextView) layout_first.findViewById(R.id.admin_custom_manage_voucher_option_sap_dien_ra);
        admin_custom_manage_voucher_option_dang_hoat_dong.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_custom_manage_voucher_option_dang_hoat_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_manage_voucher_option_dang_hoat_dong.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_custom_manage_voucher_option_da_ket_thuc.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_manage_voucher_option_sap_dien_ra.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "1";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        admin_custom_manage_voucher_option_sap_dien_ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_manage_voucher_option_dang_hoat_dong.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_manage_voucher_option_da_ket_thuc.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_manage_voucher_option_sap_dien_ra.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "2";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        admin_custom_manage_voucher_option_da_ket_thuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_manage_voucher_option_dang_hoat_dong.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_manage_voucher_option_da_ket_thuc.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_custom_manage_voucher_option_sap_dien_ra.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "3";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }});

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }
}// class
