package com.abdulaleem.i181570_i180514;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulaleem.i181570_i180514.databinding.RowContactsBinding;
import com.abdulaleem.i181570_i180514.databinding.RowConversationBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<User> userList;
    Context context;
    public UsersAdapter(Context context, List<User> userList){this.userList=userList;
    this.context = context;};
    @NonNull
    @Override

    public UsersAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conversation,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        String senderId = FirebaseAuth.getInstance().getUid();
        String senderRoom = senderId + user.getUid();
        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                            //long time = snapshot.child("lastMsgTime").getValue(Long.class);
                            holder.binding.msg.setText(lastMsg);
                            //holder.binding.time.setText(DateFormat.getTimeInstance().format(new Date(time)));
                        }
                        else {
                            holder.binding.msg.setText("Tap to Chat");
                            holder.binding.time.setText("00:00");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        holder.binding.textView.setText(user.getLname());

        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .into(holder.binding.imageView2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ChatActivity.class);
                intent.putExtra("uid",user.getUid());
                intent.putExtra("lName",user.getLname());
                intent.putExtra("profile",user.getProfileImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size() ;
    }




    public class UserViewHolder extends RecyclerView.ViewHolder {

        RowConversationBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowConversationBinding.bind(itemView);
        }
    }


}

