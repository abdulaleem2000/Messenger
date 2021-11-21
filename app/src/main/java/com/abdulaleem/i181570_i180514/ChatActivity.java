package com.abdulaleem.i181570_i180514;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abdulaleem.i181570_i180514.databinding.ActivityChatBinding;
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
import java.util.Vector;

public class ChatActivity extends AppCompatActivity {
    ImageView back;
    ActivityChatBinding binding;
    MessageAdapter adapter12;
    ArrayList<Messsage> messsages;
    String senderRoom, receiverRoom;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String recieverUid, senderUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());




        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        messsages = new ArrayList<>();


        binding.recycleView12.setLayoutManager(new LinearLayoutManager(this));


        recieverUid = getIntent().getStringExtra("uid");
        String lName = getIntent().getStringExtra("lName");
        String profile = getIntent().getStringExtra("profile");
        System.out.println(123);

        senderUid = FirebaseAuth.getInstance().getUid();
        senderRoom = senderUid+recieverUid;
        receiverRoom = recieverUid+senderUid;
        adapter12 = new MessageAdapter(this,messsages,senderRoom,receiverRoom);
        binding.recycleView12.setAdapter(adapter12);
        System.out.println(senderUid);
        database.getReference().child("presence").child(recieverUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String status = snapshot.getValue(String.class);
                    if(!status.isEmpty()){
                        if(status.equals("Offline")){
                            binding.status.setVisibility(View.GONE);
                        }
                        else {
                            binding.status.setText(status);
                            binding.status.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("chats")
                .child(senderRoom)
                .child("messsages")
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


        back = (ImageView) findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActivity.this,Home.class);
                intent.putExtra("uid",recieverUid);
                intent.putExtra("name",lName);
                intent.putExtra("profile",profile);
                startActivity(intent);
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String msgText = binding.editTextTextPersonName.getText().toString();
                Date date = new Date();
                Messsage messsage = new Messsage(msgText,senderUid,date.getTime());
                binding.editTextTextPersonName.setText("");

                HashMap<String,Object> lastMsgObj = new HashMap<>();
                lastMsgObj.put("lastMsg",messsage.getMessage());
                lastMsgObj.put("lastMsgTime",date.getTime());


                String randomKey = database.getReference().push().getKey();

                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messsages")
                        .child(randomKey)
                        .setValue(messsage)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(receiverRoom)
                                .child("messsages")
                                .child(randomKey)
                                .setValue(messsage).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

                    }
                });





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
        final Handler handler = new Handler();
        binding.editTextTextPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                database.getReference().child("presence").child(senderUid).setValue("typing...");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(userStoppedTyping,1000);
            }
            Runnable userStoppedTyping = new Runnable() {
                @Override
                public void run() {
                    database.getReference().child("presence").child(senderUid).setValue("Online");
                }
            };
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

                                        String randomKey = database.getReference().push().getKey();
                                        HashMap<String,Object> lastMsgObj = new HashMap<>();
                                        lastMsgObj.put("lastMsg",messsage.getMessage());
                                        lastMsgObj.put("lastMsgTime",date.getTime());

                                        database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                                        database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);
                                        database.getReference().child("chats")
                                                .child(senderRoom)
                                                .child("messsages")
                                                .push()
                                                .setValue(messsage)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        database.getReference().child("chats")
                                                                .child(receiverRoom)
                                                                .child("messsages")
                                                                .push()
                                                                .setValue(messsage).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                            }
                                                        });

                                                    }
                                                });

                                        Toast.makeText(ChatActivity.this,filePath,Toast.LENGTH_SHORT).show();
                                    }
                                });
                             }
                        }
                    });
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        String currentUid = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentUid).setValue("Online");
    }
    @Override
    public void onPause() {
        super.onPause();
        String currentUid = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentUid).setValue("Offline");


    }
}