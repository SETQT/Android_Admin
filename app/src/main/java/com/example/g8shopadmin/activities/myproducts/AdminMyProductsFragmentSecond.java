package com.example.g8shopadmin.activities.myproducts;

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
import androidx.fragment.app.FragmentManager;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_my_products;
import com.example.g8shopadmin.activities.managevoucher.AdminCustomManageVoucherListViewAdapter;
import com.example.g8shopadmin.activities.managevoucher.AdminManageVoucherFragmentSecond;
import com.example.g8shopadmin.models.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class AdminMyProductsFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_my_products main;
    ListView listMyProducts;

    ArrayList<Product> listProducts = new ArrayList<>();

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");

//    AdminMyProductsFragmentFirst first;
//    FragmentManager fm = getFragmentManager();
//    AdminMyProductsFragmentFirst first;
//fragm.otherList();
    public static AdminMyProductsFragmentSecond newInstance(String strArg1) {
        AdminMyProductsFragmentSecond fragment = new AdminMyProductsFragmentSecond();
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
        main = (activity_admin_my_products) getActivity();
//        first = (AdminMyProductsFragmentFirst)fm.findFragmentById(R.id.admin_my_products_fragment_first);

//        first = (AdminMyProductsFragmentFirst)getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_my_products_fragment_second, null);

        listMyProducts = (ListView) layout_second.findViewById(R.id.admin_my_products_listview);


//        AdminCustomManageVoucherListViewAdapter myAdapter = new AdminCustomManageVoucherListViewAdapter(getActivity(), R.layout.admin_custom_listview_manage_voucher, Voucher);
//        listVoucher.setAdapter(myAdapter);

        manage_product_asynctask mv_at = new manage_product_asynctask("0");
        mv_at.execute();

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        manage_product_asynctask mv_at = new manage_product_asynctask(strValue);
        mv_at.execute();

    }


    private class manage_product_asynctask extends AsyncTask<Void, Product, Product> {
        //        Date curDate;
        String state;

        //
        manage_product_asynctask(String state) {
            this.state = state;
        }

        @Override
        protected Product doInBackground(Void... voids) {
            try {
                listProducts.clear();
                productsRef
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                boolean isHave = false;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    product.setIdDoc(document.getId());
//                                    isHave = true;
                                    if (state.equals("0")) {
//                                        Log.d("ASd", "onComplete: " + "con");
                                        if (product.getAmount() - product.getAmountOfSold() != 0) {
                                            isHave = true;
                                            publishProgress(product);

                                        }


                                    } else {
//                                        Log.d("num", "onComplete: "+(product.getAmount() -product.getAmountOfSold()));
                                        if (product.getAmount() - product.getAmountOfSold() == 0) {
                                            Log.d("ASd", "onComplete: " + "het");
                                            isHave = true;

                                            publishProgress(product);

                                        }

                                    }

                                }


                                if (!isHave) publishProgress();

                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "AdminMangerVoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Product... products) {
            super.onProgressUpdate(products);
            if (products.length == 0) {
                listProducts.clear();
            } else {
                listProducts.add(products[0]);

            }


            AdminCustomMyProductsListViewAdapter myAdapter = new AdminCustomMyProductsListViewAdapter(getActivity(), R.layout.admin_custom_listview_my_products, listProducts);
            listMyProducts.setAdapter(myAdapter);
        }
    }


}
