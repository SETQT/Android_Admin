package com.example.g8shopadmin.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_evaluate;
import com.example.g8shopadmin.activities.activity_admin_order;
import com.example.g8shopadmin.models.Notification;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminCustomNotifyListViewAdapter extends ArrayAdapter<Notification> {

    ArrayList<Notification> notifies = new ArrayList<>();
    Context curContext;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notifyRef = db.collection("notifications");


    public AdminCustomNotifyListViewAdapter(Context context, int resource, ArrayList<Notification> objects) {
        super(context, resource, objects);
        this.notifies = objects;
        this.curContext = context;
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
        v = inflater.inflate(R.layout.admin_custom_notify_listview, null);

        TextView textTitle = (TextView) v.findViewById(R.id.custom_notify_itemTitle);
        ImageView imageView = (ImageView) v.findViewById(R.id.custom_notify_icon);
        TextView textContent = (TextView) v.findViewById(R.id.custom_notify_content);
        TextView textDate = (TextView) v.findViewById(R.id.custom_notify_date);
        View custom_notify_listview_bg_notify = (View) v.findViewById(R.id.custom_notify_listview_bg_notify);

        if (notifies.get(position).getStatus() == 1) {
            custom_notify_listview_bg_notify.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c7c7c7")));
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        textTitle.setText(notifies.get(position).getTitle());
        Picasso.with(curContext).load(notifies.get(position).getImage()).into(imageView);
        textDate.setText(simpleDateFormat.format(notifies.get(position).getDate()));
        textContent.setText(notifies.get(position).getContent());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notifies.get(position).getStatus() == 0) {
                    notifyRef.document(notifies.get(position).getIdDoc()).update("status", 1);

                    notifies.get(position).setStatus(1);

                    notifyDataSetChanged();
                }

                Intent moveActivity;

                switch (notifies.get(position).getType()) {
                    case "order":
                        // chuyển sang giao diện đơn hàng của tôi
                        moveActivity = new Intent(curContext, activity_admin_order.class);
                        moveActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        curContext.startActivity(moveActivity);
                        break;
                    case "evaluate":
                        // chuyển sang giao diện my voucher
                        moveActivity = new Intent(curContext, activity_admin_evaluate.class);
                        moveActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        curContext.startActivity(moveActivity);
                        break;
                    default:
                        break;
                }
            }
        });

        return v;
    }
}
