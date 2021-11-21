package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.abdulaleem.i181570_i180514.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    FirebaseAuth mAuth;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bw);

        FragmentTransaction chatTrans = getSupportFragmentManager().beginTransaction();
        chatTrans.replace(R.id.content,new HomeFragment());
        chatTrans.commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.chat:
                        FragmentTransaction chatTrans = getSupportFragmentManager().beginTransaction();
                        chatTrans.replace(R.id.content,new HomeFragment());
                        chatTrans.commit();
                        break;
                    case R.id.contact:
                        FragmentTransaction contactTrans = getSupportFragmentManager().beginTransaction();
                        contactTrans.replace(R.id.content,new ContactsFragment());
                        contactTrans.commit();
                        break;
                    case R.id.call:
                        FragmentTransaction callTrans = getSupportFragmentManager().beginTransaction();
                        callTrans.replace(R.id.content,new CallFragment());
                        callTrans.commit();
                        break;
                }
                return true;
            }
        });

    }


}