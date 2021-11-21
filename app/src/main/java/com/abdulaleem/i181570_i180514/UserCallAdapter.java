package com.abdulaleem.i181570_i180514;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulaleem.i181570_i180514.databinding.RowCallBinding;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class UserCallAdapter extends RecyclerView.Adapter<UserCallAdapter.UserViewHolder> {
    Context context;
    ArrayList<User> users;
    ImageView video,voice;
    UserCallAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_call,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.binding.nameCalls.setText(user.getLname());
        holder.binding.phNumCalls.setText(user.getEmail());
        Glide.with(context).load(user.getProfileImage())
                .placeholder(R.drawable.ic_baseline_photo_camera_24)
                .into(holder.binding.imageContacts);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                video = popupView.findViewById(R.id.video);
                voice = popupView.findViewById(R.id.voice);
                // create the popup window
                int width = 350;
                int height = 150;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("video pressed");
                    }
                });
                voice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //((VoiceCallActivity)context).callUser(user);
                        Intent intent = new Intent(context,VoiceCallActivity.class);
                        intent.putExtra("user", user);
                        //intent.putExtra("lName",user.getLname());
                        //intent.putExtra("profile",user.getProfileImage());
                        context.startActivity(intent);
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        RowCallBinding binding;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowCallBinding.bind(itemView);
        }
    }
}
