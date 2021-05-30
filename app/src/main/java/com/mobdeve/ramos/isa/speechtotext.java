package com.mobdeve.ramos.isa;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class speechtotext extends AppCompatActivity {

    protected  static  final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView tvText;
    String gotText;
    String date;

    DBHelper DB;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speechtotext);

        tvText = (TextView) findViewById(R.id.tvText);
        btnSpeak = (ImageButton) findViewById( R.id.btnSpeak);
        date = generateDateString();
        DB = new DBHelper(this);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
               try {
                   startActivityForResult(intent, RESULT_SPEECH);
                   tvText.setText("");
               } catch (ActivityNotFoundException e){
                   Toast.makeText(getApplicationContext(),"Your Device doesn't support Speech to Text",Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
               }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvText.setText(text.get(0));
                    gotText = text.get(0); //save the text to a global variable
                    //DB.insertText("SpeechToText",text.get(0),date);
                    savetext();
                }
                break;
        }
    }

    private void savetext() {
        Boolean insert = DB.insertText("SpeechToText",gotText, date);
        if(insert == true){
            Toast.makeText(speechtotext.this, "Speech Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private String generateDateString() {
        Locale philippineLocale = new Locale.Builder().setLanguage("en").setRegion("PH").build();
        return Calendar.getInstance(philippineLocale).getTime().toString();
    }


}