package com.example.g8shopadmin.activities.myproducts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_create_product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdminCustomMyProductsListViewAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> myProducts = new ArrayList<Product>();
    Context curContext;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prodsRef = db.collection("products");
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
        cost.setText("đ" + myProducts.get(position).getPrice().toString());
        text_kho_hang.setText("Kho hàng: "+myProducts.get(position).getAmount().toString());
        text_da_ban.setText("Đã bán: "+myProducts.get(position).getAmountOfSold().toString());

        text_thich.setText("Thích: 0");

        text_luot_xem.setText("Lượt xem: 0");


        Picasso.with(curContext).load(myProducts.get(position).getImage()).into(img);

        Button update = (Button)  v.findViewById(R.id.admin_custom_listview_my_products_button_an);
        Button delete = (Button)  v.findViewById(R.id.admin_custom_listview_my_products_button_xoa);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(curContext)
                        .setMessage("Bạn có chức muốn xóa sản phẩm này chứ?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                prodsRef.document(myProducts.get(position).getIdDoc()).delete();
                                Toast.makeText(curContext, "Đã xóa sản phẩm thành công!", Toast.LENGTH_LONG).show();
                                myProducts.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }

        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getContext(), activity_admin_create_product.class);
//                Log.d("data", "onClick: "+myProducts.get(position).getName().toString());
                mIntent.putExtra("update",myProducts.get(position).getIdDoc().toString());
                mIntent.putExtra("name",myProducts.get(position).getName().toString());
                mIntent.putExtra("image",myProducts.get(position).getImage().toString());
                mIntent.putExtra("price",myProducts.get(position).getPrice());
                mIntent.putExtra("amount",myProducts.get(position).getAmount());
                mIntent.putExtra("amountOfSold",myProducts.get(position).getAmountOfSold());
                mIntent.putExtra("category",myProducts.get(position).getCategory().toString());
                mIntent.putExtra("sale",myProducts.get(position).getSale());
                mIntent.putExtra("descript",myProducts.get(position).getDescription().toString());
                mIntent.putExtra("color",myProducts.get(position).getTypeColor());
                mIntent.putExtra("size",myProducts.get(position).getTypeSize());
                curContext.startActivity(mIntent);
            }

        });
        return v;

    }
}
