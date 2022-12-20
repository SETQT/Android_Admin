package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.databinding.ActivityAdminRecordCustomerBinding;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class activity_admin_record_customer extends Activity implements View.OnClickListener {
    private ActivityAdminRecordCustomerBinding binding;
    View icon_back;
    Button ban_account;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef = db.collection("users");
    String username;
    String idDoc;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRecordCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView name = (TextView) findViewById(R.id.admin_record_customer_ten);
        TextView bio = (TextView) findViewById(R.id.admin_record_customer_bio);
        TextView sex = (TextView) findViewById(R.id.admin_record_customer_sex);
        TextView dob = (TextView) findViewById(R.id.admin_record_customer_dob);
        TextView email = (TextView) findViewById(R.id.admin_record_customer_email);
        TextView phone = (TextView) findViewById(R.id.admin_record_customer_phone);
        TextView address = (TextView) findViewById(R.id.admin_record_customer_address);
        ImageView avatar = (ImageView) findViewById(R.id.admin_record_avatar);

        Intent myCallerIntent = getIntent();
        Bundle myBundle = myCallerIntent.getExtras();
        username = myBundle.getString("username");
        idDoc = myBundle.getString("idDoc");


        userRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                name.setText(user.getFullname());
                                bio.setText(user.getBio());
                                sex.setText(user.getGender());
                                if (user.getBirthdate() != null) {
                                    dob.setText(simpleDateFormat.format(user.getBirthdate()));
                                } else {
                                    dob.setText("");
                                }
                                email.setText(user.getEmail());
                                phone.setText(user.getPhone());
                                address.setText(user.getAddress());
                                if (user.getImage() != null) {
                                    Picasso.with(getApplicationContext()).load(user.getImage()).into(avatar);
                                }

                                break;
                            }
                        }
                    }
                });

        icon_back = (View) findViewById(R.id.admin_record_ic_back);
        icon_back.setOnClickListener(this);
        ban_account = (Button) findViewById(R.id.btn_admin_ban_account_customer);
        ban_account.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_list_customer.class);
            startActivity(moveActivity);
        }
        if (view.getId() == ban_account.getId()) {
            new AlertDialog.Builder(this)
                    .setMessage("Bạn có chắc muốn khóa tài khoản này không?")
                    .setCancelable(true)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            userRef.document(idDoc).update("status", 1);
                            Toast.makeText(getApplicationContext(), "Khóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            Intent moveActivity = new Intent(getApplicationContext(), activity_list_customer.class);
                            startActivity(moveActivity);
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        }
    }
}
