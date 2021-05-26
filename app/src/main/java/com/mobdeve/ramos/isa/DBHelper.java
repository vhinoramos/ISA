package com.mobdeve.ramos.isa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper( Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT )");
        MyDB.execSQL("create Table images(imagename TEXT,  image BLOB )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists images");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username); //0
        contentValues.put("password", password); //1
        long result = MyDB.insert("users", null, contentValues);
        if(result ==-1) return false;
        else
            return true;
    }

    public Boolean insertImage(String imagename, byte[] image){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagename", imagename);
        contentValues.put("image", image);
        long result = MyDB.insert("images", null, contentValues); //insert to images table
        if(result ==-1) return false;
        else
            return true;
    }

    public Cursor getimage(String imagename){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from images where imagename = ?", new String[] {imagename});

        return cursor;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0 )
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount() > 0 )
            return true;
        else
            return false;
    }

    public Cursor getUserCred(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        return cursor;
    }

    public Boolean updateCreds(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(username.isEmpty()){// update password only
            contentValues.put("password", password); //1
            MyDB.update("users",contentValues,"username = ?", new String[] {username});
            return true;
        }
        else{//update everything
            contentValues.put("username", username); //0
            contentValues.put("password", password); //1
             MyDB.update("users",contentValues,"username = ?", new String[] {username});
             return true;
        }
    }


}
