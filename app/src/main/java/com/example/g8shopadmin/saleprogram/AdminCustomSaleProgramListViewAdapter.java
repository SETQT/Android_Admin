package com.example.g8shopadmin.saleprogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;

import java.util.ArrayList;

public class AdminCustomSaleProgramListViewAdapter extends ArrayAdapter<AdminSaleProgram> {

    ArrayList<AdminSaleProgram> sale = new ArrayList<AdminSaleProgram>();


    public AdminCustomSaleProgramListViewAdapter(Context context, int resource, ArrayList<AdminSaleProgram> objects) {
        super(context, resource, objects);
        this.sale = objects;
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
        v = inflater.inflate(R.layout.admin_custom_listview_sale_program, null);

        ImageView image = (ImageView) v.findViewById(R.id.logo_sale);
        TextView title = (TextView)  v.findViewById(R.id.admin_custom_listview_sale_program_title);
        TextView time = (TextView)  v.findViewById(R.id.admin_custom_listview_sale_program_time);

        time.setText(sale.get(position).getTime());
        title.setText(sale.get(position).getTitle());
        image.setImageResource(sale.get(position).getImage());

        return v;

    }
}

