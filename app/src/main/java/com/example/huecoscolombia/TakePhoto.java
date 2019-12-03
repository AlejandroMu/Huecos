package com.example.huecoscolombia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.UUID;

public class TakePhoto extends AppCompatActivity implements View.OnClickListener{

    private ImageButton profile, camera, news, info, logout;
    private LinearLayout container, profileLl, photoLl, newsLl, infoLl;

    private TakePhotoFragment takePhotoFragment;
    private ProfileFragment profileFragment;
    private NewsFragment newsFragment;
    private InfoFragment infoFragment;
    private PublishPhotoFragment publishPhotoFragment;

    FirebaseAuth auth;


    public static final int PRIMARY_COLOR=R.color.colorPrimary;
    public static final int ACENT_COLOR=R.color.colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        auth = FirebaseAuth.getInstance();

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 11);

        takePhotoFragment = new TakePhotoFragment();
        profileFragment = new ProfileFragment();
        newsFragment = new NewsFragment();
        infoFragment = new InfoFragment();
        publishPhotoFragment = new PublishPhotoFragment();

        takePhotoFragment.setListener(publishPhotoFragment);

        profile = findViewById(R.id.take_photo_profile);
        camera = findViewById(R.id.take_photo_photo);
        news = findViewById(R.id.take_photo_news);
        info = findViewById(R.id.take_photo_info);
        container = findViewById(R.id.take_photo_container_ll);
        profileLl = findViewById(R.id.take_photo_profile_ll);
        photoLl = findViewById(R.id.take_photo_photo_ll);
        newsLl = findViewById(R.id.take_photo_news_ll);
        infoLl = findViewById(R.id.take_photo_info_ll);
        logout = findViewById(R.id.take_photo_logout_btn);

        profile.setOnClickListener(this);
        camera.setOnClickListener(this);
        news.setOnClickListener(this);
        info.setOnClickListener(this);
        logout.setOnClickListener(
                view -> {
                    if(!Login.LOGIN_GOOGLE){
                        auth.signOut();
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("log", "si esta logeado");
                        signOut();
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }

                }
        );

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.take_photo_container_ll,takePhotoFragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if(view.equals(profile)){
            ft.replace(R.id.take_photo_container_ll,profileFragment);
            changeColor(profileLl);
        } else if(view.equals(camera)) {
            ft.replace(R.id.take_photo_container_ll,takePhotoFragment);
            changeColor(photoLl);
        } else if(view.equals(news)) {
            ft.replace(R.id.take_photo_container_ll,newsFragment);
            changeColor(newsLl);
        } else if(view.equals(info)) {
            ft.replace(R.id.take_photo_container_ll,infoFragment);
            changeColor(infoLl);
        }
        ft.commit();
    }


    public void changeColor(LinearLayout ll){
        if(ll.equals(profileLl)){
            profileLl.setBackgroundResource(PRIMARY_COLOR);
            photoLl.setBackgroundResource(ACENT_COLOR);
            newsLl.setBackgroundResource(ACENT_COLOR);
            infoLl.setBackgroundResource(ACENT_COLOR);
            profile.setBackgroundResource(R.drawable.user_acent);
            camera.setBackgroundResource(R.drawable.camera_primary);
            news.setBackgroundResource(R.drawable.newspaper_primary);
            info.setBackgroundResource(R.drawable.info_primary);
        } else if(ll.equals(photoLl)) {
            profileLl.setBackgroundResource(ACENT_COLOR);
            photoLl.setBackgroundResource(PRIMARY_COLOR);
            newsLl.setBackgroundResource(ACENT_COLOR);
            infoLl.setBackgroundResource(ACENT_COLOR);
            profile.setBackgroundResource(R.drawable.user_primary);
            camera.setBackgroundResource(R.drawable.camera_acent);
            news.setBackgroundResource(R.drawable.newspaper_primary);
            info.setBackgroundResource(R.drawable.info_primary);
        } else if(ll.equals(newsLl)) {
            profileLl.setBackgroundResource(ACENT_COLOR);
            photoLl.setBackgroundResource(ACENT_COLOR);
            newsLl.setBackgroundResource(PRIMARY_COLOR);
            infoLl.setBackgroundResource(ACENT_COLOR);
            profile.setBackgroundResource(R.drawable.user_primary);
            camera.setBackgroundResource(R.drawable.camera_primary);
            news.setBackgroundResource(R.drawable.newspaper_acent);
            info.setBackgroundResource(R.drawable.info_primary);
        } else if(ll.equals(infoLl)) {
            profileLl.setBackgroundResource(ACENT_COLOR);
            photoLl.setBackgroundResource(ACENT_COLOR);
            newsLl.setBackgroundResource(ACENT_COLOR);
            infoLl.setBackgroundResource(PRIMARY_COLOR);
            profile.setBackgroundResource(R.drawable.user_primary);
            camera.setBackgroundResource(R.drawable.camera_primary);
            news.setBackgroundResource(R.drawable.newspaper_primary);
            info.setBackgroundResource(R.drawable.info_acent);
        }
    }

    public void publishPhoto() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.take_photo_container_ll, publishPhotoFragment);
        ft.commit();
    }
    public void showNews(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.take_photo_container_ll, newsFragment);
        changeColor(newsLl);
        ft.commit();
    }

    public void backPublishPhoto(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.take_photo_container_ll, takePhotoFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {

    }

    public void signOut() {
        Login.mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
