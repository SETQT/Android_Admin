package com.example.g8shopadmin.activities.order;

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

public class AdminCustomOrderListViewAdapter extends ArrayAdapter<AdminOrder> {

    ArrayList<AdminOrder> order = new ArrayList<AdminOrder>();


    public AdminCustomOrderListViewAdapter(Context context, int resource, ArrayList<AdminOrder> objects) {
        super(context, resource, objects);
        this.order = objects;
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
        v = inflater.inflate(R.layout.admin_custom_listview_order, null);

        ImageView avatar = (ImageView) v.findViewById(R.id.admin_order_avatar_customer);
        TextView name_customer = (TextView)  v.findViewById(R.id.admin_order_name_customer);
        TextView name_option = (TextView) v.findViewById(R.id.admin_order_option);
        ImageView picture = (ImageView) v.findViewById(R.id.admin_order_picture_product) ;
        TextView name = (TextView) v.findViewById(R.id.admin_order_name_product);
        TextView size = (TextView) v.findViewById(R.id.admin_order_size_product);
        TextView count = (TextView) v.findViewById(R.id.admin_order_count_product);
        TextView old_cost = (TextView) v.findViewById(R.id.admin_order_old_cost_product);
        TextView new_cost = (TextView) v.findViewById(R.id.admin_order_new_cost_product);
        TextView total = (TextView) v.findViewById(R.id.admin_order_total_product);
        Button button_option = (Button) v.findViewById(R.id.admin_order_button_option);
        TextView ma_don_hang = (TextView) v.findViewById(R.id.admin_order_ma_don_hang);


        avatar.setImageResource(order.get(position).getAvatar());
        name_customer.setText(order.get(position).getName_customer());
        name_option.setText(order.get(position).getName_option());
        picture.setImageResource(order.get(position).getPicture());
        name.setText(order.get(position).getName());
        size.setText(order.get(position).getSize());
        count.setText(order.get(position).getCount());
        old_cost.setText(order.get(position).getOld_cost());
        new_cost.setText(order.get(position).getNew_cost());
        total.setText(order.get(position).getTotal());
        button_option.setText(order.get(position).getButton_option());
        ma_don_hang.setText(order.get(position).getMa_don_hang());

        return v;

    }
}
