package com.example.g8shopadmin.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_list_customer;
import com.example.g8shopadmin.activities.myproducts.AdminCustomMyProductsListViewAdapter;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListCustomerFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_list_customer main;
    ListView list_customer_listview;


    ArrayList<User> CustomerArray = new ArrayList<User>();

    ArrayList<String> username = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference customersRef = db.collection("users");
    public static ListCustomerFragmentSecond newInstance(String strArg1) {
        ListCustomerFragmentSecond fragment = new ListCustomerFragmentSecond();
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

        main = (activity_list_customer) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.custom_list_customer_fragment_second, null);

        list_customer_listview = (ListView) layout_second.findViewById(R.id.list_customer_listview);
        manage_users_asynctask mv_at = new manage_users_asynctask();
        mv_at.execute();


        try {
            Bundle arguments = getArguments();
        } catch (Exception e) {
            Log.e("RED BUNDLE ERROR – ", "" + e.getMessage());
        }

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        manage_users_asynctask mv_at = new manage_users_asynctask(strValue);
        mv_at.execute();
    }
    class manage_users_asynctask extends AsyncTask<Void, User, User> {
        //        Date curDate;
        String dataSearch;
        public manage_users_asynctask(String dataSearch) {
            this.dataSearch = dataSearch;
        }
        public manage_users_asynctask() {
        }
        @Override
        protected User doInBackground(Void... voids) {
            try {
                if(dataSearch != null&& !(dataSearch.compareTo("")==0)) {
                    CustomerArray.clear();
                    customersRef
                            .whereEqualTo("username", dataSearch)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    boolean isHave = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User customer = document.toObject(User.class);
                                        customer.setIdDoc(document.getId());
                                        isHave = true;
                                        publishProgress(customer);
                                    }


                                    if (!isHave) publishProgress();

                                }
                            });
                }
                else{
                    CustomerArray.clear();
                    customersRef
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    boolean isHave = false;

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User customer = document.toObject(User.class);
                                        customer.setIdDoc(document.getId());
                                        isHave = true;
                                        publishProgress(customer);
                                    }


                                    if (!isHave) publishProgress();

                                }
                            });
                }
            } catch (Exception error) {
                Log.e("ERROR", "AdminMangerVoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(User... Customer) {
            super.onProgressUpdate(Customer);
            if (Customer.length == 0) {
                CustomerArray.clear();
            } else {
                CustomerArray.add(Customer[0]);
//                Product product= products[0];
//                MyProducts.add(new AdminMyProducts(product., image.get(i), name.get(i), cost.get(i), text_kho_hang.get(i), text_da_ban.get(i), text_thich.get(i), text_luot_xem.get(i)));

            }


            AdminCustomListCustomerAdapter myAdapter = new AdminCustomListCustomerAdapter(getActivity(), R.layout.custom_listview_list_customer, CustomerArray);
            list_customer_listview.setAdapter(myAdapter);
        }
    }
}
