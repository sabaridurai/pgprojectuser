package com.example.pickmybus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class studentsignup extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public EditText smail, spassword,sconpassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup);

        firebaseAuth = FirebaseAuth.getInstance();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        smail = findViewById(R.id.studentmail);
        spassword = findViewById(R.id.studentpassword);
        sconpassword=findViewById(R.id.studentconpassword);
        Button signup = findViewById(R.id.studentsignupbtn);

        progressDialog = new ProgressDialog(this);
        signup.setOnClickListener(view -> {

            String smailid = smail.getText().toString();
            String spass = spassword.getText().toString();
            String sconpass = sconpassword.getText().toString();
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            {
                if (!TextUtils.isEmpty(smailid) && !TextUtils.isEmpty(spass)) {

                    if(spass.equals(sconpass)) {
                        progressDialog.setMessage("Registering Please wait");
                        progressDialog.show();

                        firebaseAuth.createUserWithEmailAndPassword(smailid, spass).addOnCompleteListener(studentsignup.this, task -> {

                            if (task.isSuccessful()) {

                                Toast.makeText(studentsignup.this, "Account creation is successful", Toast.LENGTH_LONG).show();
                                Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(studentsignup.this, "please check and verify your email", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(studentsignup.this, studentlogin.class);
                                        intent.putExtra("mailid",smailid);
                                        intent.putExtra("password",spass);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(studentsignup.this, "Error in send verify link then retry", Toast.LENGTH_LONG).show();

                                    }


                                });
                            } else {
                                Toast.makeText(studentsignup.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }


                            progressDialog.dismiss();


                        });
                    }
                    else{
                    Toast.makeText(studentsignup.this,"Password and conform password not match",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(studentsignup.this, "Empty value", Toast.LENGTH_LONG).show();
                }



                }
            else {
                Toast.makeText(studentsignup.this,"Please connect to the Internet",Toast.LENGTH_SHORT).show();
            }


        });




    }
}