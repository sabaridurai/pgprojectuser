package com.example.pickmybus;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.pickmybus.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private GoogleMap mMap;
    public String latitudeval, longitudeval, driverid,Ownerid,Vehiname,drivername;
    private final Set<String> hash_Set = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.pickmybus.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Fungetdata();

    }

    private void Fungetdata() {
        firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("bookedID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ownerid = String.valueOf(snapshot.getValue());
                Log.e("owner",Ownerid);
                firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("vehicle").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Vehiname = String.valueOf(snapshot.getValue());
                        Log.e("---------Vehi",Vehiname);

                        firebaseDatabase.getReference("owner").child(Ownerid).child("routes").child(Vehiname).child("Dname").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                drivername = String.valueOf(snapshot.getValue());
                                Log.e("-----------dname",drivername);
                                firebaseDatabase.getReference("owner").child(Ownerid).child("wokingdrivers").child(drivername).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        driverid = String.valueOf(snapshot.getValue());

                                        Log.e("----------Driver id",driverid);
                                        firebaseDatabase.getReference("owner").child(Ownerid).child("drivers").child(driverid).child("location").child("latitude").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                latitudeval = String.valueOf(snapshot.getValue());
                                                Log.e("--------latitude", latitudeval);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }

                                        });
                                        firebaseDatabase.getReference("owner").child(Ownerid).child("drivers").child(driverid).child("location").child("longitude").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                longitudeval = String.valueOf(snapshot.getValue());
                                                Log.e("------longitude", longitudeval);


                                                LatLng place = new LatLng(Double.parseDouble(String.valueOf(latitudeval)), Double.parseDouble(String.valueOf(longitudeval)));


                                                final MarkerOptions marker = new MarkerOptions().position(place).title("vehicle");
                                                mMap.clear();
                                                mMap.addMarker(marker);
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
















