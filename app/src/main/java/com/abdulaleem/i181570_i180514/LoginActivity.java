package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import database.MyHelper;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    ImageView login;
    TextView register;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        login = (ImageView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String pass = editTextPassword.getText().toString();
                if(email.equals("")||pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"Please Enter all the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   auth.signInWithEmailAndPassword(email,pass)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {

                               Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(LoginActivity.this,Home.class);


                               startActivity(intent);
                                finish();
                           }
                           else
                           {
                               Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(signupIntent);

            }
        });

    }
}