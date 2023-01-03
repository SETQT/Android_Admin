package com.example.g8shopadmin.activities;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.Services.SendNotification;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.databinding.ActivityAdminCreateProductBinding;
import com.example.g8shopadmin.databinding.ActivityQrcodeBinding;
import com.example.g8shopadmin.models.Order;
import com.example.g8shopadmin.models.Scan;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class activity_qrcode extends Activity implements AdapterView.OnItemSelectedListener{
    Uri newImage;
    Integer state;
    String[] type = {"Momo", "Ngân hàng", "Zalopay"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference scanRef = db.collection("scan");
    String typeSelected;
    private static final int PICK_IMAGE = 100;
    private boolean checkExitsImage = false;
    private ActivityQrcodeBinding binding;
    private Scan newScan;

    private String iddoc = "";
    private String oldImage;


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    // request permission can thiet camera,thu vien ,...
    public void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(activity_qrcode.this, " Cấp quyền thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(activity_qrcode.this, "Đã từ chối!!", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("Không thể thực hiện do chưa cấp quyền truy cập!! \n\n Thay đổi bằng cách Setting -> Permission")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            binding.imageQrcode.setImageURI(imageUri);
            checkExitsImage = true;
            newImage = data.getData();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityQrcodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Spiner product
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_create_voucher, type);
        binding.spinnerOption.setAdapter(adapter);
        binding.spinnerOption.setOnItemSelectedListener(this);

        super.onCreate(savedInstanceState);

        binding.iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        binding.buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        typeSelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void changeImage() {
        if (ContextCompat.checkSelfPermission(activity_qrcode.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

            openGallery();


        } else {
            requestPermission();

            if (ContextCompat.checkSelfPermission(activity_qrcode.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {

                openGallery();

            }
        }
    }

    public Boolean checkData(String type, String account_number, String name) {
        if (type.length() == 0 || account_number.length() == 0 ||name.length() == 0)
            return false;
        return true;
    }

    public void updateData(Bundle extras) {
        checkExitsImage = true;
        binding.valueAccountNumber.setText(extras.getString("number"));
        binding.valueName.setText(extras.getString("name"));
        oldImage = extras.getString("image");
        Picasso.with(getApplicationContext()).load(oldImage).into(binding.imageQrcode);
    }

    public void initData() {
        if (typeSelected == "Momo"){
            state = 1;
        }
        else if (typeSelected == "Zalopay"){
            state = 2;
        } else {
            state = 3;
        }
        String type = typeSelected;
        String number = binding.valueAccountNumber.getText().toString();
        String name = binding.valueName.getText().toString();

        if (!checkExitsImage) {
            new AlertDialog.Builder(activity_qrcode.this)
                    .setMessage("Thiếu ảnh mã QR!")
                    .setCancelable(true)
                    .show();
            return;
        }
        if (!checkData(type, number, name)) {
            new AlertDialog.Builder(activity_qrcode.this)
                    .setMessage("Nhập thiếu thông tin!")
                    .setCancelable(true)
                    .show();
            return;
        }

        newScan = new Scan("", name, number, state);
        try {
            uploadFile();
        } catch (Exception e) {
        }
    }

    public void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference refImage = storageRef.child("image/" + newScan.getName());

        UploadTask uploadTask = refImage.putFile(newImage);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // upload file xong tiếp tục lấy link image vừa upload
                return refImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String urlImageProduct = downloadUri.toString();
                    newScan.setImg(urlImageProduct);

                    scanRef
                            .whereEqualTo("state", state)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            iddoc = document.getId();
                                            scanRef.document(iddoc).update("img", newScan.getImg());
                                            scanRef.document(iddoc).update("name", newScan.getName());
                                            scanRef.document(iddoc).update("option", newScan.getOption());
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                        Toast.makeText(getApplicationContext(), "Cập nhật mã QR thanh toán thành công !", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), activity_qrcode.class));
                } else {
                    return;
                }
            }
        });
    }
}
