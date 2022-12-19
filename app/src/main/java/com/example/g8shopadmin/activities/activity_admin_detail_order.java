package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.order.Myorder;
import com.example.g8shopadmin.activities.order.Order;
import com.example.g8shopadmin.models.User;
import com.example.g8shopadmin.models.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class activity_admin_detail_order extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listOrder;
    ArrayList<Myorder> myorders = new ArrayList<Myorder>();
    String IdDoc;
    String username;
    TextView fullName, phone, address, methodPayment, user_name, transportFee, finalTotalMoney, code, date, total_order, cost_voucher;
    ImageView avatar;
    Integer total = 0;
    String nameVoucher;
    Order curOder;
    View icon_back;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef = db.collection("users");
    CollectionReference orderRef = db.collection("orders");
    CollectionReference voucherRef = db.collection("vouchers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_order);

        listOrder = (ListView) findViewById(R.id.listview_detail_order);

        fullName = (TextView) findViewById(R.id.name_user_detail_order);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address_detail_order);
        methodPayment = (TextView) findViewById(R.id.value_method_payment_detail_order);
        user_name = (TextView) findViewById(R.id.username_customer);
        finalTotalMoney = (TextView) findViewById(R.id.value_total_money_payment_detail_order);
        transportFee = (TextView) findViewById(R.id.value_cost_tranfer_detail_order);
        code = (TextView) findViewById(R.id.value_code_order);
        date = (TextView) findViewById(R.id.value_time_order);
        total_order = (TextView) findViewById(R.id.value_total_cost_payment_detail_order);
        cost_voucher = (TextView) findViewById(R.id.value_total_voucher_discount_detail_order);
        avatar = (ImageView) findViewById(R.id.picture_account_customer);

        icon_back = (View) findViewById(R.id.icon_back);

        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_admin_order.class);
                startActivity(moveActivity);
            }
        });

        Intent intent = getIntent();
        curOder = (Order) intent.getExtras().getSerializable("order");
        username = intent.getStringExtra("username");

        code.setText(curOder.getIdDoc().toUpperCase());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(formatDate.format(curOder.getCreatedAt()).toString());
        methodPayment.setText(curOder.getPaymentMethods());
        transportFee.setText("đ" + curOder.getTransportFee().toString());
        finalTotalMoney.setText("đ" + curOder.getFinalTotalMoney().toString());
        myorders = curOder.getArrayOrder();
        for (int i = 0; i < myorders.size(); i++) {
            total = total + myorders.get(i).getTotal();
        }
        total_order.setText("đ" + total.toString());
        nameVoucher = curOder.getVoucher();
        if (nameVoucher != "") {
            voucherRef
                    .whereEqualTo("id", nameVoucher)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Voucher voucher = document.toObject(Voucher.class);
                                cost_voucher.setText("-đ" + voucher.getMoneyDeals().toString());
                            }
                        }
                    });
        } else {
            cost_voucher.setText("đ0");
        }

        CustomMyListViewPaymentAdapter myAdapter = new CustomMyListViewPaymentAdapter(getApplicationContext(), R.id.listview_detail_order, myorders);
        listOrder.setAdapter(myAdapter);
        setListViewHeightBasedOnChildren(listOrder);

        userRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            Picasso.with(getApplicationContext()).load(user.getImage()).into(avatar);
                            address.setText("Địa chỉ: " + user.getAddress());
                            fullName.setText(user.getFullname());
                            phone.setText(user.getPhone());
                            user_name.setText(user.getUsername());
                        }
                    }
                });
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