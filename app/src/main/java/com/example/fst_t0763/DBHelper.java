package com.example.fst_t0763;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "new_db";
    public static final String TABLE_NAME = "fst1_user";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE fst1_user(USERNAME TEXT UNIQUE,FIRSTNAME TEXT, LASTNAME TEXT ," +
                " PASSWORD TEXT, EMAIL TEXT, MOBILE TEXT UNIQUE, PROFILE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fst1_user ");
    }

    public boolean checkUser(String username, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT USERNAME,MOBILE FROM fst1_user WHERE USERNAME=? AND mobile=?", new String[]{username, mobile});
        if (cursor.getCount() > 1)
            return true;
        else return false;

    }




    public boolean checkUname(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT USERNAME FROM fst1_user WHERE USERNAME=?", new String[]{username});
        if (cursor.getCount() != 0)
            return true;
        else return false;

    }


    public boolean checkUphone(String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MOBILE FROM fst1_user WHERE mobile=?", new String[]{mobile});
        if (cursor.getCount() != 0)
            return true;
        else return false;

    }


//
//    public boolean checktable(){
//       SQLiteDatabase db=this.getWritableDatabase();
//
//       db.execSQL("SELECT * FROM FST_USERS");
//  }

    public boolean regInsertUser(String firstname, String lastname, String username, String password, String email, String phone, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USERNAME", username);
        values.put("FIRSTNAME", firstname);
        values.put("LASTNAME", lastname);
        values.put("EMAIL", email);
        values.put("PASSWORD", password);
        values.put("MOBILE", phone);
        values.put("PROFILE", img);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean checkuser(String mobile, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  MOBILE,PASSWORD  FROM fst1_user WHERE MOBILE=? AND PASSWORD=?", new String[]{mobile, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getUdata(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT USERNAME , EMAIL , PROFILE FROM fst1_user WHERE MOBILE=?", new String[]{phone});
        return c;
    }

}