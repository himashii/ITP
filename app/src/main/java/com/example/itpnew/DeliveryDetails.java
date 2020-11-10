package com.example.itpnew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class DeliveryDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;

    delivery delivery;
    DatabaseReference Ref;
    private File pdfFile;
    Button button , btn ,bt,bt1,bt2 ;
    EditText insertName;
    EditText insertFn;
    EditText insertEmail;
    TextView name,color,price,size,qty;
    EditText insertAddress ,  insertCity , insertIns;
    Spinner spin;
    AwesomeValidation awesomeValidation;

    private void clearControls(){
        insertName.setText("");
        insertFn.setText("");
        insertEmail.setText("");
        insertAddress.setText("");
        insertCity.setText("");
        tvDate.setText("");
        insertIns.setText("");
        //spin.setText("");
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverydetails);

        Spinner aSpinner = findViewById(R.id.type);
        aSpinner.setOnItemSelectedListener(this);

        insertName = findViewById(R.id.etPersonName);
        insertFn = findViewById(R.id.editPhone);
        insertEmail = findViewById(R.id.editEmailAddress);
        insertAddress = findViewById(R.id.editPostalAddress);
        insertCity = findViewById(R.id.editCity);
        spin = findViewById(R.id.type);
        tvDate = findViewById(R.id.date1);
        insertIns = findViewById(R.id.editTextPersonName2);
        bt = findViewById(R.id.bt);
        bt2 = findViewById(R.id.go);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        color = findViewById(R.id.color);
        size = findViewById(R.id.size);
        qty = findViewById(R.id.qty);


        button = findViewById(R.id.btnupload);

        bt1 = findViewById(R.id.print);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();

        delivery = new delivery();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.etPersonName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.editPhone,
                "[0-9]{10}$",R.string.invalid_phone);
        awesomeValidation.addValidation(this,R.id.editEmailAddress,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.editPostalAddress,
                RegexTemplate.NOT_EMPTY,R.string.invalid_address_code);
        awesomeValidation.addValidation(this,R.id.editCity,
                RegexTemplate.NOT_EMPTY,R.string.invalid_city);




        DatabaseReference UpRef = FirebaseDatabase.getInstance().getReference().child("Item").child("6");
        UpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    color.setText(dataSnapshot.child("color").getValue().toString());
                    price.setText(dataSnapshot.child("price").getValue().toString());
                    size.setText(dataSnapshot.child("size").getValue().toString());
                    qty.setText(dataSnapshot.child("qty").getValue().toString());


                }else
                    Toast.makeText(getApplicationContext(),"No  Source to Display",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {

                    Ref = FirebaseDatabase.getInstance().getReference().child("delivery");

                    delivery.setNames(insertName.getText().toString().trim());
                    delivery.setPhone(insertFn.getText().toString().trim());
                    delivery.setEmail(insertEmail.getText().toString().trim());
                    delivery.setAddress(insertAddress.getText().toString().trim());
                    delivery.setCity(insertCity.getText().toString().trim());
                    delivery.setDate(tvDate.getText().toString().trim());
                    delivery.setIns(insertIns.getText().toString().trim());
                    delivery.setSpin(spin.getSelectedItem().toString());
                    delivery.setName(name.getText().toString().trim());
                    delivery.setColor(color.getText().toString().trim());
                    delivery.setPrice(price.getText().toString().trim());
                    delivery.setSize(size.getText().toString().trim());
                    delivery.setQty(qty.getText().toString().trim());

                    //Ref.push().setValue(delivery);
                    Ref.child("6").setValue(delivery);



                    Ref = FirebaseDatabase.getInstance().getReference().child("summary");

                    delivery.setNames(insertName.getText().toString().trim());
                    delivery.setPhone(insertFn.getText().toString().trim());
                    delivery.setEmail(insertEmail.getText().toString().trim());
                    delivery.setAddress(insertAddress.getText().toString().trim());
                    delivery.setCity(insertCity.getText().toString().trim());
                    delivery.setDate(tvDate.getText().toString().trim());
                    delivery.setIns(insertIns.getText().toString().trim());
                    delivery.setSpin(spin.getSelectedItem().toString());
                    delivery.setName(name.getText().toString().trim());
                    delivery.setColor(color.getText().toString().trim());
                    delivery.setPrice(price.getText().toString().trim());
                    delivery.setSize(size.getText().toString().trim());
                    delivery.setQty(qty.getText().toString().trim());

                    //Ref.push().setValue(delivery);
                    Ref.child("6").setValue(delivery);

                    Toast.makeText(getApplicationContext(),"validated Successfully..",Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(getApplicationContext(),Details.class);
                   // clearControls();
                    startActivity(i);


                  /*  String nm = insertName.getText().toString();
                    String ph = insertFn.getText().toString();
                    String em = insertEmail.getText().toString();
                    String ad = insertAddress.getText().toString();
                    String ci = insertCity.getText().toString();
                    String sp = spin.getSelectedItem().toString();
                    String dt = tvDate.getText().toString();
                    String in = insertIns.getText().toString();

                    Ref = FirebaseDatabase.getInstance().getReference().child("Item");

                    HashMap hashMap = new HashMap();

                    hashMap.put("names", nm);
                    hashMap.put("phone", ph);
                    hashMap.put("address", ad);
                    hashMap.put("city", ci);
                    hashMap.put("email", em);
                    hashMap.put("spin", sp);
                    hashMap.put("date", dt);
                    hashMap.put("ins", in);

                    Ref.child("1").updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(getApplicationContext(), Details.class);

                            startActivity(i);
                        }
                    }); */

                } else {
                    Toast.makeText(getApplicationContext(), "Validation Failed..", Toast.LENGTH_SHORT).show();
                }
            }
        });




                 /*   final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!(dataSnapshot.child("Item").child("1").exists())){
                                HashMap<String, Object> cusData = new HashMap<>();
                                cusData.put("phone", phone);
                                cusData.put("email", data2);
                                cusData.put("Cname", name);
                                cusData.put("address", data3);
                                cusData.put("city", data4);
                                cusData.put("date", data5);
                                cusData.put("instructions", data6);
                                cusData.put("sp", data7);

                                ref.child("Item").child("1").updateChildren(cusData)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"Added successfully!!",Toast.LENGTH_SHORT).show();
                                                    //loadingBar.dismiss();

                                                    Intent i = new Intent(getApplicationContext(),Details.class);
                                                    startActivity(i);

                                                }
                                                else {
                                                    //loadingBar.dismiss();
                                                    Toast.makeText(getApplicationContext(),"Try Again!",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_SHORT).show();
                                //loadingBar.dismiss();

                                //Intent i = new Intent(getApplicationContext(),login.class);
                                //startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/



                /*}else {
                    Toast.makeText(getApplicationContext(),"Validation Failed..",Toast.LENGTH_SHORT).show();
                }*/




       /* btn = findViewById(R.id.btndelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(),List.class);
                startActivity(in);
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Summary.class);
                startActivity(i);
            }
        });*/

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AdminPart.class);
                startActivity(i);
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
                        DeliveryDetails.this,android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,setListener,year,month,day);
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

    private void createPDF() {
        bt1.setOnClickListener(new View.OnClickListener() {
            //private static final Object TAG =i ;

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (insertName.getText().toString().length()==0 || insertFn.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"No source to create a PDF",Toast.LENGTH_SHORT).show();
                }
                else {
                    PdfDocument pdfDocument = new PdfDocument();
                    Paint paint = new Paint();
                    Paint titlePaint = new Paint();

                    PdfDocument.PageInfo pageInfo
                            = new PdfDocument.PageInfo.Builder(1200,2010,1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                    Canvas canvas = page.getCanvas();

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(2);
                    //canvas.drawRect(20,780,pageWidth-20,860,paint);

                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setStyle(Paint.Style.FILL);

                    canvas.drawText("Name",30,30,paint);
                    canvas.drawText("Phone",120,30,paint);
                    canvas.drawText("Address",220,30,paint);
                    canvas.drawText("Date",300,30,paint);
                    canvas.drawText("City",400,30,paint);
                    canvas.drawText("Email",480,30,paint);

                    /*canvas.drawLine(180,79,18,84,paint);
                    canvas.drawLine(680,79,68,84,paint);
                    canvas.drawLine(680,79,88,84,paint);
                    canvas.drawLine(1030,79,103,84,paint);*/

                    canvas.drawText(insertName.getText().toString(),30,50,paint);
                    canvas.drawText(insertFn.getText().toString(),120,50,paint);
                    canvas.drawText(insertAddress.getText().toString(),220,50,paint);
                    canvas.drawText(tvDate.getText().toString(),300,50,paint);
                    canvas.drawText(insertCity.getText().toString(),400,50,paint);
                    canvas.drawText(insertEmail.getText().toString(),480,50,paint);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(118);



                    pdfDocument.finishPage(page);

                    File file = new File(Environment.getExternalStorageDirectory()+"/Himashi");
                    if (!file.exists()){
                        file.mkdir();
                        //Log.i(TAG, "Create a new directory for PDF");
                    }

                    String pdfName = "DeliveryDetails.pdf";
                    pdfFile = new File(file.getAbsolutePath(), pdfName);

                    try {
                        pdfDocument.writeTo(new FileOutputStream(pdfFile));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*try {
                        pdfDocument.writeTo(new FileOutputStream(file));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                    pdfDocument.close();
                    Toast.makeText(getApplicationContext(),"PDF created!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void showToast(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),"Details Added",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}