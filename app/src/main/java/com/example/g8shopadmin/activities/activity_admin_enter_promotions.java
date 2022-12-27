package com.example.g8shopadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.databinding.ActivityAdminCreateProductBinding;
import com.example.g8shopadmin.databinding.ActivityAdminEnterPromotionsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;

public class activity_admin_enter_promotions extends Activity {

    private ActivityAdminEnterPromotionsBinding binding;
    private ArrayList<String> listEdit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prodsRef = db.collection("products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityAdminEnterPromotionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        try {
            listEdit = getIntent().getExtras().getStringArrayList("listEdit");
            binding.adminNumberProductPromotions.setText(listEdit.size()+" sản phẩm đã được chọn");

        } catch (Exception e) {

        }
        binding.icBackAdminEnterPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_promotions.class);
                startActivity(intent);
            }
        });

        binding.btnAdminEnterPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.valueDiscountRatePromotions.getText().toString();
                if (text.length()==0){

                    Toast.makeText(getApplicationContext(), "Chưa nhập mức giảm giá !", Toast.LENGTH_SHORT).show();
                    return;
                }

                    Integer rate = checkValidRate(text);
                    if (rate==-1||rate<0) {
                        Toast.makeText(getApplicationContext(), "Mức giảm giá không hợp lệ !", Toast.LENGTH_SHORT).show();
                        return;
                    }




                for (int i = 0; i < listEdit.size(); i++) {
                    String id=listEdit.get(i).toString();
                    try {
                    DocumentReference productRef = db.collection("products").document(id);
                    productRef.update("sale", rate);

                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Lỗi hệ thống !", Toast.LENGTH_SHORT).show();
                         return;
                    }

                }
                Toast.makeText(getApplicationContext(), "Tạo khuyến mãi thành công !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),activity_promotions.class));


            }
        });
    }
    public Integer checkValidRate(String text){
        try {

            Integer rate = Integer.parseInt(text);
            return rate;
        } catch (Exception e) {
           return  -1;
        }
    }


}