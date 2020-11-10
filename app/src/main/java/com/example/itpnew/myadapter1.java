package com.example.itpnew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import static androidx.recyclerview.widget.RecyclerView.*;

public class myadapter1 extends FirebaseRecyclerAdapter<delivery,myadapter1.myviewholder> {


    public myadapter1(@NonNull FirebaseRecyclerOptions<delivery> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final delivery model) {

        holder.name.setText(model.getName());
        holder.color.setText(model.getColor());
        holder.price.setText(model.getPrice());
        holder.qty.setText(model.getQty());
        holder.size.setText(model.getSize());
        holder.date.setText(model.getDate());



       /* holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Do you want to Delete?");
               // builder.setMessage("If You Already Checked This Order, You Can Remove..");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Delivery")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });*/




    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checked,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends ViewHolder{

        TextView name,color,price,qty,size, date;
        Button button;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            color = (TextView)itemView.findViewById(R.id.color);
            price = (TextView)itemView.findViewById(R.id.price);
            qty = (TextView)itemView.findViewById(R.id.qty);
            size = (TextView)itemView.findViewById(R.id.size);
            date =(TextView)itemView.findViewById(R.id.date);
            button=(Button)itemView.findViewById(R.id.button);




        }
    }
}
