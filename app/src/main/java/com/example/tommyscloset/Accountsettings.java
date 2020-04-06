package com.example.tommyscloset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Accountsettings extends AppCompatActivity {

    TextView fullName, email;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    Button buttonLogout, buttonDelete, buttonReset;
    ProgressBar progressBar;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);


        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonReset = findViewById(R.id.buttonReset);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    fullName.setText(documentSnapshot.getString("fName"));
                }
                if (documentSnapshot != null) {
                    email.setText(documentSnapshot.getString("email"));
                }
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                startActivity( new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Accountsettings.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will result in completely removing your " +
                        "account from the system and you won't be able to access the app");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
                        fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()){
                                    startActivity( new Intent(getApplicationContext(), MainActivity.class));
                                    Toast.makeText(getApplicationContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
                passwordResetDialog.setView(resetEmail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get email and send reset link
                                String mail = resetEmail.getText().toString();
                                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Reset Link Sent To Your Email M7", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error! No Link Sent M8" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        }
                );

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });
    }
}
