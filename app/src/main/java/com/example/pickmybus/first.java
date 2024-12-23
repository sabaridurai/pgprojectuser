package com.example.pickmybus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class first extends Fragment {

 FirebaseAuth firebaseAuth;
 FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_first, container, false);
       firebaseAuth=FirebaseAuth.getInstance();
       firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(requireContext());
        DatabaseReference db=firebaseDatabase.getReference("Users");
        TextView tna = view.findViewById(R.id.transname);
        TextView vehi =view.findViewById(R.id.vehicle);
        TextView frm = view.findViewById(R.id.from);
        TextView too = view.findViewById(R.id.to);
        TextView tick=view.findViewById(R.id.ticketno);
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });
         tick.setText(firebaseAuth.getUid());
        progressDialog.setMessage("Wait");
       // progressDialog.show();
        db.child(Objects.requireNonNull(firebaseAuth.getUid()));
    db.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String mm= String.valueOf(snapshot.child("from").getValue());
            String jm= String.valueOf(snapshot.child("to").getValue());
            String nm= String.valueOf(snapshot.child("tname").getValue());
            String vehicle= String.valueOf(snapshot.child("vehicle").getValue());
            Log.e("vak", String.valueOf(snapshot));

            tna.setText(nm);
            vehi.setText(vehicle);
            frm.setText(mm);
            too.setText(jm);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        return view;
    }
    }