package com.example.g8shopadmin.managevoucher;

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
import com.example.g8shopadmin.activities.activity_admin_manage_voucher;

import java.util.ArrayList;

public class AdminManageVoucherFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_manage_voucher main;
    ListView listVoucher;
    ArrayList<AdminManageVoucher> Voucher = new ArrayList<AdminManageVoucher>();

    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> cost_sale = new ArrayList<>();
    ArrayList<String> min_cost = new ArrayList<>();
    ArrayList<String> text_da_su_dung = new ArrayList<>();
    ArrayList<String> text_so_luong = new ArrayList<>();
    ArrayList<String> btn1 = new ArrayList<>();

    public static AdminManageVoucherFragmentSecond newInstance(String strArg1) {
        AdminManageVoucherFragmentSecond fragment = new AdminManageVoucherFragmentSecond();
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
        main = (activity_admin_manage_voucher) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_manage_voucher_fragment_second, null);

        listVoucher = (ListView) layout_second.findViewById(R.id.admin_manage_voucher_listview);

        image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);
        time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");
        cost_sale.add("đ50.000");cost_sale.add("đ100.000");cost_sale.add("đ70.000");
        min_cost.add("Đơn tối thiểu: đ300.000");min_cost.add("Đơn tối thiểu: đ500.000"); min_cost.add("Đơn tối thiểu: đ400.000");
        text_da_su_dung.add("Đã sử dụng: 10");text_da_su_dung.add("Đã sử dụng: 0");text_da_su_dung.add("Đã sử dụng: 0");
        text_so_luong.add("Số lượng: 50");text_so_luong.add("Số lượng: 20");text_so_luong.add("Số lượng: 30");
        btn1.add("Sửa");btn1.add("Sửa");btn1.add("Sửa");

        for (int i = 0; i < image.size(); i++) {
            Voucher.add(new AdminManageVoucher(image.get(i), time.get(i), cost_sale.get(i), min_cost.get(i), text_da_su_dung.get(i), text_so_luong.get(i), btn1.get(i)));
        }

        AdminCustomManageVoucherListViewAdapter myAdapter = new AdminCustomManageVoucherListViewAdapter(getActivity(), R.layout.admin_custom_listview_manage_voucher, Voucher);
        listVoucher.setAdapter(myAdapter);


        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);

        if (strValue == "Dang hoat dong") {
            image.clear();time.clear();cost_sale.clear();min_cost.clear();
            text_so_luong.clear();text_da_su_dung.clear();btn1.clear();

            image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");time.add("25/11/2022 - 2/12/2022");
            cost_sale.add("đ50.000");cost_sale.add("đ100.000");cost_sale.add("đ70.000");
            min_cost.add("Đơn tối thiểu: đ300.000");min_cost.add("Đơn tối thiểu: đ500.000"); min_cost.add("Đơn tối thiểu: đ400.000");
            text_da_su_dung.add("Đã sử dụng: 10");text_da_su_dung.add("Đã sử dụng: 0");text_da_su_dung.add("Đã sử dụng: 0");
            text_so_luong.add("Số lượng: 50");text_so_luong.add("Số lượng: 20");text_so_luong.add("Số lượng: 30");
            btn1.add("Xóa");btn1.add("Xóa");btn1.add("Xóa");
            Voucher.clear();

            for (int i = 0; i < image.size(); i++) {
                Voucher.add(new AdminManageVoucher(image.get(i), time.get(i), cost_sale.get(i), min_cost.get(i), text_da_su_dung.get(i), text_so_luong.get(i), btn1.get(i)));
            }
        }

        if (strValue == "Sap dien ra") {
            image.clear();time.clear();cost_sale.clear();min_cost.clear();
            text_so_luong.clear();text_da_su_dung.clear();btn1.clear();

            image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);
            time.add("20/11/2022 - 30/11/2022");time.add("22/11/2022 - 30/11/2022");
            cost_sale.add("đ50.000");cost_sale.add("đ100.000");
            min_cost.add("Đơn tối thiểu: đ300.000");min_cost.add("Đơn tối thiểu: đ500.000");
            text_da_su_dung.add("Đã sử dụng: 10");text_da_su_dung.add("Đã sử dụng: 0");
            text_so_luong.add("Số lượng: 50");text_so_luong.add("Số lượng: 20");
            btn1.add("Sửa");btn1.add("Sửa");
            Voucher.clear();

            for (int i = 0; i < image.size(); i++) {
                Voucher.add(new AdminManageVoucher(image.get(i), time.get(i), cost_sale.get(i), min_cost.get(i), text_da_su_dung.get(i), text_so_luong.get(i), btn1.get(i)));
            }
        }

        if (strValue == "Da ket thuc") {
            image.clear();time.clear();cost_sale.clear();min_cost.clear();
            text_so_luong.clear();text_da_su_dung.clear();btn1.clear();

            image.add(R.drawable.ma_giam_gia);image.add(R.drawable.ma_giam_gia);
            time.add("14/11/2022 - 19/11/2022");time.add("10/11/2022 - 18/11/2022");
            cost_sale.add("đ50.000");cost_sale.add("đ200.000");
            min_cost.add("Đơn tối thiểu: đ300.000");min_cost.add("Đơn tối thiểu: đ1.000.000");
            text_da_su_dung.add("Đã sử dụng: 10");text_da_su_dung.add("Đã sử dụng: 10");
            text_so_luong.add("Số lượng: 10");text_so_luong.add("Số lượng: 10");
            btn1.add("Xem");btn1.add("Xem");
            Voucher.clear();

            for (int i = 0; i < image.size(); i++) {
                Voucher.add(new AdminManageVoucher(image.get(i), time.get(i), cost_sale.get(i), min_cost.get(i), text_da_su_dung.get(i), text_so_luong.get(i), btn1.get(i)));
            }
        }

        AdminCustomManageVoucherListViewAdapter myAdapter = new AdminCustomManageVoucherListViewAdapter(getActivity(), R.layout.admin_custom_listview_manage_voucher, Voucher);
        listVoucher.setAdapter(myAdapter);
    }


}
