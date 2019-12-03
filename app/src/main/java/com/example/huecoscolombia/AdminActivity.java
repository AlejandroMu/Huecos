package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton picturesBtn, listBtn, listUsersBtn, messagesBtn;
    private LinearLayout containerLl, picturesLl, listLl, listUsersLl, messagesLl;

    private PicturesAdminFragment picturesAdminFragment;
    private ListAdminFragment listAdminFragment;
    private ListUsersAdminFragment listUsersAdminFragment;
    private MessagesAdminFragment messagesAdminFragment;

    public static final int PRIMARY_COLOR=R.color.colorPrimary;
    public static final int ACENT_COLOR=R.color.colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        picturesAdminFragment = new PicturesAdminFragment();
        listAdminFragment = new ListAdminFragment();
        listUsersAdminFragment = new ListUsersAdminFragment();
        messagesAdminFragment = new MessagesAdminFragment();

        picturesBtn = findViewById(R.id.admin_pictures_btn);
        listBtn = findViewById(R.id.admin_list_btn);
        listUsersBtn = findViewById(R.id.admin_list_users_btn);
        messagesBtn = findViewById(R.id.admin_messages_btn);
        picturesLl = findViewById(R.id.admin_pictures_ll);
        listLl = findViewById(R.id.admin_list_ll);
        listUsersLl = findViewById(R.id.admin_list_users_ll);
        messagesLl = findViewById(R.id.admin_messages_ll);

        picturesBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        listUsersBtn.setOnClickListener(this);
        messagesBtn.setOnClickListener(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.admin_container_ll,picturesAdminFragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if(view.equals(picturesBtn)){
            ft.replace(R.id.admin_container_ll,picturesAdminFragment);
            changeColor(picturesLl);
        } else if(view.equals(listBtn)) {
            ft.replace(R.id.admin_container_ll,listAdminFragment);
            changeColor(listLl);
        } else if(view.equals(listUsersBtn)) {
            ft.replace(R.id.admin_container_ll,listUsersAdminFragment);
            changeColor(listUsersLl);
        } else if(view.equals(messagesBtn)) {
            ft.replace(R.id.admin_container_ll,messagesAdminFragment);
            changeColor(messagesLl);
        }
        ft.commit();
    }

    public void changeColor(LinearLayout ll) {
        if(ll.equals(picturesLl)){
            picturesLl.setBackgroundResource(PRIMARY_COLOR);
            listLl.setBackgroundResource(ACENT_COLOR);
            listUsersLl.setBackgroundResource(ACENT_COLOR);
            messagesLl.setBackgroundResource(ACENT_COLOR);
            picturesBtn.setBackgroundResource(R.drawable.pictures_acent);
            listBtn.setBackgroundResource(R.drawable.list_primary);
            listUsersBtn.setBackgroundResource(R.drawable.list_users_primary);
            messagesBtn.setBackgroundResource(R.drawable.messages_primary);
        } else if(ll.equals(listLl)){
            picturesLl.setBackgroundResource(ACENT_COLOR);
            listLl.setBackgroundResource(PRIMARY_COLOR);
            listUsersLl.setBackgroundResource(ACENT_COLOR);
            messagesLl.setBackgroundResource(ACENT_COLOR);
            picturesBtn.setBackgroundResource(R.drawable.pictures_primary);
            listBtn.setBackgroundResource(R.drawable.list_acent);
            listUsersBtn.setBackgroundResource(R.drawable.list_users_primary);
            messagesBtn.setBackgroundResource(R.drawable.messages_primary);
        } else if(ll.equals(listUsersLl)){
            picturesLl.setBackgroundResource(ACENT_COLOR);
            listLl.setBackgroundResource(ACENT_COLOR);
            listUsersLl.setBackgroundResource(PRIMARY_COLOR);
            messagesLl.setBackgroundResource(ACENT_COLOR);
            picturesBtn.setBackgroundResource(R.drawable.pictures_primary);
            listBtn.setBackgroundResource(R.drawable.list_primary);
            listUsersBtn.setBackgroundResource(R.drawable.list_users_acent);
            messagesBtn.setBackgroundResource(R.drawable.messages_primary);
        } else if(ll.equals(messagesLl)){
            picturesLl.setBackgroundResource(ACENT_COLOR);
            listLl.setBackgroundResource(ACENT_COLOR);
            listUsersLl.setBackgroundResource(ACENT_COLOR);
            messagesLl.setBackgroundResource(PRIMARY_COLOR);
            picturesBtn.setBackgroundResource(R.drawable.pictures_primary);
            listBtn.setBackgroundResource(R.drawable.list_primary);
            listUsersBtn.setBackgroundResource(R.drawable.list_users_primary);
            messagesBtn.setBackgroundResource(R.drawable.messages_acent);
        }
    }
}
