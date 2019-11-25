package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;

import android.os.Bundle;
import android.provider.FontRequest;
import android.widget.ImageButton;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        EmojiCompat.Config emoji = new BundledEmojiCompatConfig(this);
        back = findViewById(R.id.message_back_btn);
        back.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
    }
}
