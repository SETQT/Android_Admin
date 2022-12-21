package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.Services.SendNotification;
import com.example.g8shopadmin.databinding.ActivityDashboardBinding;
import com.example.g8shopadmin.models.Order;
import com.example.g8shopadmin.utilities.Constants;
import com.example.g8shopadmin.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class DashboardActivity extends activity_base implements View.OnClickListener {

    private ActivityDashboardBinding binding;
    private PreferenceManager preferenceManager;
    TextView number_confirm, number_wait_delivery, number_delivered, number_rate;
    Integer count_confirm = 0, count_wait_delivery = 0, count_delivered = 0, count_rate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        number_confirm = (TextView) findViewById(R.id.number_confirm);
        number_wait_delivery = (TextView) findViewById(R.id.number_wait_delivery);
        number_delivered = (TextView) findViewById(R.id.number_delivered);
        number_rate = (TextView) findViewById(R.id.number_rate);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference orderRef = db.collection("orders");
        CollectionReference commentsRef = db.collection("comments");

        orderRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Order order = document.toObject(Order.class);
                                if (order.getState() == 1) {
                                    count_confirm = count_confirm + 1;
                                }
                                if (order.getState() == 2) {
                                    count_wait_delivery = count_wait_delivery + 1;
                                }
                                if (order.getState() == 3) {
                                    count_delivered = count_delivered + 1;
                                }
                            }
                            number_confirm.setText(count_confirm.toString());
                            number_wait_delivery.setText(count_wait_delivery.toString());
                            number_delivered.setText(count_delivered.toString());
                        }
                    }
                });

        commentsRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                count_rate = count_rate + 1;
                            }
                            number_rate.setText(count_rate.toString());
                        }
                    }
                });

        setListeners();
        getToken();
    }

    // hàm lắng nghe các sự kiện trong layout
    private void setListeners() {
        binding.iconLogout.setOnClickListener(view -> signOut());
        binding.iconChat.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), activity_dashboard_chat.class));
        });
        binding.productAdmin.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), activity_admin_my_products.class));
        });
        binding.listCustomerAdmin.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), activity_list_customer.class));
        });
        binding.discountAdmin.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), activity_promotions.class));
        });


        binding.adminWaitConfirm.setOnClickListener(this);
        binding.voucherAdmin.setOnClickListener(this);
        binding.adminWaitRate.setOnClickListener(this);
        binding.historyOrder.setOnClickListener(this);
        binding.revenueAdmin.setOnClickListener(this);
        binding.adminWaitConfirm.setOnClickListener(this);
        binding.adminWaitDelivery.setOnClickListener(this);
        binding.adminDelivered.setOnClickListener(this);
        binding.notifyAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.notifyAdmin.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_notify.class);
            startActivity(moveActivity);
        }

        if (view.getId() == binding.adminWaitConfirm.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_order.class);
            moveActivity.putExtra("state", "1");
            startActivity(moveActivity);
        }
        if (view.getId() == binding.adminWaitDelivery.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_order.class);
            moveActivity.putExtra("state", "2");
            startActivity(moveActivity);
        }
        if (view.getId() == binding.adminDelivered.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_order.class);
            moveActivity.putExtra("state", "3");
            startActivity(moveActivity);
        }


        if (view.getId() == binding.voucherAdmin.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_manage_voucher.class);
            startActivity(intent);
        }
        if (view.getId() == binding.adminWaitRate.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_evaluate.class);
            startActivity(intent);
        }
        if (view.getId() == binding.historyOrder.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_order.class);
            startActivity(intent);
        }
        if (view.getId() == binding.revenueAdmin.getId()) {
            Intent intent = new Intent(getApplicationContext(), activity_admin_revenue.class);
            startActivity(intent);
        }
    }

    // Hàm show các thông báo
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                db.collection(Constants.KEY_COLLECTION_ADMIN).document(
                        preferenceManager.getString(Constants.KEY_ADMIN_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                //.addOnSuccessListener(unused -> showToast("Token updated"))
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }

    private void signOut() {
        showToast("Signing out ...");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                db.collection(Constants.KEY_COLLECTION_ADMIN).document(
                        preferenceManager.getString(Constants.KEY_ADMIN_ID)
                );
        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnCompleteListener(unused -> {
                    preferenceManager.clear();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Đăng xuất không thành công"));
    }
}