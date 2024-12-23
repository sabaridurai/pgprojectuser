package com.example.pickmybus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class second extends Fragment implements Myadapter.OnNoteListener {

    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    Myadapter myadapter;
    ArrayList<UserData> list;
    public String ID, tname, from, To, vehiclename;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


     View view=inflater.inflate(R.layout.fragment_second, container, false);
    DatabaseReference databaseReference=firebaseDatabase.getReference("owner");

     recyclerView=view.findViewById(R.id.cycler);
     recyclerView.setHasFixedSize(true);
     RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
     recyclerView.setLayoutManager(layoutManager);



list=new ArrayList<>();
        myadapter=new Myadapter(requireActivity(),list,this);


    recyclerView.setAdapter(myadapter);



        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ID = snapshot.getKey();
                Log.e("ID",ID);
                ValueEventListener ner =  firebaseDatabase.getReference("owner").child(ID).child("Transportdetail").child("Transportname").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        tname = snapshot.getValue().toString();
                        tname="abc";
                        Log.e("Tname",  tname + " " + ID);
                        ChildEventListener db =  firebaseDatabase.getReference("owner").child(ID).child("routes").addChildEventListener(new ChildEventListener() {


                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                vehiclename = String.valueOf(snapshot.getKey());

                                Log.e("name id vname",   vehiclename + " " + ID + " " + tname);

                                ValueEventListener value = firebaseDatabase.getReference("owner").child(ID).child("routes").child(vehiclename).child("from").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        from = String.valueOf(snapshot.getValue());
                                        ValueEventListener value1 = firebaseDatabase.getReference("owner").child(ID).child("routes").child(vehiclename).child("to").addValueEventListener(new ValueEventListener() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                                To = String.valueOf(snapshot1.getValue());
                                                Log.e("from to", from + " " + String.valueOf(snapshot1.getValue()));
//                                                ArrayList<Dataclass> arrayList = new ArrayList<>();
                                                UserData userData = new UserData(ID, tname, from, To, vehiclename);
                                                list.add(userData);
                                                //Toast.makeText(requireActivity(), "==="+list, Toast.LENGTH_SHORT).show();
                                                myadapter.notifyDataSetChanged();



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


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


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //        arrayList.add(new Dataclass(ID,tname,));
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


     return  view;
    }

    @Override
    public void onNoteClick(int position) {

            Intent intent = new Intent(requireActivity(), VehicleBooking.class);
        UserData userData= list.get(position);

        String ownerid=userData.getOwnerID();
        intent.putExtra("value",ownerid);
        intent.putExtra("vehicle",userData.getVehiclename());
        intent.putExtra("from",userData.getFrom());
        intent.putExtra("to",userData.getto());
        intent.putExtra("tname",userData.getTname());

        startActivity(intent);
    }
}