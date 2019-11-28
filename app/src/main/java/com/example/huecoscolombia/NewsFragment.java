package com.example.huecoscolombia;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.util.ClientRest;
import com.example.huecoscolombia.util.Response;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements Response {

    private ListView newsLv;
    private NewAdapter newAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsLv = view.findViewById(R.id.fr_news_lv);
        newAdapter = new NewAdapter(this);
        ClientRest rest=new ClientRest();
        rest.getPubications(0,-1,-1,this);

        newsLv.setAdapter(newAdapter);
        return view;
    }


    @Override
    public void responsePublications(LinkedList<Publication> publications) {
        getActivity().runOnUiThread(()->{
            newAdapter.setList(publications);
            newAdapter.notifyDataSetChanged();

        });

    }

    @Override
    public void responseImage(Publication pub, Bitmap image) {
        getActivity().runOnUiThread(()->{
            pub.setImage(image);
            newAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void responsePublication(Publication old,Publication newP) {
        getActivity().runOnUiThread(()->{
            old.setLikes(newP.getLikes());

            newAdapter.notifyDataSetChanged();
        });

    }
}
