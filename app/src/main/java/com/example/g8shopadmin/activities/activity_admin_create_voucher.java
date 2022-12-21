package com.example.g8shopadmin.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.Services.SendNotification;
import com.example.g8shopadmin.databinding.ActivityAdminCreateVoucherBinding;
import com.example.g8shopadmin.models.User;
import com.example.g8shopadmin.models.Voucher;
import com.example.g8shopadmin.utilities.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class activity_admin_create_voucher extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ActivityAdminCreateVoucherBinding binding;
    private final int GALLERY_REQ_CODE = 1000;

    String[] typeVouchers = {"Freeship", "Discount"};
    String typeSelected;
    Date timeCreatedAt, timeFinishedAt;
    Uri pathImageVoucher;
    String idDocVoucher;
    Voucher curVoucher;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference vouchersRef = db.collection("vouchers");
    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCreateVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_create_voucher, typeVouchers);
        binding.spinerCreateVoucher.setAdapter(adapter);
        binding.spinerCreateVoucher.setOnItemSelectedListener(this);
        binding.timeStartAdminCreateVoucher.setInputType(InputType.TYPE_NULL);
        binding.timeEndAdminCreateVoucher.setInputType(InputType.TYPE_NULL);

        binding.rectangleTimeStartAdminCreateVoucher.setOnClickListener(this);
        binding.rectangleTimeEndAdminCreateVoucher.setOnClickListener(this);
        binding.icBackAdminCreateVoucher.setOnClickListener(this);
        binding.buttonAddImageCreateVoucher.setOnClickListener(this);
        binding.btnAdminCreateVoucher.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.IDDOC_VOUCHER_TO_CREATE_VOUCHER)) {
            idDocVoucher = intent.getStringExtra(Constants.IDDOC_VOUCHER_TO_CREATE_VOUCHER);

            vouchersRef.document(idDocVoucher).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        curVoucher = task.getResult().toObject(Voucher.class);

                        binding.adminNameVoucherCreateVoucher.setText(curVoucher.getTitle());
                        binding.valueReductionRateAdminCreateVoucher.setText(curVoucher.getMoneyDeals().toString());
                        binding.valueMinOrderAdminCreateVoucher.setText(curVoucher.getMinimumCost().toString());
                        binding.valueNumberCodeAdminCreateVoucher.setText(curVoucher.getAmount().toString());

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        timeCreatedAt = curVoucher.getStartedAt();
                        timeFinishedAt = curVoucher.getFinishedAt();

                        binding.timeStartAdminCreateVoucher.setText(simpleDateFormat.format(curVoucher.getStartedAt()));
                        binding.timeEndAdminCreateVoucher.setText(simpleDateFormat.format(curVoucher.getFinishedAt()));
                        binding.codeMarkDownCreateVoucher.setText(curVoucher.getId());
                        Picasso.with(getApplicationContext()).load(curVoucher.getImage()).into(binding.imageVoucherCreateVoucher);
                        switch (curVoucher.getType()) {
                            case "freeship":
                                binding.spinerCreateVoucher.setSelection(0);
                                break;
                            case "discount":
                                binding.spinerCreateVoucher.setSelection(1);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.rectangleTimeStartAdminCreateVoucher.getId()) {
            showDateTimeDialog(binding.timeStartAdminCreateVoucher, "start");
        }

        if (view.getId() == binding.rectangleTimeEndAdminCreateVoucher.getId()) {
            showDateTimeDialog(binding.timeEndAdminCreateVoucher, "end");
        }

        if (view.getId() == binding.icBackAdminCreateVoucher.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_manage_voucher.class);
            startActivity(moveActivity);
        }

        if (view.getId() == binding.buttonAddImageCreateVoucher.getId()) {
            // truy cập vào thư viện ảnh của máy lấy ảnh
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        }

        if (view.getId() == binding.btnAdminCreateVoucher.getId()) {
            // check thông tin có empty hay không
            if (binding.adminNameVoucherCreateVoucher.getText() == null ||
                    binding.valueReductionRateAdminCreateVoucher.getText() == null ||
                    binding.valueMinOrderAdminCreateVoucher.getText() == null ||
                    binding.valueNumberCodeAdminCreateVoucher.getText() == null ||
                    timeCreatedAt == null ||
                    timeFinishedAt == null ||
                    (pathImageVoucher == null && idDocVoucher == null)) {
                new AlertDialog.Builder(activity_admin_create_voucher.this)
                        .setMessage("Vui lòng điền đầy đủ thông tin!")
                        .setCancelable(true)
                        .show();
            } else {
                // lấy thông tin voucher
                String name = binding.adminNameVoucherCreateVoucher.getText().toString().toUpperCase();
                String typeVoucher = typeSelected.toLowerCase();
                Integer moneyDeals = Integer.parseInt(binding.valueReductionRateAdminCreateVoucher.getText().toString());
                Integer minimumCost = Integer.parseInt(binding.valueMinOrderAdminCreateVoucher.getText().toString());
                Integer amount = Integer.parseInt(binding.valueNumberCodeAdminCreateVoucher.getText().toString());
                Date startedAt = timeCreatedAt;
                Date finishedAt = timeFinishedAt;

                Voucher newVoucher = new Voucher(name, minimumCost, amount, startedAt, finishedAt, moneyDeals, typeVoucher);
                newVoucher.generateID();

                binding.codeMarkDownCreateVoucher.setText(newVoucher.getId());

                try {
                    if (pathImageVoucher != null) {
                        uploadFileAndPostToDB(newVoucher, pathImageVoucher);
                    } else {
                        newVoucher.setImage(curVoucher.getImage());

                        // cập nhật lên db
                        vouchersRef.document(idDocVoucher).set(newVoucher);
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (idDocVoucher != null) {
                                Toast.makeText(activity_admin_create_voucher.this, "Thêm voucher thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity_admin_create_voucher.this, "Cập nhật voucher thành công!", Toast.LENGTH_SHORT).show();
                            }

                            Intent moveActivity = new Intent(activity_admin_create_voucher.this, activity_admin_manage_voucher.class);
                            activity_admin_create_voucher.this.startActivity(moveActivity);
                            activity_admin_create_voucher.this.finish();
                        }
                    }, 3000);
                } catch (Exception error) {
                    Log.e("ERROR", "activity_admin_create_voucher onClick: ", error);
                }
            }
        }
    }

    private void showDateTimeDialog(EditText timeEditText, String type) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        if (type == "start") {
                            timeEditText.setText(simpleDateFormat.format(calendar.getTime()));
                            timeCreatedAt = calendar.getTime();
                        } else {
                            timeEditText.setText(simpleDateFormat.format(calendar.getTime()));
                            timeFinishedAt = calendar.getTime();
                        }
                    }
                };
                new TimePickerDialog(activity_admin_create_voucher.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(activity_admin_create_voucher.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        typeSelected = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                // preview ảnh
                binding.imageVoucherCreateVoucher.setImageURI(data.getData());

                pathImageVoucher = data.getData();
            }
        }
    }

    public void uploadFileAndPostToDB(Voucher newVoucher, Uri pathImage) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference refImage = storageRef.child("voucher/" + newVoucher.generateSlugVoucher());

        UploadTask uploadTask = refImage.putFile(pathImage);

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

                    String urlImageVoucher = downloadUri.toString();

                    newVoucher.setImage(urlImageVoucher);
                    if (idDocVoucher == null) {
                        vouchersRef.add(newVoucher);

                        String title = "Shop vừa có thêm voucher mới đó!";
                        String content = "Voucher mới " + newVoucher.getTitle() + " vừa được bắt đầu trên G8Shop. Ghé xem ngay!";

                        // thông báo đến toàn bộ người dùng là sản phẩm mới được đăng lên
                        usersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        User user = document.toObject(User.class);
                                        SendNotification.pushNotifcication(activity_admin_create_voucher.this, user.getFcmToken(), title, content, "SERVER_VOUCHER");
                                    }
                                }
                            }
                        });
                    } else {
                        vouchersRef.document(idDocVoucher).set(newVoucher);
                    }
                } else {
                    return;
                }
            }
        });
    }
}