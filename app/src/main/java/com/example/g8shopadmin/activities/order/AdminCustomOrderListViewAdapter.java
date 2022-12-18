package com.example.g8shopadmin.activities.order;

import static android.app.PendingIntent.getActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_detail_order;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminCustomOrderListViewAdapter extends ArrayAdapter<Order> {

    Context curContext;
    ArrayList<Order> orders = new ArrayList<>();
    Integer state;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef = db.collection("users");
    CollectionReference orderRef = db.collection("orders");

    public AdminCustomOrderListViewAdapter(Context context, int resource, ArrayList<Order> objects, Integer state) {
        super(context, resource, objects);
        this.orders = objects;
        this.curContext = context;
        this.state = state;
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
        v = inflater.inflate(R.layout.admin_custom_listview_order, null);

        ImageView avatar = (ImageView) v.findViewById(R.id.admin_order_avatar_customer);
        TextView name_customer = (TextView) v.findViewById(R.id.admin_order_name_customer);
        TextView date = (TextView) v.findViewById(R.id.admin_order_date);
        TextView total = (TextView) v.findViewById(R.id.admin_order_total_product);
        Button button_option = (Button) v.findViewById(R.id.admin_order_button_option);
        TextView ma_don_hang = (TextView) v.findViewById(R.id.admin_order_ma_don_hang);
        TextView total_products = (TextView) v.findViewById(R.id.admin_order_total_products);
        Button view_all_products = (Button) v.findViewById(R.id.admin_order_view_all_products);
        GetAvatarUser(orders.get(position).getOwnOrder(), avatar);
        name_customer.setText(orders.get(position).getOwnOrder());
        ma_don_hang.setText("#" + orders.get(position).getCode());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        date.setText("Ngày đặt: " + formatDate.format(orders.get(position).getCreatedAt()).toString());
        total.setText("đ" + orders.get(position).getFinalTotalMoney());
        total_products.setText("Số sản phẩm: " + orders.get(position).getArrayOrder().size());

        if (orders.get(position).getState() == 1) {
            button_option.setText("Xác nhận");
            button_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConfirmOrder(position);
                }
            });
        }
        if (orders.get(position).getState() == 2) {
            button_option.setText("Tình trạng giao");
        }
        if (orders.get(position).getState() == 3) {
            button_option.setText("Xem đánh giá");
        }

        view_all_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAllProduct(orders.get(position).getIdDoc(), orders.get(position).getOwnOrder());
            }
        });

        return v;
}

    void ConfirmOrder(Integer position) {
        orderRef.document(orders.get(position).getIdDoc()).update("state", 2);
        Toast.makeText(curContext, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();

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

    void ShowAllProduct(String IdDoc, String username){
        Intent moveActivity = new Intent(curContext, activity_admin_detail_order.class);
        moveActivity.putExtra("IdDoc", IdDoc);
        moveActivity.putExtra("username", username);
        curContext.startActivity(moveActivity);
    }


}
