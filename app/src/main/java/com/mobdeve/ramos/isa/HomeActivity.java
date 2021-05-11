package com.mobdeve.ramos.isa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                intent.putExtra("username",usernametemp);//pass username to profile for getting creds in DB
                startActivity(intent);
            }
        });

    }
}