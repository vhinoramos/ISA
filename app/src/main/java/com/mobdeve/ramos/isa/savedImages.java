package com.mobdeve.ramos.isa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class savedImages extends AppCompatActivity {

    Button home_btn;
    GridView gridView;
    ArrayList<Image> list;
    ImageListAdapter adapter = null;
    DBHelper DB; //added to access DB directly
    String usernametemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        home_btn = (Button) findViewById(R.id.home_btn);
        DB = new DBHelper(this); //added to access DB directly
        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new ImageListAdapter(this, R.layout.single_image,list);
        gridView.setAdapter(adapter);
        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username");


        // get all data from sqlite
        Cursor cursor = DB.viewImages(usernametemp);
        list.clear();
        while(cursor.moveToNext()){
            String name = cursor.getString(0);
            byte[] image = cursor.getBlob(1);

            list.add(new Image(name, image));
        }
        adapter.notifyDataSetChanged();

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(savedImages.this, HomeActivity.class);
                intent.putExtra("username",usernametemp);
                startActivity(intent);
                finish();
            }
        });

    }
}