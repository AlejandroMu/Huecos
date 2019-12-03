package com.example.huecoscolombia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private ImageButton back;
    private Button save;
    private EditText name;
    private EditText newPassword;
    private EditText conpassword;
    private EditText oldPassword;
    private Switch changePassword;
    FirebaseDatabase db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        back = findViewById(R.id.edit_profile_back_btn);
        save=findViewById(R.id.edit_profile_save_btn);
        name=findViewById(R.id.edit_profile_name_et);
        oldPassword=findViewById(R.id.edit_profile_password);
        newPassword=findViewById(R.id.edit_profile_new_password);
        conpassword=findViewById(R.id.edit_profile_confirm_password);
        changePassword=findViewById(R.id.edit_profile_switch);
        if(!changePassword.isChecked()){
            oldPassword.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            conpassword.setVisibility(View.GONE);
        }
        changePassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!isChecked){
                oldPassword.setVisibility(View.GONE);
                newPassword.setVisibility(View.GONE);
                conpassword.setVisibility(View.GONE);
            }else{
                oldPassword.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                conpassword.setVisibility(View.VISIBLE);
            }
        });
        db=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        back.setOnClickListener(
                view -> super.onBackPressed()
        );
        save.setOnClickListener((v)->{
            Log.e("save","start");
            String id=auth.getCurrentUser().getEmail().replace(".","_");
            String name=this.name.getText().toString();
            if(!name.isEmpty()){
                db.getReference().child(Person.BRANCH).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Person p=dataSnapshot.getValue(Person.class);
                        p.setName(name);
                        db.getReference().child(Person.BRANCH).child(id).setValue(p);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            if(changePassword.isChecked()){
                Log.e("selected","changePass");

                db.getReference().child(User.BRANCH).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user=dataSnapshot.getValue(User.class);
                        String pas=oldPassword.getText().toString();
                        String newP=newPassword.getText().toString();
                        String coP=conpassword.getText().toString();
                        if(pas.equals(user.getPassword())){
                            if(newP.equals(coP)){
                                user.setPassword(newP);
                                db.getReference().child(User.BRANCH).child(id).setValue(user);

                                EditProfileActivity.super.onBackPressed();
                            }else{
                                Toast.makeText(EditProfileActivity.this,"Contraseñas nuevas no coinciden",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(EditProfileActivity.this,"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else{
                EditProfileActivity.super.onBackPressed();
            }
        });
    }
}
