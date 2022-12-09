package com.example.g8shopadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class activity_admin_create_promotions extends Activity implements AdapterView.OnItemSelectedListener{
    private ArrayList<Integer> listType_size = new ArrayList<Integer>();
    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    CustomRecylerviewCreatePromotionAdapter myPromotionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_promotions);

        rv = findViewById(R.id.recyclerView_product_create_promotions);

        //Setting the data source
        dataSource = new ArrayList<>();
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));
        dataSource.add(String.valueOf(R.drawable.mono1));




        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myPromotionAdapter = new CustomRecylerviewCreatePromotionAdapter(dataSource);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myPromotionAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}