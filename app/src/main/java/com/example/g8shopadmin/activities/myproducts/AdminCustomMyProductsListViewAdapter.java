package com.example.g8shopadmin.activities.myproducts;

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

public class AdminCustomMyProductsListViewAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> myProducts = new ArrayList<Product>();
    Context curContext;

    public AdminCustomMyProductsListViewAdapter(Context context, int resource, ArrayList<Product> objects) {
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
        v = inflater.inflate(R.layout.admin_custom_listview_my_products, null);

        TextView name = (TextView) v.findViewById(R.id.admin_custom_listview_my_products_name);
        TextView cost = (TextView) v.findViewById(R.id.admin_custom_listview_my_products_cost);
        TextView text_kho_hang = (TextView) v.findViewById(R.id.text_kho_hang);
        TextView text_da_ban = (TextView) v.findViewById(R.id.text_da_ban);
        TextView text_thich = (TextView) v.findViewById(R.id.text_thich);
        TextView text_luot_xem = (TextView) v.findViewById(R.id.text_luot_xem);
        ImageView img = (ImageView) v.findViewById(R.id.admin_custom_listview_my_products_picture) ;

        name.setText(myProducts.get(position).getName());
        cost.setText(myProducts.get(position).getPrice().toString());
        text_kho_hang.setText(myProducts.get(position).getAmount().toString());
        text_da_ban.setText(myProducts.get(position).getAmountOfSold().toString());
//        text_thich.setText(myProducts.get(position).getText_thich());
        text_thich.setText("0###");
//        text_luot_xem.setText(myProducts.get(position).getText_luot_xem());
        text_luot_xem.setText("0###");

//        img.setImageResource(myProducts.get(position).getPicture());
        Picasso.with(curContext).load(myProducts.get(position).getImage()).into(img);

        return v;

    }
}
