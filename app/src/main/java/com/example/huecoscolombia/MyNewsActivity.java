package com.example.huecoscolombia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.util.ClientRest;
import com.example.huecoscolombia.util.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class MyNewsActivity extends AppCompatActivity implements Response ,ValueEventListener{

    private ImageButton backBtn;
    private ListView list;
    private NewAdapter adapter;
    private ClientRest clientRest;
    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        backBtn = findViewById(R.id.my_news_back_btn);
        list=findViewById(R.id.fr_news_lv);
        adapter=new NewAdapter(this);
        list.setAdapter(adapter);

        backBtn.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
        clientRest=new ClientRest();
        db.getReference().child(Publication.BRANCH).child(Publication.TO_DO).child(auth.getCurrentUser().getEmail().replace(".","_"))
                .addListenerForSingleValueEvent(this);
        db.getReference().child(Publication.BRANCH).child(Publication.IN_PROGRESS).child(auth.getCurrentUser().getEmail().replace(".","_"))
                .addListenerForSingleValueEvent(this);
        db.getReference().child(Publication.BRANCH).child(Publication.DONE).child(auth.getCurrentUser().getEmail().replace(".","_"))
                .addListenerForSingleValueEvent(this);


    }

    @Override
    public void responsePublications(LinkedList<Publication> publications) {

    }

    @Override
    public void responseImage(Publication pub, Bitmap image) {
        runOnUiThread(()->{
            pub.setImage(image);
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    public void responsePublication(Publication old,Publication newP) {
        runOnUiThread(()->{
            old.setLikes(newP.getLikes());

            adapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot value:dataSnapshot.getChildren()){
            Publication pub=value.getValue(Publication.class);
            adapter.addElement(pub);
            clientRest.getImage(pub,MyNewsActivity.this);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
