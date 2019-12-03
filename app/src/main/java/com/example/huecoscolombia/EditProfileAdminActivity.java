package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.huecoscolombia.Model.entity.Person;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileAdminActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private Button saveBtn;
    private EditText nameEt, emailEt, newPassword, rePassword;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        Person person = (Person) getIntent().getSerializableExtra("person");
        setContentView(R.layout.activity_edit_profile_admin);
        backBtn = findViewById(R.id.edit_profile_admin_back_btn);
        saveBtn = findViewById(R.id.edit_profile_admin_save_btn);
        nameEt = findViewById(R.id.edit_profile_admin_name_et);
        emailEt = findViewById(R.id.edit_profile_admin_email_et);
        newPassword = findViewById(R.id.edit_profile_admin_new_password_et);
        rePassword = findViewById(R.id.edit_profile_admin_confirm_password_et);

        nameEt.setText(person.getName());
        emailEt.setText(person.getEmail());

        backBtn.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );

        saveBtn.setOnClickListener(
                view -> {
                    db.getReference().child("users")
                            .child(person.getEmail());
                }
        );
    }
}
