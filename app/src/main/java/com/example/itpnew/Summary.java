package com.example.itpnew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Summary extends AppCompatActivity {

    RecyclerView recview;
    myadapter1 adapter;
    //private File pdfFile;
   // Button bt1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        recview = (RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

       /* bt1 = findViewById(R.id.print);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();*/


        FirebaseRecyclerOptions<delivery> options = new FirebaseRecyclerOptions.Builder<delivery>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("summary"),delivery.class).build();

        adapter = new myadapter1(options);
        recview.setAdapter(adapter);


    }




   /* private void createPDF() {
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
                    canvas.drawText("Email",480,30,paint);*/

                    /*canvas.drawLine(180,79,18,84,paint);
                    canvas.drawLine(680,79,68,84,paint);
                    canvas.drawLine(680,79,88,84,paint);
                    canvas.drawLine(1030,79,103,84,paint);*/

                    /*canvas.drawText(insertName.getText().toString(),30,50,paint);
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
                    }*/
                    /*try {
                        pdfDocument.writeTo(new FileOutputStream(file));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                   /* pdfDocument.close();
                    Toast.makeText(getApplicationContext(),"PDF created!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }*/




    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

}