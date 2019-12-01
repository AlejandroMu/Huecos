package com.example.huecoscolombia;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.example.huecoscolombia.util.ClientRest;
import com.example.huecoscolombia.util.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PicturesAdminFragment extends Fragment implements Response {

    private ImageButton previousBtn, nextBtn;
    private AdapterViewFlipper publicationsAVF;
    private ContainerPubllicationsAdapter containerPubllicationsAdapter;

    FirebaseDatabase db;

    public PicturesAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pictures_admin, container, false);
        db = FirebaseDatabase.getInstance();
        previousBtn = view.findViewById(R.id.fr_pictures_admin_previous_pub_btn);
        nextBtn = view.findViewById(R.id.fr_pictures_admin_next_pub_btn);
        publicationsAVF = view.findViewById(R.id.fr_pictures_admin_publication_avf);
        containerPubllicationsAdapter = new ContainerPubllicationsAdapter(this);
        ClientRest rest=new ClientRest();
        rest.getPubications(0,-1,-1,this);
        previousBtn.setOnClickListener(
                view1 -> {
                    publicationsAVF.showPrevious();
                }
        );
        nextBtn.setOnClickListener(
                view1 -> {
                    publicationsAVF.showNext();
                }
        );

        publicationsAVF.setAdapter(containerPubllicationsAdapter);
        return view;
    }

    @Override
    public void responsePublications(LinkedList<Publication> publications) {
        getActivity().runOnUiThread(()->{
            containerPubllicationsAdapter.setList(publications);
            containerPubllicationsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void responseImage(Publication pub, Bitmap image) {
        getActivity().runOnUiThread(()->{
            pub.setImage(image);
            containerPubllicationsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void responsePublication(Publication old, Publication newP) {
        getActivity().runOnUiThread(()->{
            old.setState(newP.getState());
            containerPubllicationsAdapter.notifyDataSetChanged();
        });
    }

}
