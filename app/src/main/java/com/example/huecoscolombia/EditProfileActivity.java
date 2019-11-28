package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Log.e("edit","create");

        back = findViewById(R.id.edit_profile_back_btn);

        back.setOnClickListener(
                view ->  {
                    super.onBackPressed();
                }
        );
    }
}
