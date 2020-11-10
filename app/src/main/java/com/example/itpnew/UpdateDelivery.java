package com.example.itpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class UpdateDelivery extends AppCompatActivity {

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;
    EditText name , phone , email , address , city, inst ;
    Spinner spin;
    Button button1 , button2;
    DatabaseReference Ref;

    private void clearControls(){
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        city.setText("");
        tvDate.setText("");
        inst.setText("");
        //spin.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery);

        tvDate = findViewById(R.id.date1);
        name = findViewById(R.id.editTextPersonName);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextEmail);
        address = findViewById(R.id.editTextAddress);
        city = findViewById(R.id.editTextCity);
        tvDate = findViewById(R.id.date1);
        inst = findViewById(R.id.editTextIns);
        button1 = findViewById(R.id.button3);
        button2 = findViewById(R.id.btndelete);
        spin = findViewById(R.id.type);


        String nam = getIntent().getStringExtra("nm");
        String pho = getIntent().getStringExtra("ph");
        String ema = getIntent().getStringExtra("em");
        String add = getIntent().getStringExtra("ad");
        String cit = getIntent().getStringExtra("ct");
        String dat = getIntent().getStringExtra("dt");
        String ins = getIntent().getStringExtra("in");
        String spi = getIntent().getStringExtra("sp");

        name.setText(nam);
        phone.setText(pho);
        email.setText(ema);
        address.setText(add);
        city.setText(cit);
        tvDate.setText(dat);
        inst.setText(ins);
        //spin.setSelected(spi);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm = name.getText().toString();
                String ph = phone.getText().toString();
                String em = email.getText().toString();
                String ad = address.getText().toString();
                String ci = city.getText().toString();
                String sp = spin.getSelectedItem().toString();
                String dt = tvDate.getText().toString();
                String in = inst.getText().toString();

                Ref = FirebaseDatabase.getInstance().getReference().child("delivery");

                HashMap hashMap = new HashMap();

                hashMap.put("names",nm);
                hashMap.put("phone",ph);
                hashMap.put("address",ad);
                hashMap.put("city",ci);
                hashMap.put("email",em);
                hashMap.put("spin",sp);
                hashMap.put("date",dt);
                hashMap.put("ins",in);

                Ref.child("6").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),Details.class);
                        startActivity(i);
                    }
                });
            }
        });




        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateDelivery.this,android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+" / "+month+" / "+year;
                tvDate.setText(date);
            }
        };
    }
}