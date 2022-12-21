package com.example.g8shopadmin.activities.promotion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.g8shopadmin.R;

import java.util.ArrayList;

public class CustomRecylerviewCreatePromotionAdapter extends RecyclerView.Adapter<CustomRecylerviewCreatePromotionAdapter.MyHolder>  {

        ArrayList<String> data;

        public CustomRecylerviewCreatePromotionAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_create_promotions, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tvTitle.setImageResource(Integer.parseInt(data.get(position)));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            ImageView tvTitle;

            public MyHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.picture_create_promotions);
            }
        }

    }

