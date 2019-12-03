package com.example.huecoscolombia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;

import android.os.Bundle;
import android.provider.FontRequest;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Message;
import com.example.huecoscolombia.Model.entity.Role;
import com.example.huecoscolombia.util.ClientRest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageActivity extends AppCompatActivity {

    private ImageButton back;
    private ImageButton send;
    private ListView messages;
    private EditText message;
    private MessageAdapter adapter;
    private String userDestId;
    FirebaseDatabase db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        adapter=new MessageAdapter();
        userDestId=getIntent().getExtras().getString("dest").replace(".","_");

        send=findViewById(R.id.message_send_btn);
        messages=findViewById(R.id.list_message);
        messages.setAdapter(adapter);
        message=findViewById(R.id.message_et);

        back = findViewById(R.id.message_back_btn);
        back.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
        ClientRest clientRest=new ClientRest();
        clientRest.getMessage(auth.getCurrentUser().getEmail().replace(".","_"),userDestId,(list)->{
            runOnUiThread(()->{
                adapter.setList(list);
            });
        });
        String userId=auth.getCurrentUser().getEmail().replace(".","_");

        send.setOnClickListener(v->{
            String msm=message.getText().toString();
            if(!msm.isEmpty()){
                String id= UUID.randomUUID().toString();
                Message newMessage=new Message(id,auth.getCurrentUser().getEmail(),msm,System.currentTimeMillis());
                db.getReference().child(Message.BRANCH).child(userDestId).child(userId).child(id).setValue(newMessage);
                db.getReference().child(Message.BRANCH).child(userId).child(userDestId).child(id).setValue(newMessage);
                message.setText("");
             }
        });
        db.getReference().child(Message.BRANCH).child(userId).child(userDestId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Message newM=dataSnapshot.getValue(Message.class);
                        adapter.addMessage(newM);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
