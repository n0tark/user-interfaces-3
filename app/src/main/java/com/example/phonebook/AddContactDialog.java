package com.example.phonebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class AddContactDialog extends DialogFragment {

    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private Button buttonAdd;
    private AddContactListener listener;

    public AddContactDialog(AddContactListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_contact, container, false);
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextPhoneNumber = view.findViewById(R.id.edit_text_phone_number);
        buttonAdd = view.findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();
            listener.onAddContact(name, phoneNumber);
            dismiss();
        });

        return view;
    }

    public interface AddContactListener {
        void onAddContact(String name, String phoneNumber);
    }
}
