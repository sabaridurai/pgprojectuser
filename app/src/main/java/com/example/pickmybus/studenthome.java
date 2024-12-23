package com.example.pickmybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class studenthome extends AppCompatActivity {
    private long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studenthome);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            //
        }





        BottomNavigationView navigationView = findViewById(R.id.nav);


        navigationView.setSelectedItemId(R.id.nav_home);
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.viewarea, new first());
        fragmentTransaction1.commit();

        navigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_home: {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new first());

                    fragmentTransaction.commit();
                    return true;

                }
                case R.id.nav_add: {


                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new second());
                    fragmentTransaction.commit();
                    return true;

                }
                case R.id.nav_view: {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.viewarea, new third());
                    fragmentTransaction.commit();
                    return true;

                }

            }
            return false;

        });

    }
    @Override
    public void onBackPressed() {
        BottomNavigationView bottomNavigationView= findViewById(R.id.nav);
        if (back_pressed + 950 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
        back_pressed = System.currentTimeMillis();
    }
}