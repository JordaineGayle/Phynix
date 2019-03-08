package com.thephynix.www.phynix;



import android.app.Activity;

import android.content.Context;

import android.content.Intent;

import android.net.Network;

import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;

import android.view.Window;



import android.view.WindowManager;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;


import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;


import java.net.URISyntaxException;
import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;



public class LoginActivity extends AppCompatActivity {





    private FirebaseAuth mAuth;



    public void register() {

        TextView register = findViewById(R.id.createAcc);

        register.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, RegisterScreen.class);

                LoginActivity.this.startActivity(i);

            }

        });



    }



    public void clickLogin() {

        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                EditText username = findViewById(R.id.userName);

                EditText password = findViewById(R.id.userPassword);

                try {
                    String userString = username.getText().toString();

                    String passString = password.getText().toString();

                    if(userString.isEmpty() || passString.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                    }else{
                        mAuth.signInWithEmailAndPassword(userString, passString)

                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                    @Override

                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){

                                            // sign in the user if successful

                                            Intent login = new Intent(LoginActivity.this, DashActivity.class);

                                            LoginActivity.this.startActivity(login);



                                        }else{

                                            // if sign in fails, tell the user why

                                            Log.w("signIn", "onComplete: failure to login", task.getException());

                                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                        }

                                    }

                                });
                    }


                }catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_LONG).show();
                }





            }

        });

    }



/*

    public String hash256(String password) throws NoSuchAlgorithmException {

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        byte[] passBytes = password.getBytes();

        byte[] passHash = sha256.digest(passBytes);



        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passHash.length; i++) {

            sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));

        }



        String generatedPassword = sb.toString();



        return generatedPassword;

    }

*/

    private Socket socket;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        try {
            socket = IO.socket("http://192.168.1.15:3000");
            socket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        CustomGoogleMapsClass mapsClass = new CustomGoogleMapsClass();



        boolean isSuccessFul = mapsClass.isServicesOk(this, LoginActivity.this);



        if (isSuccessFul == true) {

            mAuth = FirebaseAuth.getInstance();

            clickLogin();

            register();

        }



    }



    @Override

    public void onBackPressed() {

        //super.onBackPressed(); The reason for commenting this out was to prevent the user from going back to the loading screen.

    }

}