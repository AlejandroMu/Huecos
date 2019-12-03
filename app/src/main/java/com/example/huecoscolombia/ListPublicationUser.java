package com.example.huecoscolombia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.AdapterViewFlipper;
import android.widget.ImageButton;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.util.Response;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class ListPublicationUser extends AppCompatActivity implements Response{

    private ImageButton backBtn, previousBtn, nextBtn;
    private AdapterViewFlipper publicationsAVF;
    private ContainerPubllicationsAdapter containerPubllicationsAdapter;
    private Person person;
    private LinkedList publicationsList;
    private String state, key;
    FirebaseDatabase db;

    public ListPublicationUser () {
        publicationsList = new LinkedList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_publication_user);
        db = FirebaseDatabase.getInstance();

        key = (String) getIntent().getSerializableExtra("key");
        state = (String) getIntent().getSerializableExtra("state");
        backBtn = findViewById(R.id.list_publication_user_back_btn);
        previousBtn = findViewById(R.id.list_publication_user_previous_pub_btn);
        nextBtn = findViewById(R.id.list_publication_user_next_pub_btn);
        publicationsAVF = findViewById(R.id.list_publication_user_publications_avf);
        containerPubllicationsAdapter = new ContainerPubllicationsAdapter(this);
        loadPublications();

        backBtn.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
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
    }


    @Override
    public void responsePublications(LinkedList<Publication> publications) {

    }

    @Override
    public void responseImage(Publication pub, Bitmap image) {

    }

    @Override
    public void responsePublication(Publication old, Publication newP) {

    }

    public void loadPublications(){
        publicationsList.clear();
        db.getReference().child("publications")
                .child(state)
                .child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Publication publication = child.getValue(Publication.class);
                    publicationsList.add(publication);
                }
                containerPubllicationsAdapter.setList(publicationsList);
                containerPubllicationsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
