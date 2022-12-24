package com.example.g8shopadmin.activities.revenue;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_revenue;

public class AdminRevenueFragmentFirst extends Fragment {
    activity_admin_revenue main;
    String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String monthSelected;
    String[] year = {"2022", "2023", "2024", "2025"};
    String yearSelected;
    Spinner spinner_month, spinner_year;
    Button btn_confirm;

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

        spinner_month  = (Spinner) layout_first.findViewById(R.id.spinner_month);
        spinner_year  = (Spinner) layout_first.findViewById(R.id.spinner_year);
        btn_confirm = (Button) layout_first.findViewById(R.id.btn_confirm);

        //Spiner month
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spiner_create_voucher, month);
        spinner_month.setAdapter(adapter);
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //Spiner month
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.custom_spiner_create_voucher, year);
        spinner_year.setAdapter(adapter1);
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearSelected = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataSend = monthSelected + "," + yearSelected;
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });


        return layout_first;
    }// onCreateView


    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

    }
}
