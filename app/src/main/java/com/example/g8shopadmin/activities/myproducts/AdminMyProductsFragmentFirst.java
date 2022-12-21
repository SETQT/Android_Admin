package com.example.g8shopadmin.activities.myproducts;

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

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_my_products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminMyProductsFragmentFirst extends Fragment {
    // this fragment shows a ListView
    activity_admin_my_products main;
    TextView admin_custom_my_products_option_con_hang, admin_custom_my_products_option_het_hang;


    ListView listMyProducts;
    ArrayList<Product> listProducts = new ArrayList<>();
    ArrayList<Product> listProducts_soldout = new ArrayList<>();
    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static AdminMyProductsFragmentFirst newInstance(String strArg) {
        AdminMyProductsFragmentFirst fragment = new AdminMyProductsFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContent(String text) {
        admin_custom_my_products_option_con_hang.setText("ok");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_admin_my_products) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.admin_custom_my_products_fragment_first, null);

        admin_custom_my_products_option_con_hang = (TextView) layout_first.findViewById(R.id.admin_custom_my_products_option_con_hang);
        admin_custom_my_products_option_het_hang = (TextView) layout_first.findViewById(R.id.admin_custom_my_products_option_het_hang);

        admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        admin_custom_my_products_option_con_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                admin_custom_my_products_option_het_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                String dataSend = "0";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        admin_custom_my_products_option_het_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_custom_my_products_option_con_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                admin_custom_my_products_option_het_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                String dataSend = "1";
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        manage_product_asynctask mv_at = new manage_product_asynctask("0");
        mv_at.execute();
        listProducts.clear();

        manage_product_asynctask mv_at2 = new manage_product_asynctask("1");
        mv_at2.execute();

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);

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
                listProducts_soldout.clear();
            } else {
                if(state=="0") {
                    listProducts.add(products[0]);
                }else
                    listProducts_soldout.add(products[0]);

            }
            if (state == "0") {
                admin_custom_my_products_option_con_hang.setText("Còn hàng(" + listProducts.size() + ")");
//                listProducts.clear();
            }
            else {
                admin_custom_my_products_option_het_hang.setText("Hết hàng(" + listProducts_soldout.size() + ")");
            }
//            AdminCustomMyProductsListViewAdapter myAdapter = new AdminCustomMyProductsListViewAdapter(getActivity(), R.layout.admin_custom_listview_my_products, listProducts);
//            listMyProducts.setAdapter(myAdapter);
        }
    }
}// class
