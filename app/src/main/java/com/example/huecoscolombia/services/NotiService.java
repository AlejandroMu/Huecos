package com.example.huecoscolombia.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.huecoscolombia.Model.entity.Message;
import com.example.huecoscolombia.util.NotificationUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;



public class NotiService extends Service {

    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        db.getReference().child("notificaciones")
                .child(auth.getCurrentUser().getEmail().replace(".","_"))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        int i = 0;
                        Log.e("noti","onDatachange");
                        for(DataSnapshot mensaje : dataSnapshot.getChildren()){
                            Message m = mensaje.getValue(Message.class);
                            NotificationUtils.createNotification(
                                    i++,
                                    "Nueva Notificacion",
                                    m.getMessage()
                            );
                            db.getReference().child("notificaciones")
                                    .child(auth.getCurrentUser().getEmail().replace(".","_"))
                                    .child(m.getId())
                                    .setValue(null);
                        }
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


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
