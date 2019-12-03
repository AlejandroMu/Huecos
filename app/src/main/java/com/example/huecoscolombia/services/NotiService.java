package com.example.huecoscolombia.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;

import com.example.huecoscolombia.Model.entity.Message;
import com.example.huecoscolombia.util.NotificationUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Comment;

public class NotiService extends Service {

    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        db.getReference().child("notificaciones")
                .child(auth.getCurrentUser().getEmail())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i = 0;
                        for(DataSnapshot mensaje : dataSnapshot.getChildren()){
                            Message m = mensaje.getValue(Message.class);
                            NotificationUtils.createNotification(
                                    i++,
                                    "Nueva Notificacion",
                                    m.getMessage()
                            );
                            db.getReference().child("notificaciones")
                                    .child(auth.getCurrentUser().getEmail())
                                    .child(m.getId())
                                    .setValue(null);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
