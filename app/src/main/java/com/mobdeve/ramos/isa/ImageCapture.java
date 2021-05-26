package com.mobdeve.ramos.isa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageCapture extends AppCompatActivity {

    DbBitmapUtility img_conv;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText imagename;
    Button launch_btn, next_btn;
    String imagename_S;

    ImageView lastcapture;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);


        DB = new DBHelper(this);
        imagename  = (EditText) findViewById(R.id.imagename_et) ;
        lastcapture = (ImageView) findViewById(R.id.lastcapture); //image display
        launch_btn = (Button) findViewById(R.id.launch_btn); //launch camera button
        next_btn = (Button) findViewById(R.id.next_btn); // next button


        //permission to open camera
        if (ContextCompat.checkSelfPermission(ImageCapture.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImageCapture.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        //end of permission to open camera


            imagename.addTextChangedListener(new TextWatcher() { //enables button if there is an image name
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String imagenametemp = imagename.getText().toString().trim();

                launch_btn.setEnabled(!imagenametemp.isEmpty());
                next_btn.setEnabled(!imagenametemp.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Launches camera
        launch_btn.setOnClickListener(new View.OnClickListener() { //opens camera launcher
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        //end of launches camera
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            imagename_S = imagename.getText().toString(); // image name in string
            //save image to DB
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Boolean insert = DB.insertImage(imagename_S,img_conv.getBytes(bitmap)); //covert bitmap to byte array
            if(insert == true){
                Toast.makeText(ImageCapture.this, "Image Saved", Toast.LENGTH_SHORT).show();
            }
            // end of save image to DB

            //get image from DB
            Cursor cursor = DB.getimage(imagename_S);
            cursor.moveToFirst();
            byte[] imagetemp = cursor.getBlob(1);
            cursor.close();
            // end of get image from DB

            //convert image to display
            Bitmap bitmapimage = BitmapFactory.decodeByteArray(imagetemp, 0, imagetemp.length); //convert image for displays
            lastcapture.setImageBitmap(bitmapimage); //bitmap image //optional


        }
    }
}