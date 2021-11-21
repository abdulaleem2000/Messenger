package com.abdulaleem.i181570_i180514;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulaleem.i181570_i180514.databinding.RowContactsBinding;
import com.abdulaleem.i181570_i180514.databinding.RowConversationBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserContactsAdapter extends RecyclerView.Adapter<UserContactsAdapter.UserViewHolder>{
    Context context;
    ArrayList<User> users;
    ArrayList<User> selectedUsers;
    boolean isSelected=false;
    UserContactsAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.nameContacts.setText(user.getLname());
        holder.binding.phNum.setText(user.getEmail());
        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .into(holder.binding.imageContacts);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isSelected=true;

                    holder.itemView.setBackgroundColor(Color.BLUE);
                    //selectedUsers.add(user);



                return true;
            }




        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isSelected){

                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

                }
                isSelected=false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        RowContactsBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowContactsBinding.bind(itemView);
        }
    }
}
