package com.example.huecoscolombia;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Role;
import com.example.huecoscolombia.Model.entity.User;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button messages, photos, editProfile;
    private ImageView picture;
    private TextView name;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        messages = view.findViewById(R.id.fr_profile_messages_btn);
        photos = view.findViewById(R.id.fr_profile_photos_btn);
        editProfile = view.findViewById(R.id.fr_profile_edit_profile_btn);;
        picture = view.findViewById(R.id.fr_profile_picture_img);
        name = view.findViewById(R.id.fr_profile_name_tv);
        getCurrentUser();
        messages.setOnClickListener(
                view1 -> {
                    Intent intent = new Intent(view1.getContext(), MessageActivity.class);
                    intent.putExtra("dest", Role.ADMIN.toString());
                    startActivity(intent);
                }
        );
        editProfile.setOnClickListener(
                view12 -> {
                    Intent intent = new Intent(view12.getContext(), EditProfileActivity.class);
                    startActivity(intent);
                }
        );
        photos.setOnClickListener(
                view13 -> {
                    Intent intent = new Intent(view13.getContext(), MyNewsActivity.class);
                    startActivity(intent);
                }
        );
        return view;
    }

    public void getCurrentUser(){
        String email  = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseDatabase.getInstance().getReference()
                .child("persons")
                .child(email.replace(".","_"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        Person person = dataSnapshot.getValue(Person.class);
                        getPerson(person);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void getPerson(Person  p) {
        name.setText(p.getName());
    }

}
