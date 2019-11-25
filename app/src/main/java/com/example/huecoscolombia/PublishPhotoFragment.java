package com.example.huecoscolombia;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishPhotoFragment extends Fragment implements TakePhotoFragment.OnTakedPhoto{

    private EditText direction, description;
    private Button publishBtn;
    private ImageButton cancelBtn, backBtn;
    private ImageView pictureImg;
    Bitmap bitmapAux = null;

    public PublishPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish_photo, container, false);
        publishBtn = view.findViewById(R.id.publish_photo_publish_btn);
        cancelBtn = view.findViewById(R.id.publish_photo_cancel_btn);
        backBtn = view.findViewById(R.id.publish_photo_back_btn);
        direction = view.findViewById(R.id.publish_photo_direction_et);
        description = view.findViewById(R.id.publish_photo_description_et);
        pictureImg = view.findViewById(R.id.publish_photo_picture_img);
        if(bitmapAux!=null){
            pictureImg.setImageBitmap(bitmapAux);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TakePhoto)getActivity()).backPublishPhoto();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TakePhoto)getActivity()).backPublishPhoto();
            }
        });
        return view;
    }

    @Override
    public void onTakedPhoto(Bitmap image) {
        bitmapAux = image;
       /* pictureImg.setImageBitmap(image);*/
    }

}
