package com.mobdeve.ramos.isa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView username_tv;
    ConstraintLayout profile_layout;
    String usernametemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username_tv = (TextView) findViewById(R.id.username_tv);
        profile_layout = (ConstraintLayout) findViewById(R.id.profile_layout);

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

}