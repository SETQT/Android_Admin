package com.example.g8shopadmin.activities.saleprogram;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_sale_program;

import java.util.ArrayList;

public class AdminSaleProgramFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_sale_program main;
    ListView listSale;
    ArrayList<AdminSaleProgram> SaleProgram = new ArrayList<AdminSaleProgram>();

    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();

    public static AdminSaleProgramFragmentSecond newInstance(String strArg1) {
        AdminSaleProgramFragmentSecond fragment = new AdminSaleProgramFragmentSecond();
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
        main = (activity_admin_sale_program) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_sale_program_fragment_second, null);

        listSale = (ListView) layout_second.findViewById(R.id.admin_sale_program_listview);

        image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
        title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
        time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");

        for (int i = 0; i < image.size(); i++) {
            SaleProgram.add(new AdminSaleProgram(image.get(i), title.get(i), time.get(i)));
        }

        AdminCustomSaleProgramListViewAdapter myAdapter = new AdminCustomSaleProgramListViewAdapter(getActivity(), R.layout.admin_custom_listview_sale_program, SaleProgram);
        listSale.setAdapter(myAdapter);


        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);

        if (strValue == "Dang hoat dong") {
            image.clear();time.clear();title.clear();

            image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");
            SaleProgram.clear();

            for (int i = 0; i < image.size(); i++) {
                SaleProgram.add(new AdminSaleProgram(image.get(i), title.get(i), time.get(i)));
            }
        }

        if (strValue == "Sap dien ra") {
            image.clear();time.clear();title.clear();
            image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");
            image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");
            SaleProgram.clear();

            for (int i = 0; i < image.size(); i++) {
                SaleProgram.add(new AdminSaleProgram(image.get(i), title.get(i), time.get(i)));
            }
        }

        if (strValue == "Da ket thuc") {
            image.clear();time.clear();title.clear();

            image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");
            image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            title.add("Giảm giá bất ngờ"); title.add("Giảm giá bất ngờ");
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");
            SaleProgram.clear();

            for (int i = 0; i < image.size(); i++) {
                SaleProgram.add(new AdminSaleProgram(image.get(i), title.get(i), time.get(i)));
            }
        }

        AdminCustomSaleProgramListViewAdapter myAdapter = new AdminCustomSaleProgramListViewAdapter(getActivity(), R.layout.admin_custom_listview_sale_program, SaleProgram);
        listSale.setAdapter(myAdapter);
    }


}

