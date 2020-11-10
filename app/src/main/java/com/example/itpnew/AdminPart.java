package com.example.itpnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPart extends AppCompatActivity {

    Button btn,bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_part);


        btn=findViewById(R.id.btnlist);
        bt=findViewById(R.id.bt);


       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent inte = new Intent(getApplicationContext(),List.class);
               startActivity(inte);
           }
       });


       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent in = new Intent(getApplicationContext(),Summary.class);
               startActivity(in);
           }
       });

    }
}