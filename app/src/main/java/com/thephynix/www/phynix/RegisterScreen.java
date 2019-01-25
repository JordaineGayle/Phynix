package com.thephynix.www.phynix;



import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.annotation.Nullable;

import android.support.constraint.ConstraintLayout;

import android.support.constraint.ConstraintSet;

import android.support.v7.app.AppCompatActivity;

import android.transition.ChangeBounds;

import android.transition.Transition;

import android.transition.TransitionManager;

import android.util.Log;

import android.view.View;

import android.view.Window;

import android.view.WindowManager;

import android.view.animation.Animation;

import android.view.animation.AnimationUtils;

import android.view.animation.AnticipateInterpolator;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.auth.User;



import org.w3c.dom.Text;



import java.util.ArrayList;

import java.util.HashMap;



/* Created by Jordaine Gayle - Encapsulation Coding Style Date 29/08/2018 */



public class RegisterScreen extends AppCompatActivity {



    private boolean isEmpty;

    private EditText username, password, fname, lname, email, tele;

    private Button returnToLogin,saveInfo;//private buttons, edit texts, text views

    private HashMap<String, String> UserInfo = new HashMap<>();

    private ArrayList<Integer> resArry = new ArrayList<Integer>();//private arraylust to hold buttons etc.

    public  boolean finalCheck = false;



    private FirebaseAuth mAuth;

    private FirebaseFirestore db;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration);

        _init();



    }



    private void _init(){

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        populatingArrayList();

        buttonAndTextCreation();

        returnTOloginScreen();

        //HashMap<String, Object> map = saveInfoGenerateHasMap();

    }



    private void returnTOloginScreen(){

        returnToLogin.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                returnIntent();

            }

        });

    }



    private void returnIntent(){

        Intent intent = new Intent(RegisterScreen.this, LoginActivity.class);

        RegisterScreen.this.startActivity(intent);

    }





    private void buttonAndTextCreation(){

        username = findViewById(convertRes(0));

        password = findViewById(convertRes(1));

        fname = findViewById(convertRes(2));

        lname = findViewById(convertRes(3));

        email = findViewById(convertRes(4));

        tele = findViewById(convertRes(5));

        saveInfo = findViewById(convertRes(7));

        returnToLogin = findViewById(convertRes(6));

    }





    int convertRes(int resNum){

        int o = resArry.get(resNum);

        return o;

    }



    private void populatingArrayList(){

        resArry.add(0, R.id.username);

        resArry.add(1, R.id.password);

        resArry.add(2, R.id.fname);

        resArry.add(3, R.id.lname);

        resArry.add(4, R.id.email);

        resArry.add(5, R.id.tele);

        resArry.add(6, R.id.returnToLogin);

        resArry.add(7, R.id.saveInfo);

    }



    public void saveInfoGenerateHasMap(View view){

        String emailtext = email.getText().toString();

        if(emailtext.isEmpty() || password.getText().toString().isEmpty() || (password.getText().toString().length() < 6) ){

            isEmpty = true;

        }else{

            UserInfo.put("Email", email.getText().toString());

            UserInfo.put("Password", password.getText().toString());

            UserInfo.put("Username", username.getText().toString());

            UserInfo.put("Firstname", fname.getText().toString());

            UserInfo.put("Lastname", lname.getText().toString());

            UserInfo.put("Telephone", tele.getText().toString());

            isEmpty = false;

        }

        if(isEmpty == true){

            Toast.makeText(RegisterScreen.this, "Either Email Address or Password is empty! or Password is ot long enough", Toast.LENGTH_LONG).show();

        }else {

            createUser(UserInfo);

        }

    }



    public void createUser(final HashMap map){



        String email = map.get("Email").toString();

        String password = map.get("Password").toString();



        if (map.equals(null)){

            Toast.makeText(getApplicationContext(), "the hashmap is null", Toast.LENGTH_LONG).show();

        }else{

            mAuth.createUserWithEmailAndPassword(email, password)

                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override

                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){



                                String uid = mAuth.getCurrentUser().getUid();



                                updateUserData(map, uid);



                            }else{

                                // creating user failed

                                Toast.makeText(getApplicationContext(), "creating account failed", Toast.LENGTH_LONG).show();

                            }





                        }

                    });

        }

    }



    public void updateUserData(HashMap map, String UID){



        db.collection("user").document(UID).set(map)

                .addOnSuccessListener(new OnSuccessListener() {

                    @Override

                    public void onSuccess(Object o) {

                        DashActivity();

                        Toast.makeText(RegisterScreen.this, "Update to database successful", Toast.LENGTH_LONG).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {

            @Override

            public void onFailure(@NonNull Exception e) {

                Toast.makeText(RegisterScreen.this, "Update "+e.getMessage(), Toast.LENGTH_LONG).show();

                //finalCheck = false;



            }

        });



    }





    public  void DashActivity(){

        Intent intent = new Intent(RegisterScreen.this,DashActivity.class);

        RegisterScreen.this.startActivity(intent);

    }







    @Override

    public void onBackPressed() {

        super.onBackPressed();

        Toast.makeText(this, "returning to login screen", Toast.LENGTH_LONG).show();

        returnTOloginScreen();

    }

}