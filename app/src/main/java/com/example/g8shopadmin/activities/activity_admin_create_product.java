package com.example.g8shopadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.databinding.ActivityAdminCreateProductBinding;
import com.example.g8shopadmin.databinding.ActivityDashboardBinding;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_admin_create_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    CustomRecylerviewCreatePromotionAdapter myPromotionAdapter;
    //    activity_admin_create_product binding;
    String urlAvatar = "";
    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri newImage;
    CollectionReference prodsRef = db.collection("products");

    private ActivityAdminCreateProductBinding binding;
    private static final int PICK_IMAGE = 100;
    private boolean checkExitsImage = false;
    private Product newProduct;
    private String iddoc="";
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
                Toast.makeText(activity_admin_create_product.this, " Cấp quyền thành công !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(activity_admin_create_product.this, "Đã từ chối !!", Toast.LENGTH_SHORT).show();
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

            binding.recyclerViewProductCreateProduct.setImageURI(imageUri);
            checkExitsImage = true;
            newImage = data.getData();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAdminCreateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        super.onCreate(savedInstanceState);
        try {
            String value = getIntent().getExtras().getString("update");
            if (value != null) {
                iddoc=value;
                updateData(getIntent().getExtras());
//                return;

            }
        } catch (Exception e) {

        }

        binding.icBackAdminCreatePromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_admin_my_products.class);
                startActivity(intent);
            }
        });
        binding.buttonAddImageCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeImage();

            }
        });
        binding.btnAdminCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initData();

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void changeImage() {
        if (ContextCompat.checkSelfPermission(activity_admin_create_product.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {

            openGallery();


        } else {
            requestPermission();

            if (ContextCompat.checkSelfPermission(activity_admin_create_product.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {

                openGallery();

            }
        }
    }

    public Boolean checkData(String type, String color, String descript, String name, String size, String price, String quantity) {
        if (type.length() == 0 || color.length() == 0 || descript.length() == 0 ||
                name.length() == 0 || price.length() == 0 || quantity.length() == 0)
            return false;

        return true;
    }

    public void updateData(Bundle extras) {


        checkExitsImage=true;
        binding.valueTypeProductAdminCreateProduct.setText(extras.getString("category"));

        binding.valueDescribeProductAdminCreateProduct.setText(extras.getString("descript"));
        binding.valueNameProductAdminCreateProduct.setText(extras.getString("name"));

        ArrayList<String> color =extras.getStringArrayList("color");
        String textColor=color.toString().replace("[", "");
        textColor=textColor.replace("]", "");
        binding.valueColorProductAdminCreateProduct.setText(textColor);

        ArrayList<String> size =extras.getStringArrayList("size");
        String textSize=size.toString().replace("[", "");
        textSize=textSize.replace("]", "");
        binding.valueSizeProductAdminCreateProduct.setText(textSize);

        Integer price=extras.getInt("price");
        binding.valuePriceProductAdminCreateProduct.setText(price.toString());
        Integer amount=extras.getInt("amount");
        binding.valueQuantityProductAdminCreateProduct.setText(amount.toString());

         oldImage= extras.getString("image");
        Picasso.with(getApplicationContext()).load(oldImage).into( binding.recyclerViewProductCreateProduct);


    }

    public void initData() {
        String type = binding.valueTypeProductAdminCreateProduct.getText().toString();
        String color = binding.valueColorProductAdminCreateProduct.getText().toString();
        String descript = binding.valueDescribeProductAdminCreateProduct.getText().toString();
        String name = binding.valueNameProductAdminCreateProduct.getText().toString();
        String size = binding.valueSizeProductAdminCreateProduct.getText().toString();
        String price = binding.valuePriceProductAdminCreateProduct.getText().toString();
        String quantity = binding.valueQuantityProductAdminCreateProduct.getText().toString();


        if (!checkExitsImage) {
            Toast.makeText(activity_admin_create_product.this, "Thiếu ảnh sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkData(type, color, descript, name, size, price, quantity)) {
            Toast.makeText(activity_admin_create_product.this, "Nhập thiếu thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer prices;
        Integer amount;

        try {

            prices = Integer.parseInt(price);
        } catch (Exception e) {
            prices = 0;
            Toast.makeText(getApplicationContext(), "Giá không hợp lệ", Toast.LENGTH_SHORT).show();

        }
        try {

            amount = Integer.parseInt(quantity);
        } catch (Exception e) {
            amount = 0;
            Toast.makeText(getApplicationContext(), "Số lượng tin không hợp lệ", Toast.LENGTH_SHORT).show();

        }

        if (prices == 0 || amount == 0) {
//            Toast.makeText(getApplicationContext(), "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("TAG", "initData: " + prices);
        String[] arrayColor = color.split(",");
        String[] arraySize = size.split(",");
        ArrayList<String> list1 = new ArrayList<String>();
        Collections.addAll(list1, arrayColor);
        ArrayList<String> list2 = new ArrayList<String>();
        Collections.addAll(list2, arraySize);

        newProduct = new Product(type, name, prices, amount, 0, "", descript, 0, list1, list2);
        try {

            uploadFile();
        } catch (Exception e) {

        }
    }

    public void uploadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference refImage = storageRef.child("image/" + newProduct.getName());

        if (newImage==null){
            newProduct.setImage(oldImage);
//            prodsRef.add(newProduct);
            prodsRef.document(iddoc).set(newProduct);

            Toast.makeText(getApplicationContext(), "Cập nhật sản phẩm thành công !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), activity_admin_my_products.class));

            return ;
        }
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
                    newProduct.setImage(urlImageProduct);


                    if(!iddoc.equals("")){
                        prodsRef.document(iddoc).set(newProduct);
                        Toast.makeText(getApplicationContext(), "Cập nhật sản phẩm thành công !", Toast.LENGTH_SHORT).show();

                    }else{

                        prodsRef.add(newProduct);
                    Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công !", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(getApplicationContext(), activity_admin_my_products.class));

                } else {
                    return;
                }
            }
        });
    }

}