package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import database.MyHelper;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    CircleImageView profile;
    FloatingActionButton floatBtn;
    TextView textEmail;
    EditText text1Name,textlName,bioT,genderT;
    Button create;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth auth;
    Uri selectedImage;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        profile = (CircleImageView) findViewById(R.id.photo);
        floatBtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        textEmail = (TextView) findViewById(R.id.textEmail);
        text1Name = (EditText) findViewById(R.id.editText1Name);
        textlName = (EditText) findViewById(R.id.editTextlName);
        bioT = (EditText) findViewById(R.id.editTextBio);
        genderT = (EditText) findViewById(R.id.editTextgender);
        create = (Button) findViewById(R.id.create);


        String email = getIntent().getStringExtra("userEmail");
        String passw = getIntent().getStringExtra("password");
        textEmail.setText(email);
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else
                    {
                        pickImageFromGallery();
                    }
                }
                else
                {
                    pickImageFromGallery();
                }

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = text1Name.getText().toString();
                String lName = textlName.getText().toString();
                String bio = bioT.getText().toString();
                String gender = genderT.getText().toString();
                if(fName.equals("")||lName.equals("")||bio.equals("")|gender.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please Enter all the fields", Toast.LENGTH_SHORT).show();
                }
                if(selectedImage!=null)
                {
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUri = uri.toString();
                                        String uid = auth.getUid();
                                        String email = auth.getCurrentUser().getEmail();
                                        User user = new User(uid,fName,lName,gender,bio,email,imageUri);

                                        database.getReference()
                                                .child("users")
                                                .child(uid)
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Intent intent = new Intent(RegisterActivity.this,Home.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    });


                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Please Insert Profile Picture", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void pickImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery();
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==IMAGE_PICK_CODE)
        {
            Uri uri = data.getData();
            profile.setImageURI(uri);
            selectedImage = data.getData();
            //String x = getPath(uri);
            /*if(db.insertImage(x))
            {
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Not Successful",Toast.LENGTH_SHORT).show();
            }*/

        }




    }
    public String getPath(Uri uri)
    {
        if(uri==null)
            return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null)
        {
            int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_EXPIRES);
            cursor.moveToFirst();
            return  cursor.getString(colIndex);
        }
        return  uri.getPath();
    }
}