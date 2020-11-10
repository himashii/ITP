package com.example.itpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Details extends AppCompatActivity {

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;
    Button button1 , button2;
    TextView name, phone, email, address, city, ins,spin;

    DatabaseReference Ref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        tvDate = findViewById(R.id.date1);
        ins = findViewById(R.id.ins);
        spin = findViewById(R.id.type);

        button1 = findViewById(R.id.btnupdate);
        button2 = findViewById(R.id.btndelete);



        DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("delivery").child("6");
        UpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    name.setText(dataSnapshot.child("names").getValue().toString());
                    phone.setText(dataSnapshot.child("phone").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    address.setText(dataSnapshot.child("address").getValue().toString());
                    city.setText(dataSnapshot.child("city").getValue().toString());
                    tvDate.setText(dataSnapshot.child("date").getValue().toString());
                    ins.setText(dataSnapshot.child("ins").getValue().toString());
                    spin.setText(dataSnapshot.child("spin").getValue().toString());

                }else
                    Toast.makeText(getApplicationContext(),"No  Source to Display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*String nm = getIntent().getStringExtra("nm");
        String ph = getIntent().getStringExtra("ph");
        String em = getIntent().getStringExtra("em");
        String ad = getIntent().getStringExtra("ad");
        String ct = getIntent().getStringExtra("ct");
        String dt = getIntent().getStringExtra("dt");
        String is = getIntent().getStringExtra("in");
        String sp = getIntent().getStringExtra("sp");


        name.setText(nm);
        phone.setText(ph);
        email.setText(em);
        address.setText(ad);
        city.setText(ct);
        tvDate.setText(dt);
        ins.setText(is);
        spin.setText(sp);*/



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nam = name.getText().toString();
                String pho = phone.getText().toString();
                String ema = email.getText().toString();
                String add = address.getText().toString();
                String cit = city.getText().toString();
                String dat = tvDate.getText().toString();
                String inst = ins.getText().toString();
                String spi = spin.getText().toString();

                Intent i = new Intent(getApplicationContext(),UpdateDelivery.class);

                i.putExtra("nm",nam);
                i.putExtra("ph",pho);
                i.putExtra("em",ema);
                i.putExtra("ad",add);
                i.putExtra("ct",cit);
                i.putExtra("dt",dat);
                i.putExtra("in",inst);
                i.putExtra("sp",spi);
                startActivity(i);
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("delivery");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("6")){
                            Ref = FirebaseDatabase.getInstance().getReference().child("delivery").child("6");
                            Ref.child("names").removeValue();
                            Ref.child("phone").removeValue();
                            Ref.child("email").removeValue();
                            Ref.child("city").removeValue();
                            Ref.child("address").removeValue();
                            Ref.child("spin").removeValue();
                            Ref.child("ins").removeValue();
                            Ref.child("date").removeValue();

                            Toast.makeText(getApplicationContext(),"Details Deleted Successfully..",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),DeliveryDetails.class);
                            startActivity(i);
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        tvDate = findViewById(R.id.date1);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Details.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = day + " / " + month + " / " + year;
                tvDate.setText(date);
            }
        };

    }
}