package com.mobdeve.ramos.isa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
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
    DbBitmapUtility img_conv;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText imagename;
    Button save_btn, cancel_btn;
    String imagename_S;



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



    public void popuptext(){



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            //creates a pop up to input image name
            dialogBuilder = new AlertDialog.Builder(this);
            final View popuptext = getLayoutInflater().inflate(R.layout.popup, null);
            imagename = (EditText) popuptext.findViewById(R.id.imagename);
            save_btn = (Button) popuptext.findViewById(R.id.save_btn);

            imagename_S = imagename.getText().toString(); //this is the image name

            dialogBuilder.setView((popuptext));
            dialog = dialogBuilder.create();
            dialog.show();

            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();;
                }
            });

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Boolean insert = DB.insertImage(imagename_S,img_conv.getBytes(bitmap)); //covert bitmap to byte array
            if(insert == true){
                Toast.makeText(HomeActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
            }

            Cursor cursor = DB.getimage(imagename_S);
            cursor.moveToFirst();
            byte[] imagetemp = cursor.getBlob(1);
            cursor.close();

            Bitmap bitmapimage = BitmapFactory.decodeByteArray(imagetemp, 0, imagetemp.length);
            lastcapture.setImageBitmap(bitmapimage); //bitmap image
           // Bitmap image = img_conv.getImage(imagetemp); // convert byte[] to bitmap


        }
    }
}