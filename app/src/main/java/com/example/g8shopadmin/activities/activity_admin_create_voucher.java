package com.example.g8shopadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class activity_admin_create_voucher extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText time_start,time_end;
    RelativeLayout rectangle_time_start,rectangle_time_end;
    Spinner spinner;
    String[] typeVoucher={"Freeship","Discount"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_voucher);

        spinner=(Spinner)findViewById(R.id.spiner_create_voucher);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_create_voucher, typeVoucher);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        time_start=(EditText) findViewById(R.id.time_start_admin_create_voucher);
        time_end=(EditText) findViewById(R.id.time_end_admin_create_voucher);

        rectangle_time_start=(RelativeLayout)findViewById(R.id.rectangle_time_start_admin_create_voucher);
        rectangle_time_end=(RelativeLayout)findViewById(R.id.rectangle_time_end_admin_create_voucher);

        time_start.setInputType(InputType.TYPE_NULL);
        time_end.setInputType(InputType.TYPE_NULL);


        rectangle_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(time_start,"start");
            }
        });
        rectangle_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(time_end,"end");
            }
        });
    }
    private void showDateTimeDialog(final EditText time_start,String type){
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hour);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        if(type=="start") {
                            time_start.setText(simpleDateFormat.format(calendar.getTime()));
                        }else{
                            time_end.setText(simpleDateFormat.format(calendar.getTime()));
                        }

                    }

                };
                new TimePickerDialog(activity_admin_create_voucher.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

            }
        };
        new DatePickerDialog(activity_admin_create_voucher.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}