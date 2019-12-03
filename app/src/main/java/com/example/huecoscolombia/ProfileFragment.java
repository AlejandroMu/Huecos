package com.example.huecoscolombia;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


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

        messages.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), MessageActivity.class);
                        startActivity(intent);
                    }
                }
        );
        editProfile.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                        startActivity(intent);
                    }
                }
        );
        photos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), MyNewsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        return view;
    }

}
