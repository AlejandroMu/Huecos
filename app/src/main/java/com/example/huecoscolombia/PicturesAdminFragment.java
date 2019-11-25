package com.example.huecoscolombia;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PicturesAdminFragment extends Fragment {

    private ImageView pictureImg, checkImg;
    private ImageButton likeBtn;
    private TextView dateTv, numLikesTv, directionTv, descriptionTv;
    private Button inProcessBtn, messagesBtn, deleteBtn;

    public PicturesAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pictures_admin, container, false);
        pictureImg = view.findViewById(R.id.fr_pictures_admin_picture_img);
        checkImg = view.findViewById(R.id.fr_pictures_admin_check_img);
        likeBtn = view.findViewById(R.id.fr_pictures_admin_like_btn);
        dateTv = view.findViewById(R.id.fr_pictures_admin_date_tv);
        numLikesTv = view.findViewById(R.id.fr_pictures_admin_num_like_tv);
        directionTv = view.findViewById(R.id.fr_pictures_admin_direction_tv);
        descriptionTv = view.findViewById(R.id.fr_pictures_admin_description_tv);
        inProcessBtn = view.findViewById(R.id.fr_pictures_admin_inprocess_btn);
        messagesBtn = view.findViewById(R.id.fr_pictures_admin_messages_btn);
        deleteBtn = view.findViewById(R.id.fr_pictures_admin_delete_btn);

        inProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

}
