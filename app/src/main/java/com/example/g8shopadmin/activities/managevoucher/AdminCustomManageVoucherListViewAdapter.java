package com.example.g8shopadmin.activities.managevoucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.models.Voucher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminCustomManageVoucherListViewAdapter extends ArrayAdapter<Voucher> {

    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();


    public AdminCustomManageVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
        super(context, resource, objects);
        this.vouchers = objects;
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

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        time.setText("Từ " + formatDate.format(vouchers.get(position).getStartedAt()) + " đến " + formatDate.format(vouchers.get(position).getFinishedAt()));
        cost_sale.setText("Số tiền giảm: đ" + vouchers.get(position).getMoneyDeals().toString());
        min_cost.setText("Đơn tối thiểu: đ" + vouchers.get(position).getMinimumCost().toString());
        text_da_su_dung.setText("Đã sử dung: " + vouchers.get(position).getAmount().toString());
        text_so_luong.setText("Còn lại: " + vouchers.get(position).getAmount().toString());

//        image.setImageResource(vouchers.get(position).getImage());

        return v;

    }
}

