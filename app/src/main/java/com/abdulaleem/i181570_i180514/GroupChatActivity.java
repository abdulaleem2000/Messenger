package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abdulaleem.i181570_i180514.databinding.ActivityChatBinding;
import com.abdulaleem.i181570_i180514.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;


    ImageView back;

    GroupMessageAdapter adapter12;
    ArrayList<Messsage> messsages;

    FirebaseDatabase database;
    FirebaseStorage storage;
    String senderUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        senderUid = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        messsages = new ArrayList<>();

        adapter12 = new GroupMessageAdapter(this,messsages);
        binding.recycleView12.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView12.setAdapter(adapter12);
        database.getReference().child("group")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messsages.clear();

                        for(DataSnapshot dataSnapshot1 : snapshot.getChildren())
                        {

                            Messsage messsage = dataSnapshot1.getValue(Messsage.class);
                            messsage.setMessageId(dataSnapshot1.getKey());
                            messsages.add(messsage);


                        }
                        adapter12.notifyDataSetChanged();

                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgText = binding.editTextTextPersonName.getText().toString();
                Date date = new Date();
                Messsage messsage = new Messsage(msgText,senderUid,date.getTime());
                binding.editTextTextPersonName.setText("");

                database.getReference().child("group")
                        .push().setValue(messsage);
            }
        });
        binding.attatchment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,25);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 25){
            if(data!=null){
                if(data.getData()!=null){
                    Uri selectedImage = data.getData();
                    Calendar calendar = Calendar.getInstance();
                    StorageReference reference = storage.getReference().child("chats").child(calendar.getTimeInMillis()+ "");
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String filePath = uri.toString();
                                        String msgText = binding.editTextTextPersonName.getText().toString();

                                        Date date = new Date();

                                        Messsage messsage = new Messsage(msgText,senderUid,date.getTime());
                                        messsage.setMessage("photo");
                                        messsage.setImageUri(filePath);
                                        binding.editTextTextPersonName.setText("");

                                        database.getReference().child("group")
                                                .push().setValue(messsage);


                                       // Toast.makeText(GroupChatActivity.this,filePath,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }

}