package com.mobdeve.ramos.isa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class savedTexts extends AppCompatActivity {

    Button home_btn1;
    ListView listView;
    ArrayList<Text> list;
    TextListAdapter adapter = null;
    DBHelper DB; //added to access DB directly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_texts);

        home_btn1 = (Button) findViewById(R.id.home_btn1);
        DB = new DBHelper(this); //added to access DB directly
        listView = (ListView) findViewById(R.id.list_view);
        list = new ArrayList<>();
        adapter = new TextListAdapter(this, R.layout.single_text,list);
        listView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = DB.getText("SpeechToText");
        list.clear();
        while(cursor.moveToNext()){
            String text = cursor.getString(1);
            String date = cursor.getString(2);

            list.add(new Text(text, date));
        }
        adapter.notifyDataSetChanged();

        home_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(savedTexts.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}