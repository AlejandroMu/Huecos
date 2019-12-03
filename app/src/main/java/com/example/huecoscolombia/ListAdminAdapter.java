package com.example.huecoscolombia;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.huecoscolombia.Model.entity.ListAdmin;
import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Publication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ListAdminAdapter extends BaseAdapter {

    FirebaseDatabase db;

    private ArrayList<Person> persons;

    public ListAdminAdapter()  {
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
        View v = layoutInflater.inflate(R.layout.row_list_admin, null);
        TextView name = v.findViewById(R.id.row_list_admin_name_tv);
        name.setText(persons.get(i).getName());
        return v;
    }

    public void loadPersons(boolean toDo, boolean inProcess, boolean done){
        persons.clear();
        if(!toDo && !inProcess && !done){

        } else if(toDo && !inProcess && !done) {
            loadToDoPublications();
        } else if(!toDo && inProcess && !done) {
            loadInProcessPublications();
        } else if(!toDo && !inProcess && done) {
            loadDonePublications();
        } else if(toDo && inProcess && !done) {
            loadToDoPublications();
            loadInProcessPublications();
        } else if(!toDo && inProcess && done) {
            loadDonePublications();
            loadInProcessPublications();
        } else if(toDo && !inProcess && done) {
            loadToDoPublications();
            loadDonePublications();
        } else if(toDo && inProcess && done) {
            loadToDoPublications();
            loadInProcessPublications();
            loadToDoPublications();
        }
    }

    public void loadToDoPublications(){
        db.getReference().child("publications").child(Publication.TO_DO).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                            db.getReference().child("persons")
                                    .child(key).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Person person = dataSnapshot.getValue(Person.class);
                                            persons.add(person);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    }
                            );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public void loadInProcessPublications(){
        db.getReference().child("publications").child(Publication.IN_PROGRESS).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                            db.getReference().child("persons")
                                    .child(key).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Person person = dataSnapshot.getValue(Person.class);
                                            persons.add(person);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    }
                            );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    public void loadDonePublications(){
        db.getReference().child("publications").child(Publication.DONE).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                            db.getReference().child("persons")
                                    .child(key).addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Person person = dataSnapshot.getValue(Person.class);
                                            persons.add(person);
                                            notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    }
                            );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }
}
