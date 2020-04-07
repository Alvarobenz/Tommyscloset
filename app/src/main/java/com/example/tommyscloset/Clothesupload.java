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

    // set itemType to top, bottom, shoe, or accessory
    public  void selectTop(){
        String itemType = "";
        Intent intent = new Intent(Clothesupload.this, uploadImage.class);
        intent.putExtra("top", itemType);
        startActivity(intent);
        finish();
    }

    public  void selectBottom(){
        String itemType = "";
        Intent intent = new Intent(Clothesupload.this, uploadImage.class);
        intent.putExtra("bottom", itemType);
        startActivity(intent);
        finish();
    }

    public  void selectShoe(){
        String itemType = "";
        Intent intent = new Intent(Clothesupload.this, uploadImage.class);
        intent.putExtra("shoe", itemType);
        startActivity(intent);
        finish();
    }

    public  void selectAcc(){
        String itemType = "";
        Intent intent = new Intent(Clothesupload.this, uploadImage.class);
        intent.putExtra("accessory", itemType);
        startActivity(intent);
        finish();
    }
}

