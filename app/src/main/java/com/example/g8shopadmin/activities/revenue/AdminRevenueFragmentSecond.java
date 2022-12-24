package com.example.g8shopadmin.activities.revenue;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_revenue;
import com.example.g8shopadmin.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminRevenueFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_revenue main;
    ListView admin_revenue_listview;
    TextView total_cost;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ordersRef = db.collection("orders");

    public static AdminRevenueFragmentSecond newInstance(String strArg1) {
        AdminRevenueFragmentSecond fragment = new AdminRevenueFragmentSecond();
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
        main = (activity_admin_revenue) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_revenue_fragment_second, null);

        admin_revenue_listview = (ListView) layout_second.findViewById(R.id.admin_revenue_listview);
        total_cost = (TextView)  layout_second.findViewById(R.id.total_cost);


        AdminRevenueFragmentSecond.order_asynctask o_at = new AdminRevenueFragmentSecond.order_asynctask();
        o_at.execute();

        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        String[] parts = strValue.split(",");
        String month_send = parts[0];
        String year_send = parts[1];
        AdminRevenueFragmentSecond.order_asynctask o_at = new AdminRevenueFragmentSecond.order_asynctask(month_send, year_send);
        o_at.execute();

    }

    class order_asynctask extends AsyncTask<Void, Order, Order> {
        ArrayList<Order> listOrder = new ArrayList<>();
        String month, year;

        public order_asynctask() {
        }

        public order_asynctask(String month, String year) {
            this.month = month;
            this.year = year;
        }

        @Override
        protected Order doInBackground(Void... voids) {
            try {
                ordersRef
                        .whereEqualTo("state", 3)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Integer total = 0;
                                if (task.isSuccessful()) {
                                    Boolean isHave = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Order order = document.toObject(Order.class);
                                        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                                        String date = formatDate.format(order.getCreatedAt()).toString();
                                        String[] splits = date.split("-");
                                        String month_val = splits[1]; // 004
                                        String year_val = splits[2];
                                        if (month_val.equals(month) & year_val.equals(year)){
                                            total = total + order.getFinalTotalMoney();
                                            order.setIdDoc(document.getId());
                                            isHave = true;
                                            publishProgress(order);
                                        }
                                        total_cost.setText("đ"+ total.toString());

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
            SortArrayList(listOrder);
            try {
                AdminCustomRevenueListViewAdapter myAdapter = new AdminCustomRevenueListViewAdapter(getActivity(), R.layout.admin_custom_listview_revenue, listOrder);
                admin_revenue_listview.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "MyorderFragmentSecond: ", error);
                return;
            }
        }
    }

    class sortCompare implements Comparator<Order> {
        public int compare(Order s1, Order s2) {
            return s2.getCreatedAt().compareTo(s1.getCreatedAt());
        }
    }

    public void SortArrayList(ArrayList<Order> order){
        Collections.sort(order, new sortCompare());
    }
}
