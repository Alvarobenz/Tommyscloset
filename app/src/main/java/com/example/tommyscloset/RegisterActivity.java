package com.example.tommyscloset;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mTextEmail = findViewById(R.id.edittext_email);
        mTextPassword = findViewById(R.id.edittext_password);
        mTextCnfPassword = findViewById(R.id.edittext_cnf_password);

        mButtonRegister = findViewById(R.id.button_register);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCnfPassword.getText().toString().trim();

                if (email.isEmpty()) { //if Email field is empty
                    mTextEmail.setError("Please enter your email");
                    mTextEmail.requestFocus();

                } else if (pwd.isEmpty()) { //if Password field is empty
                    mTextPassword.setError("Please enter your password");
                    mTextPassword.requestFocus();

                } else if (cnf_pwd.isEmpty()) { //if Cnf Password field is empty
                    mTextCnfPassword.setError("Please confirm your password");
                    mTextCnfPassword.requestFocus();


                } else if ((pwd.isEmpty() && email.isEmpty() && cnf_pwd.isEmpty())) { //if all fields are empty
                    Toast.makeText(RegisterActivity.this, "The Fields Are Empty", Toast.LENGTH_SHORT).show();


                } else if (!(pwd.equals(cnf_pwd))) { //if Password and Cnf Password don't match
                    Toast.makeText(RegisterActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();

                /*  if all fields are filled and Password matches Cnf Password
                    create user from Email and Pwd and incase it doesn't go through then let user know about
                    registration error. Let user know if successful registration.
                */
                } else if ((!(pwd.isEmpty() && email.isEmpty() && cnf_pwd.isEmpty())) && (pwd.equals(cnf_pwd))) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else { // General error outside known tests for user
                    Toast.makeText(RegisterActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Login clickable text to go back to login screen
        mTextViewLogin = findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(RegisterActivity.this, MainActivity.class));

            }
        });
    }
}