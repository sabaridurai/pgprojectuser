package com.example.pickmybus;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class VehicleBooking extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    private String tnam,from,toplace,veh,ownerid;
private EditText phno,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_booking);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        TextView tna = findViewById(R.id.transname);
        TextView vehi = findViewById(R.id.vehicle);
        TextView frm = findViewById(R.id.from);
        TextView too = findViewById(R.id.to);

        phno=findViewById(R.id.phno);
        date=findViewById(R.id.date);

        Button button = findViewById(R.id.bookbtn);

        Intent intent=this.getIntent();

        ownerid= intent.getStringExtra("value");
        tnam=intent.getStringExtra("tname");
        from=intent.getStringExtra("from");
        toplace=intent.getStringExtra("to");
        veh=intent.getStringExtra("vehicle");

        tna.setText(tnam);
        vehi.setText(veh);
        frm.setText(from);
        too.setText(toplace);


        button.setOnClickListener(view -> {
            DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid()));

           String ph= String.valueOf(phno.getText());
           String dat= String.valueOf(date.getText());
           if (TextUtils.isEmpty(ph) || TextUtils.isEmpty(dat)) {
                Toast.makeText(VehicleBooking.this, "Empty value", Toast.LENGTH_SHORT).show();
            }
           else
            {

                databaseReference.child("bookedID").setValue(ownerid);
                databaseReference.child("tname").setValue(tnam);
                databaseReference.child("from").setValue(from);
                databaseReference.child("to").setValue(toplace);
                databaseReference.child("vehicle").setValue(veh);
                databaseReference.child("phno").setValue(ph);
                databaseReference.child("date").setValue(dat).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(VehicleBooking.this, "Vehicle booking successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(VehicleBooking.this, "Vehicle booking faild", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




    }
}