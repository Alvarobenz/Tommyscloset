package com.example.tommyscloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Clothesupload extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothesupload);


        Button buttonTop = findViewById(R.id.buttonTop);
        Button buttonBottom = findViewById(R.id.buttonBottom);
        Button buttonShoe = findViewById(R.id.buttonShoe);
        Button buttonAcc = findViewById(R.id.buttonAcc);

        buttonTop.setOnClickListener(this);
        buttonBottom.setOnClickListener(this);
        buttonShoe.setOnClickListener(this);
        buttonAcc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTop:
                selectTop();
                break;
            case R.id.buttonBottom:
                selectBottom();
                break;
            case R.id.buttonShoe:
                selectShoe();
                break;
            case R.id.buttonAcc:
                selectAcc();
                break;
        }
    }

    public  void selectTop(){
//        create item w/ unique code from firebase attached to user
  //              set itemType to 'Top'

        startActivity( new Intent(getApplicationContext(), uploadImage.class));
        finish();
    }

    public  void selectBottom(){
        startActivity( new Intent(getApplicationContext(), uploadImage.class));
        finish();
    }

    public  void selectShoe(){
        startActivity( new Intent(getApplicationContext(), uploadImage.class));
        finish();
    }

    public  void selectAcc(){
        startActivity( new Intent(getApplicationContext(), uploadImage.class));
        finish();
    }


    }

