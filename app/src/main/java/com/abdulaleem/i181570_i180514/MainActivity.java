package com.abdulaleem.i181570_i180514;

import static android.os.Handler.*;

import static androidx.core.os.HandlerCompat.postDelayed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import database.MyHelper;

public class MainActivity extends AppCompatActivity {
    static MyHelper db;
    FirebaseAuth mAuth;


    static List<Contacts> contacts = new ArrayList<>();
    static  List<ItemsClass> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = new MyHelper(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread thread = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {

                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null)
                    {
                        Intent intent = new Intent(MainActivity.this,Home.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };thread.start();


    }
}