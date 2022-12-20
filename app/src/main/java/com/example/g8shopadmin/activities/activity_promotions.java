package com.example.g8shopadmin.activities;


import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.activities.promotion.CustomPromotionAdapter;
import com.example.g8shopadmin.databinding.ActivityPromotionsBinding;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import com.example.g8shopadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class activity_promotions extends Activity {
    private ActivityPromotionsBinding binding;
    ListView list_product_promotions;

    ArrayList<Product> listProducts = new ArrayList<>();

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");

    public ArrayList<String> listEdit = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPromotionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list_product_promotions = (ListView) findViewById(R.id.list_view_promotions);


       manage_product_asynctask mv_at = new manage_product_asynctask();
        mv_at.execute();

        binding.btnAdminAddPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("TAG", "edit "+listEdit.toString());
                if (listEdit.size()==0){
                    Toast.makeText(getApplicationContext(), "Chưa chọn sản phẩm !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent =new Intent(getApplicationContext(), activity_admin_enter_promotions.class);
                intent.putExtra("listEdit",listEdit);
                startActivity(intent);
            }
        });
        binding.iconBackPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private class manage_product_asynctask extends AsyncTask<Void, Product, Product> {


        manage_product_asynctask() {

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
                                    publishProgress(product);
                                    isHave = true;


                                }

                                if (!isHave) publishProgress();
                            }


                        });
            } catch (
                    Exception error) {
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
//                Product product= products[0];
//                MyProducts.add(new AdminMyProducts(product., image.get(i), name.get(i), cost.get(i), text_kho_hang.get(i), text_da_ban.get(i), text_thich.get(i), text_luot_xem.get(i)));

            }


            CustomPromotionAdapter myAdapter = new CustomPromotionAdapter(activity_promotions.this, R.layout.custom_listview_promotions, listProducts);
            list_product_promotions.setAdapter(myAdapter);
        }
    }
    public void updateAmount(Integer num){
        binding.quantitySelectedAdminPromotions.setText(num.toString());
    }
}