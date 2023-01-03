package com.example.g8shopadmin.activities.myproducts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_my_products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

        productsRef
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    setAmountProDuctForTextView(admin_custom_my_products_option_con_hang, "0");
                                    setAmountProDuctForTextView(admin_custom_my_products_option_het_hang, "1");
                                    break;
                                case REMOVED:
                                    setAmountProDuctForTextView(admin_custom_my_products_option_con_hang, "0");
                                    setAmountProDuctForTextView(admin_custom_my_products_option_het_hang, "1");
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }

    public void setAmountProDuctForTextView(TextView tv, String state) {
        productsRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Integer count = 0;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            product.setIdDoc(document.getId());
                            if (state.equals("0")) {
                                if (product.getAmount() - product.getAmountOfSold() != 0) {
                                    count++;
                                }
                            } else {
                                if (product.getAmount() - product.getAmountOfSold() == 0) {
                                    count++;
                                }
                            }
                        }

                        if (state == "0") {
                            tv.setText("Còn hàng(" + count.toString() + ")");
                        } else {
                            tv.setText("Hết hàng(" + count.toString() + ")");
                        }
                    }
                });
    }
}// class
