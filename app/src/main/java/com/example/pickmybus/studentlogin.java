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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class studentlogin extends AppCompatActivity {
    public TextView donthaveaccount;
    public EditText loginid,loginpass;
    public Button loginbtn;
    public FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);

        loginid=findViewById(R.id.studentmail);
        loginpass=findViewById(R.id.studentpassword);
        loginbtn=findViewById(R.id.studentloginbtn);
        donthaveaccount=findViewById(R.id.register);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        donthaveaccount.setOnClickListener(view -> {
            Intent intent=new Intent(studentlogin.this,studentsignup.class);
            startActivity(intent);
        });


        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {

            Intent intent = new Intent(studentlogin.this, studenthome.class);
            startActivity(intent);
            finish();


        }
        //value form signup page
        Intent intent1=this.getIntent();
        if (intent1!=null)
        {
            loginid.setText(intent1.getStringExtra("mailid"));
            loginpass.setText(intent1.getStringExtra("password"));
        }

        loginbtn.setOnClickListener(view ->{
                //check connection
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    String lmail = loginid.getText().toString();
                    String lpass = loginpass.getText().toString();

                    if (!TextUtils.isEmpty(lmail) && !TextUtils.isEmpty(lpass)) {
                        progressDialog.setMessage("Login.. Please wait");
                        progressDialog.show();
                        Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(lmail, lpass).addOnCompleteListener(task -> {
                            //task success or not
                            if (task.isSuccessful()) {
                                //mail verified or not
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(studentlogin.this, studenthome.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{

                                    Toast.makeText(studentlogin.this, "your account is not verified please check Inbox", Toast.LENGTH_SHORT).show();
                                }


                            }
                                else {
                                    Toast.makeText(studentlogin.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                        });
                    } else {
                        //empty value
                        Toast.makeText(studentlogin.this, "Empty value", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(studentlogin.this,"Please connect to the Internet",Toast.LENGTH_SHORT).show();
                }
        });











    }


    //back pressed
    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}