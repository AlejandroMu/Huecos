package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditProfileAdminActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private Button saveBtn;
    private EditText nameEt, emailEt, newPassword, rePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_admin);
        backBtn = findViewById(R.id.edit_profile_admin_back_btn);
        saveBtn = findViewById(R.id.edit_profile_admin_save_btn);
        nameEt = findViewById(R.id.edit_profile_admin_name_et);
        emailEt = findViewById(R.id.edit_profile_admin_email_et);
        newPassword = findViewById(R.id.edit_profile_admin_new_password_et);
        rePassword = findViewById(R.id.edit_profile_admin_confirm_password_et);

        backBtn.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );
    }
}
