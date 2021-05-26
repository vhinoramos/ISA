package com.mobdeve.ramos.isa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView username_tv;
    ConstraintLayout profile_layout;
    String usernametemp;
    DBHelper DB;
    ImageView lastcapture;
    DbBitmapUtility img_conv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username_tv = (TextView) findViewById(R.id.username_tv);
        profile_layout = (ConstraintLayout) findViewById(R.id.profile_layout);
        lastcapture = (ImageView) findViewById(R.id.lastcapture);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username");
        username_tv.setText(usernametemp);

        Button btn = (Button) findViewById(R.id.camera_btn);
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                intent.putExtra("username", usernametemp);//pass username to profile for getting creds in DB
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //lastcapture.setImageBitmap(bitmap); //bitmap is the image captured to test

            Boolean insert = DB.insertImage(img_conv.getBytes(bitmap)); //covert bitmap to byte array
            Bitmap image = img_conv.getImage(DB.getimage(usernametemp, 0)); // convert byte[] to bitmap
            lastcapture.setImageBitmap(image); //getimage here

        }
    }
}