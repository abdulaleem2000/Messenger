package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import database.MyHelper;

public class SignupActivity extends AppCompatActivity {

    EditText editTextEmail1,editTextPassword1,confPass;
    ImageView signupBtn;
    TextView loginReg;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        signupBtn = (ImageView) findViewById(R.id.signupBtn);
        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        confPass = (EditText) findViewById(R.id.ConfPass1);
        loginReg = (TextView) findViewById(R.id.loginReg);

        loginReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail1.getText().toString();
                String pass = editTextPassword1.getText().toString();
                String confirmPass = confPass.getText().toString();
                if(pass.equals("")||email.equals("")||confirmPass.equals(""))
                {
                    Toast.makeText(SignupActivity.this,"Enter Email or Password",Toast.LENGTH_LONG).show();
                }
                else if(pass.equals(confirmPass))
                {
                    mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignupActivity.this,"User Created",Toast.LENGTH_LONG).show();
                                Intent registerIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                                registerIntent.putExtra("password",pass);
                                registerIntent.putExtra("userEmail",email);
                                startActivity(registerIntent);
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this,"Failed To Create User",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                    /*if(MainActivity.db.checkEmail(email))
                    {
                        Toast.makeText(SignupActivity.this,"Email Already Exist",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent registerIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                        registerIntent.putExtra("password",pass);
                        registerIntent.putExtra("userEmail",email);
                        startActivity(registerIntent);
                    }*/

                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Password and confirm password must match",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}