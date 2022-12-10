package com.example.g8shopadmin.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.databinding.ActivityAdminCreateVoucherBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class activity_admin_create_voucher extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private ActivityAdminCreateVoucherBinding binding;

    String[] typeVoucher = {"Freeship", "Discount"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCreateVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_create_voucher, typeVoucher);
        binding.spinerCreateVoucher.setAdapter(adapter);
        binding.spinerCreateVoucher.setOnItemSelectedListener(this);
        binding.timeStartAdminCreateVoucher.setInputType(InputType.TYPE_NULL);
        binding.timeEndAdminCreateVoucher.setInputType(InputType.TYPE_NULL);

        binding.rectangleTimeStartAdminCreateVoucher.setOnClickListener(this);
        binding.rectangleTimeEndAdminCreateVoucher.setOnClickListener(this);
        binding.icBackAdminCreateVoucher.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.rectangleTimeStartAdminCreateVoucher.getId()) {
            showDateTimeDialog(binding.timeStartAdminCreateVoucher, "start");
        }

        if (view.getId() == binding.rectangleTimeEndAdminCreateVoucher.getId()) {
            showDateTimeDialog(binding.timeEndAdminCreateVoucher, "end");
        }

        if(view.getId() == binding.icBackAdminCreateVoucher.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_admin_manage_voucher.class);
            startActivity(moveActivity);
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
                        } else {
                            timeEditText.setText(simpleDateFormat.format(calendar.getTime()));
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}