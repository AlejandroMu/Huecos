package com.example.huecoscolombia;

import android.content.Intent;
import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListUsersAdminAdapter extends BaseAdapter {

    private ArrayList<Person> persons;
    FirebaseDatabase db;

    public ListUsersAdminAdapter() {
        persons = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.row_list_users_admin, null);
        TextView name = v.findViewById(R.id.row_list_users_admin_name_tv);
        ImageButton deleteUser = v.findViewById(R.id.row_list_users_admin_delete_btn);
        ImageButton editUser = v.findViewById(R.id.row_list_users_admin_edit_btn);
        ImageButton messageUser = v.findViewById(R.id.row_list_users_admin_message_btn);

        name.setText(persons.get(i).getName());

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HuecosColombiaApp.getContext(),EditProfileAdminActivity.class);
                intent.putExtra("person", persons.get(i));
                HuecosColombiaApp.getContext().startActivity(intent);
            }
        });
        messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person aux = persons.get(i);
                Intent intent = new Intent(HuecosColombiaApp.getContext(),MessageActivity.class);
                intent.putExtra("user",aux);
                intent.putExtra("state","admin");
                HuecosColombiaApp.getContext().startActivity(intent);
            }
        });

        return v;
    }

    public void loadPersons() {
        db.getReference().child("persons").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            Person person = child.getValue(Person.class);
                            persons.add(person);
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
