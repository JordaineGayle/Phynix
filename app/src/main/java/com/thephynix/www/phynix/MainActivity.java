package com.thephynix.www.phynix;



import android.Manifest;

import android.content.Intent;

import android.content.pm.PackageManager;

import android.database.sqlite.SQLiteDatabase;

import android.os.Handler;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.view.Window;

import android.view.WindowManager;

import android.view.accessibility.AccessibilityManager;

import android.view.animation.Animation;

import android.view.animation.AnimationSet;

import android.view.animation.RotateAnimation;

import android.view.animation.ScaleAnimation;

import android.widget.ImageView;

import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;



import java.io.BufferedReader;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;





public class MainActivity extends AppCompatActivity {



    Handler mhandler;

    private FirebaseAuth mAuth;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);



        ImageView logo = findViewById(R.id.logo);



        ScaleAnimation anim = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.2f);

        anim.setDuration(6000);

        anim.setInterpolator(this, android.R.interpolator.accelerate_decelerate);



        RotateAnimation anim2 = new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );

        anim2.setDuration(6000);

        anim2.setInterpolator(this, android.R.interpolator.accelerate_decelerate);



        AnimationSet set = new AnimationSet(true);

        set.addAnimation(anim);

        set.addAnimation(anim2);



        logo.startAnimation(set);





        mhandler = new Handler();

        mhandler.postDelayed(new Runnable() {

            @Override

            public void run() {



                //check if the user is logged in

                mAuth = FirebaseAuth.getInstance();



                FirebaseUser user = mAuth.getCurrentUser();



                if (user == null){

                    Intent login = new Intent(MainActivity.this, LoginActivity.class);

                    MainActivity.this.startActivity(login);

                }else{

                    Intent login = new Intent(MainActivity.this, LoginActivity.class);

                    MainActivity.this.startActivity(login);

                    //Toast.makeText(getApplicationContext(), "waiting on main screen", Toast.LENGTH_LONG).show();

                }





            }

        }, 5000);





    }







}