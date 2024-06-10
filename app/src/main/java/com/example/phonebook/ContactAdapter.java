package com.example.phonebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Model.Contact> contacts;
    private OnContactClickListener listener;

    public ContactAdapter(List<Model.Contact> contacts, OnContactClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Model.Contact contact = contacts.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewPhoneNumber.setText(contact.getPhone_number());

        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public interface OnContactClickListener {
        void onDeleteClick(int position);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPhoneNumber;
        public Button buttonDelete;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPhoneNumber = itemView.findViewById(R.id.text_view_phone_number);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }

}
