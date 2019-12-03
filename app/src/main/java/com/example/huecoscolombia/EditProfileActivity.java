package com.example.huecoscolombia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton back;
    private Button save;
    private EditText name;
    private EditText email;
    private EditText newPassword;
    private EditText conpassword;
    private EditText oldPassword;
    private Switch changePassword;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Log.e("edit","create");

        back = findViewById(R.id.edit_profile_back_btn);
        save=findViewById(R.id.edit_profile_save_btn);
        name=findViewById(R.id.edit_profile_name_et);
        email=findViewById(R.id.edit_profile_email);
        oldPassword=findViewById(R.id.edit_profile_password);
        newPassword=findViewById(R.id.edit_profile_new_password);
        conpassword=findViewById(R.id.edit_profile_confirm_password);
        changePassword=findViewById(R.id.edit_profile_switch);
        if(!changePassword.isSelected()){
            Log.e("selected","false");
            oldPassword.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            conpassword.setVisibility(View.GONE);
        }
        changePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked){
                Log.e("selected","false");
                oldPassword.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                conpassword.setVisibility(View.GONE);
            }else{
                Log.e("selected","true");
                oldPassword.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                conpassword.setVisibility(View.VISIBLE);
            }
        });
        db=FirebaseDatabase.getInstance();

        back.setOnClickListener(
                view -> super.onBackPressed()
        );
        save.setOnClickListener((v)->{

        });
    }
}
