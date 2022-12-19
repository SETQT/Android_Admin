package com.example.g8shopadmin.activities.order;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminOrderFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_order main;
    ListView listMyOrder;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ordersRef = db.collection("orders");

    String stateMyOrder;


    public static AdminOrderFragmentSecond newInstance(String strArg1) {
        AdminOrderFragmentSecond fragment = new AdminOrderFragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (activity_admin_order) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_order_fragment_second, null);

        listMyOrder = (ListView) layout_second.findViewById(R.id.admin_order_listview);

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        if(stateMyOrder != null) {
            order_asynctask o_at1 = new order_asynctask(Integer.parseInt(stateMyOrder));
            o_at1.execute();
        }
        else {
            order_asynctask o_at = new order_asynctask(1);
            o_at.execute();
        }
        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        stateMyOrder = strValue;
        order_asynctask o_at = new order_asynctask(Integer.parseInt(strValue));
        o_at.execute();


    }

    class order_asynctask extends AsyncTask<Void, Order, Order> {
        ArrayList<Order> listOrder = new ArrayList<>();
        Integer state;

        public order_asynctask() {
        }

        public order_asynctask(Integer state) {
            this.state = state;
        }

        @Override
        protected Order doInBackground(Void... voids) {
            try {
                listOrder.clear();

                ordersRef
                        .whereEqualTo("state", state)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Boolean isHave = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Order order = document.toObject(Order.class);
                                        order.setIdDoc(document.getId());
                                        isHave = true;
                                        publishProgress(order);
                                    }

                                    if (!isHave) {
                                        publishProgress();
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "VoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Order... orders) {
            // Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(orders);

            if (orders.length == 0) {
                listOrder.clear();
            } else {
                for (int i = 0; i < orders.length; i++) {
                    listOrder.add(orders[0]);
                }
            }

            try {
                AdminCustomOrderListViewAdapter myAdapter = new AdminCustomOrderListViewAdapter(getActivity(), R.layout.admin_custom_listview_order, listOrder, state);
                listMyOrder.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "MyorderFragmentSecond: ", error);
                return;
            }
        }
    }


}
