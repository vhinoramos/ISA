package com.mobdeve.ramos.isa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    EditText username_profile, password_profile, repassword_profile;
    Button update_btn;
    DBHelper DB;
    String usernametemp;
    String user_hint, pass_hint, username, password,repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //connect password
        username_profile = (EditText) findViewById(R.id.username_profile);
        password_profile = (EditText) findViewById(R.id.password_profile);
        repassword_profile = (EditText) findViewById(R.id.repassword_profile);
        update_btn = (Button) findViewById(R.id.update_btn);
        DB = new DBHelper(this);

        Intent intent = getIntent();
        usernametemp = intent.getStringExtra("username"); //username of current user

        Cursor cursor = DB.getUserCred(usernametemp);
        while(cursor.moveToNext()){
             user_hint = cursor.getString(0);
             pass_hint = cursor.getString(1);
        }

        username_profile.setHint(user_hint);
        password_profile.setHint(pass_hint);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_profile.getText().toString();
                password = password_profile.getText().toString();
                repassword = repassword_profile.getText().toString();
                if(password.equals(repassword)){// if passwords are correct proceed to updating
                    Boolean isUpdated = DB.updateCreds(username,password);
                    if(isUpdated == true){
                        Toast.makeText(Profile.this,"Profile Updated. Please Sign In Again", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (Profile.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Profile.this,"Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(Profile.this,"Passwords Mismatch.", Toast.LENGTH_SHORT).show();

            }
        });




    }
}