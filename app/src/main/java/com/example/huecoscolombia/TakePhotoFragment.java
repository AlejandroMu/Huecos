package com.example.huecoscolombia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.example.huecoscolombia.util.UtilDomi;

import java.io.File;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class TakePhotoFragment extends Fragment {

    private ImageButton takePhoto, gallery;
    private File photoFile;
    private String id;

    private static final int CAMERA_CALLBACK_ID = 11;
    private static final int GALLERY_CALLBACK_ID = 12;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CALLBACK_ID && resultCode == Activity.RESULT_OK) {
            if(listener!=null) {
                listener.onTakedPhoto(photoFile);
            }
            ((TakePhoto)getActivity()).publishPhoto();
        }
        if (requestCode == GALLERY_CALLBACK_ID && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            photoFile = new File(UtilDomi.getPath(HuecosColombiaApp.getContext(), uri));
            //Bitmap imagen = BitmapFactory.decodeFile(photoFile.toString());
            //pictureTaked.setImageBitmap(imagen);

            //5...
            File dest = new File(HuecosColombiaApp.getContext().getExternalFilesDir(null) + "/" + id + ".png");
            UtilDomi.copyFileUsingStream(photoFile, dest);
            if(listener!=null) {
                listener.onTakedPhoto(dest);
            }
            ((TakePhoto)getActivity()).publishPhoto();
            //5...
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take_photo, container,false);
        takePhoto = view.findViewById(R.id.fr_take_photo_btn);
        gallery = view.findViewById(R.id.fr_gallery_btn);
        id = UUID.randomUUID().toString();

        takePhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        photoFile = new File(HuecosColombiaApp.getContext().getExternalFilesDir(null) + "/" + id + ".png");
                        Uri uri = FileProvider.getUriForFile(HuecosColombiaApp.getContext(), HuecosColombiaApp.getContext().getPackageName(), photoFile);
                        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(i, CAMERA_CALLBACK_ID);
                    }
                }
        );
        gallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        i.setType("image/*");
                        startActivityForResult(i, GALLERY_CALLBACK_ID);

                    }
                }
        );


        return view;
    }

    public interface OnTakedPhoto {
        void onTakedPhoto(File image);
    }

    private OnTakedPhoto listener;

    public void setListener(OnTakedPhoto listener) {
        this.listener = listener;
    }
}
