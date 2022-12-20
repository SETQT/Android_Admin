package com.example.g8shopadmin.activities;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;

import com.example.g8shopadmin.activities.myproducts.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomPromotionAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> myProducts = new ArrayList<>();
    Context curContext;

    activity_promotions main;

    public CustomPromotionAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        this.myProducts = objects;
        this.curContext = context;
        main = (activity_promotions) getContext();
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
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.custom_promotions_checkbox);
        ImageView img = (ImageView) v.findViewById(R.id.custom_promotions_image_product);

        name.setText(myProducts.get(position).getName());
        cost.setText(myProducts.get(position).getPrice().toString());
        quantity.setText("Số lượng: " + myProducts.get(position).getAmount().toString());
        percent.setText("-"+myProducts.get(position).getSale().toString()+" %");


        Picasso.with(curContext).load(myProducts.get(position).getImage()).into(img);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){

                    main.listEdit.add(myProducts.get(position).getIdDoc().toString());

                } else {
                    main.listEdit.remove(myProducts.get(position).getIdDoc().toString());
                }
                    main.updateAmount(main.listEdit.size());
            }
        });


        return v;

    }
}