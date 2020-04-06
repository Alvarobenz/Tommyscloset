package com.example.tommyscloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class uploadImage2 extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    ImageView selectedImage;
    Button cameraBtn1,galleryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_upload_image2);

        selectedImage = findViewById(R.id.displayImageView);
        cameraBtn1 = findViewById(R.id.cameraBtn1);
        galleryBtn = findViewById(R.id.galleryBtn);



    }
}
