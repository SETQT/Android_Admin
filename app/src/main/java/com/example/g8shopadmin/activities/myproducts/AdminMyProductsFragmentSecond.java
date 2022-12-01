package com.example.g8shopadmin.activities.myproducts;

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
import com.example.g8shopadmin.activities.activity_admin_my_products;

import java.util.ArrayList;

public class AdminMyProductsFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_my_products main;
    ListView listMyProducts;
    ArrayList<AdminMyProducts> MyProducts = new ArrayList<AdminMyProducts>();

    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> cost = new ArrayList<>();
    ArrayList<String> text_kho_hang = new ArrayList<>();
    ArrayList<String> text_da_ban = new ArrayList<>();
    ArrayList<String> text_thich = new ArrayList<>();
    ArrayList<String> text_luot_xem = new ArrayList<>();

    public static AdminMyProductsFragmentSecond newInstance(String strArg1) {
        AdminMyProductsFragmentSecond fragment = new AdminMyProductsFragmentSecond();
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
        main = (activity_admin_my_products) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_my_products_fragment_second, null);

        listMyProducts = (ListView) layout_second.findViewById(R.id.admin_my_products_listview);

        image.add(R.drawable.mono1);image.add(R.drawable.mono1);
        image.add(R.drawable.mono1);image.add(R.drawable.mono1);
        name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo khoác");name.add("Áo thun");
        cost.add("đ100.000");cost.add("đ200.000");cost.add("đ150.000");cost.add("đ100.000");
        text_kho_hang.add("Kho hàng: 10");text_kho_hang.add("Kho hàng: 15");
        text_kho_hang.add("Kho hàng: 6");text_kho_hang.add("Kho hàng: 40");
        text_da_ban.add("Đã bán: 10");text_da_ban.add("Đã bán: 10");
        text_da_ban.add("Đã bán: 10");text_da_ban.add("Đã bán: 10");
        text_thich.add("Thích: 100");text_thich.add("Thích: 100");
        text_thich.add("Thích: 100");text_thich.add("Thích: 100");
        text_luot_xem.add("Lượt xem: 120");text_luot_xem.add("Lượt xem: 120");
        text_luot_xem.add("Lượt xem: 150");text_luot_xem.add("Lượt xem: 120");

        for (int i = 0; i < image.size(); i++) {
            MyProducts.add(new AdminMyProducts(i, image.get(i), name.get(i), cost.get(i), text_kho_hang.get(i), text_da_ban.get(i), text_thich.get(i), text_luot_xem.get(i)));
        }

        AdminCustomMyProductsListViewAdapter myAdapter = new AdminCustomMyProductsListViewAdapter(getActivity(), R.layout.admin_custom_listview_my_products, MyProducts);
        listMyProducts.setAdapter(myAdapter);


        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);

        if (strValue == "Con hang") {
            name.clear(); text_luot_xem.clear(); text_thich.clear(); text_da_ban.clear();
            text_kho_hang.clear(); image.clear(); cost.clear();

            image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            cost.add("đ100.000");cost.add("đ200.000");
            text_kho_hang.add("Kho hàng: 10");text_kho_hang.add("Kho hàng: 15");
            text_da_ban.add("Đã bán: 10");text_da_ban.add("Đã bán: 10");
            text_thich.add("Thích: 100");text_thich.add("Thích: 100");
            text_luot_xem.add("Lượt xem: 120");text_luot_xem.add("Lượt xem: 120");

            MyProducts.clear();

            for (int i = 0; i < image.size(); i++) {
                MyProducts.add(new AdminMyProducts(i, image.get(i), name.get(i), cost.get(i), text_kho_hang.get(i), text_da_ban.get(i), text_thich.get(i), text_luot_xem.get(i)));
            }
        }

        if (strValue == "Het hang") {
            name.clear(); text_luot_xem.clear(); text_thich.clear(); text_da_ban.clear();
            text_kho_hang.clear(); image.clear(); cost.clear();

            image.add(R.drawable.mono1);image.add(R.drawable.mono1);image.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");name.add("Áo thun");
            cost.add("đ100.000");cost.add("đ200.000");cost.add("đ200.000");
            text_kho_hang.add("Kho hàng: 10");text_kho_hang.add("Kho hàng: 15");text_kho_hang.add("Kho hàng: 15");
            text_da_ban.add("Đã bán: 10");text_da_ban.add("Đã bán: 10");text_da_ban.add("Đã bán: 10");
            text_thich.add("Thích: 100");text_thich.add("Thích: 100");text_thich.add("Thích: 100");
            text_luot_xem.add("Lượt xem: 120");text_luot_xem.add("Lượt xem: 120");text_luot_xem.add("Lượt xem: 120");

            MyProducts.clear();

            for (int i = 0; i < image.size(); i++) {
                MyProducts.add(new AdminMyProducts(i, image.get(i), name.get(i), cost.get(i), text_kho_hang.get(i), text_da_ban.get(i), text_thich.get(i), text_luot_xem.get(i)));
            }
        }
        AdminCustomMyProductsListViewAdapter myAdapter = new AdminCustomMyProductsListViewAdapter(getActivity(), R.layout.admin_custom_listview_my_products, MyProducts);
        listMyProducts.setAdapter(myAdapter);

    }


}
