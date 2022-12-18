package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.order.Myorder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMyListViewPaymentAdapter extends ArrayAdapter<Myorder> {

    ArrayList<Myorder> order = new ArrayList<Myorder>();


    public CustomMyListViewPaymentAdapter(Context context, int resource, ArrayList<Myorder> objects) {
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
        v = inflater.inflate(R.layout.custom_listview_payment, null);

        TextView name = (TextView) v.findViewById(R.id.custom_name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.custom_old_cost_product_payment);
        TextView new_cost = (TextView) v.findViewById(R.id.custom_new_cost_product_payment);
        TextView count = (TextView) v.findViewById(R.id.soluong_payment);
        TextView size_color = (TextView) v.findViewById(R.id.size_color_product_payment);
        TextView cost_final = (TextView) v.findViewById(R.id.total_cost_product_payment);
        ImageView img = (ImageView) v.findViewById(R.id.custom_picture_payment) ;

        name.setText(order.get(position).getName());
        old_cost.setText("đ" +order.get(position).getOldCost().toString());
        new_cost.setText("đ" + order.get(position).getNewCost().toString());
        count.setText("Số lượng: " + order.get(position).getCount().toString());
        size_color.setText("Size: " + order.get(position).getSize()+", "+order.get(position).getColor());
        cost_final.setText("đ" +order.get(position).getTotal().toString());
        Picasso.with(getContext()).load(order.get(position).getImage()).into(img);
        return v;
    }
}