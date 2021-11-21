package com.abdulaleem.i181570_i180514;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contacts> contacts;
    public  ContactsAdapter(List<Contacts> contacts){this.contacts=contacts;};
    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts,parent,false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri resource = contacts.get(position).getImageView();
        String name=contacts.get(position).getTextView();
        String phNumber = contacts.get(position).getPhone();

        holder.setData1(resource,name,phNumber);
    }

    @Override
    public int getItemCount() {

        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageContacts;
        private TextView nameContacts;
        private TextView phNum;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageContacts = view.findViewById(R.id.imageContacts);
            nameContacts = view.findViewById(R.id.nameContacts);
            phNum = view.findViewById(R.id.phNum);
        }
        public void setData1(Uri resource, String name1, String phone1) {
            imageContacts.setImageURI(resource);
            nameContacts.setText(name1);
            phNum.setText(phone1);

        }
    }

}
