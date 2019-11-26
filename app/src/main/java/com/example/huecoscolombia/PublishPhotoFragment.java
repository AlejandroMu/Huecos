package com.example.huecoscolombia;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.huecoscolombia.Model.entity.Publication;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishPhotoFragment extends Fragment implements TakePhotoFragment.OnTakedPhoto{

    private EditText direction, description;
    private Button publishBtn;
    private ImageButton cancelBtn, backBtn;
    private ImageView pictureImg;
    Bitmap bitmapAux = null;
    File newPhoto;
    FirebaseStorage storage;
    FirebaseDatabase db;
    FirebaseAuth auth;

    public PublishPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish_photo, container, false);
        storage=FirebaseStorage.getInstance();
        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        publishBtn = view.findViewById(R.id.publish_photo_publish_btn);
        cancelBtn = view.findViewById(R.id.publish_photo_cancel_btn);
        backBtn = view.findViewById(R.id.publish_photo_back_btn);
        direction = view.findViewById(R.id.publish_photo_direction_et);
        description = view.findViewById(R.id.publish_photo_description_et);
        pictureImg = view.findViewById(R.id.publish_photo_picture_img);


        if(bitmapAux!=null){
            pictureImg.setImageBitmap(bitmapAux);
        }

        backBtn.setOnClickListener(view1 -> ((TakePhoto)getActivity()).backPublishPhoto());

        cancelBtn.setOnClickListener(view1 -> ((TakePhoto)getActivity()).backPublishPhoto());

        publishBtn.setOnClickListener(view1->{
            Uri file=Uri.fromFile(newPhoto);
            String id=UUID.randomUUID().toString();
           storage.getReference().child(id).putFile(file).addOnCompleteListener(task -> {
              Task<Uri> url=task.getResult().getStorage().getDownloadUrl();
              url.addOnCompleteListener(task1 -> {
                  Uri uri=url.getResult();
                  Publication publication=new Publication(
                          id,
                          uri.toString(),
                          direction.getText().toString(),
                          description.getText().toString(),
                          System.currentTimeMillis(),
                          Publication.TO_DO,0);

                  db.getReference().child(Publication.BRANCH).child(Publication.TO_DO)
                          .child(auth.getCurrentUser().getEmail().replace(".","_"))
                          .child(id).setValue(publication);
              });
           });
            ((TakePhoto)getActivity()).showNews();


        });
        return view;
    }

    @Override
    public void onTakedPhoto(File image) {
        bitmapAux =BitmapFactory.decodeFile(image.toString());
        newPhoto=image;

    }

}
