package com.example.phonebook;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AddContactDialog.AddContactListener {

    private RecyclerView recyclerViewContacts;
    private List<Model.Contact> contacts = new ArrayList<>();
    private ContactAdapter adapter;
    private TextView textViewLogin;
    private TextView textViewRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewContacts = findViewById(R.id.recycler_view_contacts);
        recyclerViewContacts = findViewById(R.id.recycler_view_contacts);

        adapter = new ContactAdapter(contacts, new ContactAdapter.OnContactClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Видалення контакту під позицією position
                contacts.remove(position);
                // Оновлення RecyclerView
                adapter.notifyItemRemoved(position);
            }
        });
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);

        // Отримання та відображення даних при створенні активності
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getAllData();

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contacts.clear();
                    contacts.addAll(response.body().getContacts());
                    adapter.notifyDataSetChanged();

                    // Перевірка на відсутність контактів
                    if (contacts.isEmpty()) {
                        // Показати повідомлення про відсутність контактів
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.text_view_empty).setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        // Приховати повідомлення про відсутність контактів
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.text_view_empty).setVisibility(View.GONE);
                            }
                        });
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response or body is null");
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });


        // Кнопка "Додати контакт"
        Button buttonAddContact = findViewById(R.id.button_add_contact);
        buttonAddContact.setOnClickListener(v -> {
            showAddContactDialog();
        });
    }

    private void showAddContactDialog() {
        AddContactDialog dialog = new AddContactDialog(this);
        dialog.show(getSupportFragmentManager(), "addContactDialog");
    }

    @Override
    public void onAddContact(String name, String phoneNumber) {
        // Додавання нового контакту
        Model.Contact newContact = new Model.Contact();
        newContact.setName(name);
        newContact.setPhone_number(phoneNumber);
        addContact(newContact);
    }


    private void addContact(Model.Contact contact) {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Void> call = methods.addContact(contact);

        Log.d(TAG, "Adding contact: " + contact.getName() + ", " + contact.getPhone_number()); // Додати цей рядок

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    contacts.add(contact);
                    adapter.notifyItemInserted(contacts.size() - 1);
                } else {
                    Log.e(TAG, "Unsuccessful add contact request");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Failed to add contact: " + t.getMessage());
            }
        });
    }
//delete
/*public void deleteContact(int position) {
    Model.Contact contact = contacts.get(position);
    Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
    Call<Void> call = methods.deleteContact(contact.getId());

    call.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                contacts.remove(position);
                notifyItemRemoved(position);
            } else {
                Log.e(TAG, "Unsuccessful delete request");
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e(TAG, "Failed to delete contact: " + t.getMessage());
        }
    });
}*/


}
