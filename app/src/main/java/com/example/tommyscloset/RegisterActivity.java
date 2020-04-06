package com.example.tommyscloset;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    EditText mTextUsername;
    Button mButtonRegister;
    TextView mTextViewLogin;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    String userID;

    private static final String TAG = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth   = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();


        progressBar = findViewById(R.id.progressBar);
        mTextEmail      = findViewById(R.id.edittext_email);
        mTextPassword   = findViewById(R.id.edittext_password);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);
        mTextViewLogin = findViewById(R.id.textview_login);
        mTextUsername = findViewById(R.id.edittext_Username);
        mButtonRegister = findViewById(R.id.button_register);


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mTextEmail.getText().toString().trim();
                final String userN = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) { //if Email field is empty
                    mTextEmail.setError("Please enter your email R1");
                    mTextEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(userN)) { //if Email field is empty
                    mTextEmail.setError("Please enter your name");
                    mTextEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pwd)) { //if Password field is empty
                    mTextPassword.setError("Please enter your password R2");
                    mTextPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(cnf_pwd)) { //if Cnf Password field is empty
                    mTextCnfPassword.setError("Please confirm your password R3");
                    mTextCnfPassword.requestFocus();
                    return;
                }



                if ((pwd.isEmpty() && email.isEmpty() && cnf_pwd.isEmpty() && userN.isEmpty())) { //if all fields are empty
                    Toast.makeText(RegisterActivity.this, "The Fields Are Empty R4", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ( !(pwd.equals(cnf_pwd)) ) { //if Passwords dont match
                    mTextPassword.setError("Passwords do not match R5");
                    mTextCnfPassword.setError("Passwords do not match R6");
                    mTextPassword.requestFocus();
                    mTextCnfPassword.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Password is not matching R7", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mTextPassword.length() < 6) { // if passwords is less then 6 char
                    mTextPassword.setError("Password must be >= 6 characters R8");
                    return;
                }

                if (mFirebaseAuth.getCurrentUser() != null){
                    Toast.makeText(RegisterActivity.this, "Already Logged in 1", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                // Set progress bar to visible while registration to firebase runs
                progressBar.setVisibility(View.VISIBLE);

                /*  if all fields are filled and Password matches Cnf Password and >= 6 char
                    create user from Email and Pwd and incase it doesn't go through then let user know about
                    registration error. Let user know if successful registration.
                */

                mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "You have registered R10", Toast.LENGTH_SHORT).show();

                            userID = mFirebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", userN);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Unsuccessful, Please Try Again: R11" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        // Login clickable text to go back to login screen
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}