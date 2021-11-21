package com.abdulaleem.i181570_i180514;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abdulaleem.i181570_i180514.databinding.ActivityInsertContactsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InsertContacts extends AppCompatActivity {
    ActivityInsertContactsBinding binding;

    Uri selectedImage;
    private static final int IMAGE_PICK_CODE = 1002;
    private static final int PERMISSION_CODE = 1003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.floatingAddConBtn.setOnClickListener(new View.OnClickListener() {
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
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertContacts.this,Home.class);
                intent.putExtra("contactName",binding.addNameCon.getText().toString());
                intent.putExtra("contactNum",binding.editTextnumberCon.getText().toString());
                intent.putExtra("contactImage",selectedImage);
                startActivity(intent);
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
            binding.photoAdd.setImageURI(uri);
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
}