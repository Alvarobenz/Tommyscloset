package com.example.tommyscloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Dialog;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    EditText mTextEmail;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //ViewGroup progressView;
    //protected boolean isProgressShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextEmail = findViewById(R.id.edittext_email);
        mTextPassword = findViewById(R.id.edittext_password);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect Login", Toast.LENGTH_SHORT).show();

                }
            }
        };

        mButtonLogin = findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();

                if (email.isEmpty()) { //if Email field is empty
                    mTextEmail.setError("Please enter your email");
                    mTextEmail.requestFocus();

                } else if (pwd.isEmpty()) { //if Password field is empty
                    mTextPassword.setError("Please enter your password");
                    mTextPassword.requestFocus();

                } else if ((pwd.isEmpty() && email.isEmpty())) { //if all fields are empty
                    Toast.makeText(MainActivity.this, "The Fields Are Empty", Toast.LENGTH_SHORT).show();
                }
                /*  if all fields are filled and Password matches Cnf Password
                    create user from Email and Pwd and in case it doesn't go through then let user know about
                    registration error. Let user know if successful registration.
                */
                 else if ( !(pwd.isEmpty() && email.isEmpty()) ) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else { // General error outside known tests for user
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                } else { // General error outside known tests for user
                    Toast.makeText(MainActivity.this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


}
