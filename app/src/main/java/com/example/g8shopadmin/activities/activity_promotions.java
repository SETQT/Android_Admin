package com.example.g8shopadmin.activities;


import com.example.g8shopadmin.databinding.ActivityPromotionsBinding;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import com.example.g8shopadmin.R;


public class activity_promotions extends Activity {
    private ActivityPromotionsBinding binding;
    ListView list_product_promotions;
    ArrayList<Product_Promotions> product_array = new ArrayList<>();

    String[] percent = {"50%", "50%", "50%", "50%", "50%", "50%"};
    String[] product = {"Áo thun cực chất", "Áo MU", "Giày độn", "Áo khoác", "Mũ lưỡi trai", "Áo len"};
    String[] cost = {"đ100.000", "đ100.000", "đ100.000", "đ100.000", "đ100.000", "đ100.000"};
    String[] quantity = {"5", "4", "10", "12","1","7"};
    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,
            R.drawable.mono1, R.drawable.mono1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPromotionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list_product_promotions= (ListView) findViewById(R.id.list_view_promotions);

        for (int i = 0; i < 6; i++) {
            product_array.add(new Product_Promotions(i, product[i], cost[i], quantity[i], percent[i], image[i]));
        }
        CustomPromotionAdapter myAdapter = new CustomPromotionAdapter(this, R.layout.custom_listview_promotions, product_array);
        list_product_promotions.setAdapter(myAdapter);


    }
}