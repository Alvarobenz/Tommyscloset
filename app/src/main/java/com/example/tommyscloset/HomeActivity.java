package com.example.tommyscloset;

import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; //might not need these two
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                openGoingout();
                break;
            case R.id.button2:
                openUpload();
                break;
            case R.id.button3:
                openViewall();
                break;
            case R.id.button4:
                openLaundry();
                break;
            case R.id.button5:
                openAccount();
                break;
        }
    }

    public  void openGoingout(){
        Intent intent = new Intent(this, Clothesselect.class  );
        startActivity(intent);
    }

    public  void openUpload(){
        Intent intent = new Intent(this, Clothesupload.class  );
        startActivity(intent);
    }

    public  void openViewall(){
        Intent intent = new Intent(this, Galleryview.class  );
        startActivity(intent);
    }

    public  void openLaundry(){
        Intent intent = new Intent(this, Laundry.class  );
        startActivity(intent);
    }

    public  void openAccount(){
        Intent intent = new Intent(this, Accountsettings.class  );
        startActivity(intent);
    }
}
