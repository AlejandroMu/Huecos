package com.example.huecoscolombia;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.huecoscolombia.Model.entity.Person;
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
        Button deleteUser = v.findViewById(R.id.row_list_users_admin_delete_btn);
        Button editUser = v.findViewById(R.id.row_list_users_admin_edit_btn);
        Button lockUser = v.findViewById(R.id.row_list_users_admin_lock_btn);

        name.setText(persons.get(i).getName());

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lockUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
