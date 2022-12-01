package com.example.g8shopadmin.managevoucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;

import java.util.ArrayList;

public class AdminCustomManageVoucherListViewAdapter extends ArrayAdapter<AdminManageVoucher> {

    ArrayList<AdminManageVoucher> voucher = new ArrayList<AdminManageVoucher>();


    public AdminCustomManageVoucherListViewAdapter(Context context, int resource, ArrayList<AdminManageVoucher> objects) {
        super(context, resource, objects);
        this.voucher = objects;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.admin_custom_listview_manage_voucher, null);

        ImageView image = (ImageView) v.findViewById(R.id.logo_ma_giam_gia);
        TextView time = (TextView)  v.findViewById(R.id.admin_custom_listview_manage_voucher_title);
        TextView cost_sale = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_cost_sale) ;
        TextView min_cost = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_min_cost);
        TextView text_da_su_dung = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_da_su_dung);
        TextView text_so_luong = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_so_luong);
        Button btn1 = (Button) v.findViewById(R.id.admin_custom_listview_manage_voucher_btn_01);

        time.setText(voucher.get(position).getTime());
        cost_sale.setText(voucher.get(position).getCost_sale());
        min_cost.setText(voucher.get(position).getMin_cost());
        text_da_su_dung.setText(voucher.get(position).getText_da_su_dung());
        text_so_luong.setText(voucher.get(position).getText_so_luong());
        btn1.setText(voucher.get(position).getBtn1());

        image.setImageResource(voucher.get(position).getImage());

        return v;

    }
}

