package com.mobdeve.ramos.isa;

import androidx.annotation.NonNull;
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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

/*
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
*/
public class ImageCapture extends AppCompatActivity {

    DbBitmapUtility img_conv;

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    EditText imagename;
    Button launch_btn, next_btn, detect_btn;
    TextView text_display;
    String imagename_S;
    String usernametemp;

    ImageView lastcapture;
    Bitmap bitmap;

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
        detect_btn = (Button) findViewById(R.id.detect_btn);
        text_display = (TextView) findViewById(R.id.text_display);

        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username");


        //permission to open camera
        if (ContextCompat.checkSelfPermission(ImageCapture.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImageCapture.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        //end of permission to open camera

        //start of making the buttons enabled
            imagename.addTextChangedListener(new TextWatcher() { //enables button if there is an image name
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String imagenametemp = imagename.getText().toString().trim();

                launch_btn.setEnabled(!imagenametemp.isEmpty());
                next_btn.setEnabled(!imagenametemp.isEmpty());
                detect_btn.setEnabled(!imagenametemp.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //end of making the buttons enabled

        //Launches camera
        launch_btn.setOnClickListener(new View.OnClickListener() { //opens camera launcher
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });
        //end of launches camera

        //opens saved images
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageCapture.this, savedImages.class);
                intent.putExtra("username",usernametemp);
                startActivity(intent);
                finish();
            }
        });

        detect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_display.setText("HELLO");
            }
        });

    }
    public void detect(){
        TextRecognizer recogizer = new TextRecognizer.Builder(ImageCapture.this).build();

        Frame frame =  new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> sparseArray = recogizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();

            stringBuilder.append(str);
        }
        text_display.setText(stringBuilder);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){

            imagename_S = imagename.getText().toString(); // image name in string
            //save image to DB
             bitmap = (Bitmap) data.getExtras().get("data"); //getting the image

            Boolean insert = DB.insertImage(imagename_S,img_conv.getBytes(bitmap),usernametemp); //covert bitmap to byte array
            if(insert == true){
                Toast.makeText(ImageCapture.this, "Image Saved", Toast.LENGTH_SHORT).show();
            }
            // end of save image to DB

            //get image from DB
            Cursor cursor = DB.getimage(imagename_S,usernametemp);
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