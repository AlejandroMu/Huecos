package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class MyNewsActivity extends AppCompatActivity {

    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);

        backBtn = findViewById(R.id.my_news_back_btn);

        backBtn.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
    }
}
