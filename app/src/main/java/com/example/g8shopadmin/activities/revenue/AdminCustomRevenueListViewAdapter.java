package com.example.g8shopadmin.activities.revenue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_notify;
import com.example.g8shopadmin.models.Notification;
import com.example.g8shopadmin.models.Order;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminCustomRevenueListViewAdapter extends ArrayAdapter<Order> {
    Context curContext;
    ArrayList<Order> orders = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef = db.collection("users");

    public AdminCustomRevenueListViewAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        this.orders = objects;
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
        v = inflater.inflate(R.layout.admin_custom_listview_revenue, null);

        ImageView avatar = (ImageView) v.findViewById(R.id.avatar);
        TextView code = (TextView) v.findViewById(R.id.code);
        TextView username = (TextView) v.findViewById(R.id.username);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView count = (TextView) v.findViewById(R.id.count_product);
        TextView total = (TextView) v.findViewById(R.id.total);
        GetAvatarUser(orders.get(position).getOwnOrder(), avatar);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        date.setText("Ngày mua: " + formatDate.format(orders.get(position).getCreatedAt()).toString());
        total.setText("đ" + orders.get(position).getFinalTotalMoney());
        code.setText("#" + orders.get(position).getIdDoc().toUpperCase());
        username.setText(orders.get(position).getOwnOrder());
        count.setText(orders.get(position).getArrayOrder().size() + " sản phẩm");

        return v;
    }


    void GetAvatarUser(String username, ImageView avatar) {
        userRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            Picasso.with(curContext).load(user.getImage()).into(avatar);
                        }
                    }
                });
    }


}
