package com.abdulaleem.i181570_i180514;

import android.accounts.AbstractAccountAuthenticator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdulaleem.i181570_i180514.databinding.DeleteDialogBinding;
import com.abdulaleem.i181570_i180514.databinding.ItemRecvBinding;
import com.abdulaleem.i181570_i180514.databinding.ItemSentBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<Messsage> messsages;
    int ITEM_SENT = 1;
    int ITEM_RECV = 2;
    String senderRoom,receiverRoom;

    public MessageAdapter(Context context, ArrayList<Messsage> messsages,String senderRoom,String receiverRoom){
        this.context=context;
        this.messsages=messsages;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sent,parent,false);
            return new sentViewwHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recv,parent,false);
            return new recvViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Messsage messsage = messsages.get(position);
        if(FirebaseAuth.getInstance().getUid().equals(messsage.getSenderId()))
        {
            return ITEM_SENT;
        }
        else
            return ITEM_RECV;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messsage messsage = messsages.get(position);
        if(holder.getClass()==sentViewwHolder.class)
        {
            sentViewwHolder viewwHolder = (sentViewwHolder) holder;

            if(messsage.getMessage().equals("photo")){
                viewwHolder.binding.imageView2.setVisibility(View.VISIBLE);
                viewwHolder.binding.message.setVisibility(View.GONE);
                Glide.with(context).load(messsage.getImageUri()).into(viewwHolder.binding.imageView2);
            }


            viewwHolder.binding.message.setText(messsage.getMessage());

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog,null);
                    DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete Message")
                            .setView(binding.getRoot())
                            .create();
                    binding.everyone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            messsage.setMessage("This message is deleted");
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messsages")
                                    .child(messsage.getMessageId())
                                    .setValue(messsage);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messsages")
                                    .child(messsage.getMessageId())
                                    .setValue(messsage);
                            dialog.dismiss();
                        }
                    });

                    binding.forMe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child(messsage.getMessageId())
                                    .setValue(null);
                            dialog.dismiss();
                        }
                    });

                    binding.cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }
        else
        {
            recvViewHolder viewwHolder = (recvViewHolder) holder;
            if(messsage.getMessage().equals("photo")){
                viewwHolder.binding.imageView2.setVisibility(View.VISIBLE);
                viewwHolder.binding.message.setVisibility(View.GONE);
                Glide.with(context).load(messsage.getImageUri()).into(viewwHolder.binding.imageView2);
            }
            viewwHolder.binding.message.setText(messsage.getMessage());

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog,null);
                    DeleteDialogBinding binding = DeleteDialogBinding.bind(view);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete Message")
                            .setView(binding.getRoot())
                            .create();
                    binding.everyone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            messsage.setMessage("This message is deleted");
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messsages")
                                    .child(messsage.getMessageId())
                                    .setValue(messsage);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(receiverRoom)
                                    .child("messsages")
                                    .child(messsage.getMessageId())
                                    .setValue(messsage);
                            dialog.dismiss();
                        }
                    });

                    binding.forMe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference()
                                    .child("chats")
                                    .child(senderRoom)
                                    .child("messsages")
                                    .child(messsage.getMessageId())
                                    .setValue(null);
                            dialog.dismiss();
                        }
                    });

                    binding.cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return false;
                }
            });
        }





    }

    @Override
    public int getItemCount() {
        return messsages.size();
    }

    public class sentViewwHolder extends RecyclerView.ViewHolder{

        ItemSentBinding binding;
        public sentViewwHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSentBinding.bind(itemView);

        }
    }
    public class recvViewHolder extends RecyclerView.ViewHolder{
        ItemRecvBinding binding;
        public recvViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecvBinding.bind(itemView);
        }
    }
}

