package com.example.g8shopadmin.activities;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomPromotionAdapter extends ArrayAdapter<Product_Promotions> {

    ArrayList<Product_Promotions> myProducts = new ArrayList<Product_Promotions>();
    Context curContext;


    public CustomPromotionAdapter(Context context, int resource, ArrayList<Product_Promotions> objects) {
        super(context, resource, objects);
        this.myProducts = objects;
        this.curContext=context;
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
        v = inflater.inflate(R.layout.custom_listview_promotions, null);

        TextView name = (TextView) v.findViewById(R.id.custom_promotions_name_product);
        TextView cost = (TextView) v.findViewById(R.id.custom_promotions_cost_product);
        TextView quantity = (TextView) v.findViewById(R.id.custom_promotions_quantity_product);
        TextView percent = (TextView) v.findViewById(R.id.custom_promotions_percent_decrease);

        ImageView img = (ImageView) v.findViewById(R.id.custom_promotions_image_product) ;

        name.setText(myProducts.get(position).getName());
        cost.setText(myProducts.get(position).getCost().toString());
        quantity.setText("Số lượng: "+myProducts.get(position).getQuantity().toString());
        percent.setText(myProducts.get(position).getPercent().toString());


        Picasso.with(curContext).load(myProducts.get(position).getImage()).into(img);

        return v;

    }
}