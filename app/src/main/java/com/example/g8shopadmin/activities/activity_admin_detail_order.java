package com.example.g8shopadmin.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.g8shopadmin.R;

import java.util.ArrayList;

public class activity_admin_detail_order extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listOrder;
    ArrayList<Order> ListOrderArray =new ArrayList<Order>();

    String[] name = {"Áo khoác Mono cực chất lượng", "Áo Liver giúp ra hang đầu mùa giải", "Áo anh 7 dự bị", "Giày độn", "Áo khoác", "Mũ lưỡi trai"};
    String[] old_cost = {"đ500.000","đ400.000","đ300.000","đ700.000","đ100.000","đ200.000" };
    String[] new_cost = {"đ300.000","đ200.000","đ200.000","đ500.000","đ80.000","đ150.000" };
    String[] number = {"02","01","02","01","01","01" };
    String[] size = {"S","M","L","XL","XXL","XXXL" };
    String[] color = {"Đen","Đỏ","Tím","Vàng","Xanh","Hồng" };
    String[] cost_final = {"đ500.000","đ500.000","đ500.000","đ500.000","đ500.000","đ500.000" };




    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,R.drawable.mono1,R.drawable.mono1  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_order);


        listOrder = (ListView) findViewById(R.id.listview_detail_order);

        for (int i=0;i<6;i++){
            ListOrderArray.add(new Order(i, name[i],old_cost[i], new_cost[i], number[i],size[i],color[i],cost_final[i],image[i]));
        }
        CustomMyListViewPaymentAdapter myAdapter = new CustomMyListViewPaymentAdapter(this,R.layout.custom_listview_payment, ListOrderArray);
        listOrder.setAdapter(myAdapter);
        setListViewHeightBasedOnChildren(listOrder);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}