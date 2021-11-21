package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyHelper extends SQLiteOpenHelper {
    private static final String dbName = "Login.db";

    public MyHelper(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE users (_id integer PRIMARY KEY AUTOINCREMENT,email TEXT ,password TEXT, firstname TEXT, lastname TEXT, gender TEXT, bio TEXT)";
       // String sql1 = "create table image(img blob not null ,imgid integer not null, foreign key(_id) references users(_id))";
        db.execSQL("CREATE TABLE users (email TEXT primary key , password TEXT , firstname TEXT , lastname TEXT, gender TEXT, bio TEXT)");
       // db.execSQL(sql1);


    }
    public Boolean insertData(String email, String password, String fName, String lName, String gender, String bio)
    {
        System.out.print(fName);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",email);
        values.put("password",password);
        values.put("firstname",fName);
        values.put("lastname",lName);
        values.put("gender",gender);
        values.put("bio",bio);




        long result = db.insert("users",null,values);



        //db.execSQL("INSERT INTO USERS(email,password,firstName,lastname,gender,bio) VALUES (email,password,fName,lName,gender,bio)");
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email=?" , new String[]{email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkEmailPasword(String email,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where email=? and password=?" , new String[]{email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
       // db.execSQL("drop table if exists image");
    }
}
