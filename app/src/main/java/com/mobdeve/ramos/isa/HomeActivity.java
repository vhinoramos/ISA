package com.mobdeve.ramos.isa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    TextView username_tv;
    ConstraintLayout profile_layout;
    String usernametemp;
    DBHelper DB;
    ImageView lastcapture;

    Button saved_images_btn, text_speech_btn, speech_text_btn, scan_btn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username_tv = (TextView) findViewById(R.id.username_tv);
        profile_layout = (ConstraintLayout) findViewById(R.id.profile_layout);
        lastcapture = (ImageView) findViewById(R.id.lastcapture);
        saved_images_btn = (Button) findViewById(R.id.saved_images_btn);
        text_speech_btn = (Button) findViewById(R.id.text_speech_btn);
        speech_text_btn = (Button) findViewById(R.id.speech_text_btn);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username");
        username_tv.setText(usernametemp);

        Button btn = (Button) findViewById(R.id.camera_btn);


        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                intent.putExtra("username", usernametemp);//pass username to profile for getting creds in DB
                startActivity(intent);
            }
        });

        speech_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, speechtotext.class); //anong meron?
                startActivity(intent);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ImageCapture.class);
                startActivity(intent);
            }
        });

        saved_images_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, savedImages.class);
                startActivity(intent);
            }
        });

        text_speech_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,texttospeech.class);
                startActivity(intent);
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (HomeActivity.this, scanner.class);
                startActivity(intent);
            }
        });



    }

}