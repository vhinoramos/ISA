package com.mobdeve.ramos.isa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;

public class scanner extends AppCompatActivity {

    ImageView imageView;
    TextView detectedText;
    Button btn_detect, next_btn1,newimg_btn;
    String usernametemp;
    DBHelper DB;
    Bitmap bitmap;
    DbBitmapUtility img_conv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        imageView =  findViewById(R.id.img);
        detectedText = findViewById(R.id.detectedText);
        btn_detect =  findViewById(R.id.button_detected);
        next_btn1 = findViewById(R.id.next_btn1);
        newimg_btn = findViewById(R.id.newimg_btn);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username");

        btn_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detect();
            }
        });



        next_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scanner.this, savedImages.class);
                intent.putExtra("username", usernametemp);
                startActivity(intent);
            }
        });

    }

    public void savetodb(){
        Boolean insert = DB.insertImage(detectedText.getText().toString(),img_conv.getBytes(bitmap),usernametemp); //covert bitmap to byte array
        if(insert == true){
            Toast.makeText(scanner.this, "Image Saved", Toast.LENGTH_SHORT).show();
        }
        // end of save image to DB

    }

    public void detect(){
        TextRecognizer recogizer = new TextRecognizer.Builder(scanner.this).build();

        bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        Frame frame =  new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> sparseArray = recogizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();

            stringBuilder.append(str);
        }
        detectedText.setText(stringBuilder);

        savetodb();

    }

}