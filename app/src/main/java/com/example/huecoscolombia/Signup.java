package com.example.huecoscolombia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Role;
import com.example.huecoscolombia.Model.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    private EditText name;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText conPassword;
    private Button signup;
    private ImageButton back;

    FirebaseAuth auth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        name=findViewById(R.id.signup_name_et);
        username=findViewById(R.id.signup_user_et);
        email=findViewById(R.id.signup_email_et);
        password=findViewById(R.id.signup_password_et);
        conPassword=findViewById(R.id.signup_repassword_et);
        signup=findViewById(R.id.signup_signup_btn);
        back=findViewById(R.id.signup_back_btn);

        back.setOnClickListener(
                view -> {
                    super.onBackPressed();
                }
        );

        signup.setOnClickListener((view)->{

            String user=email.getText().toString();
            String password=this.password.getText().toString();
            String conPassword=this.conPassword.getText().toString();
            if(password.equals(conPassword)){
                auth.createUserWithEmailAndPassword(user,password).addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        String name=this.name.getText().toString();
                        String username=this.username.getText().toString();
                        User us=new User();
                        us.setPassword(password);
                        us.setUsername(username);
                        us.setEmail(user);
                        if(user.contains("hotmail")){
                            us.addRole(Role.ADMIN);
                        } else {
                            us.addRole(Role.CLIENT);
                        }
                        db.getReference().child(User.BRANCH).child(user.replace(".","_")).setValue(us);
                        Person person=new Person();
                        person.setEmail(user);
                        person.setName(name);
                        person.setUsername(username);
                        db.getReference().child(Person.BRANCH).child(user.replace(".","_")).setValue(person);
                        if(user.contains("hotmail")){
                            startActivity(new Intent(this, AdminActivity.class));
                        } else {
                            startActivity(new Intent(this, TakePhoto.class));
                        }
                    }
                });
            }else{
                Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onBackPressed() {

    }
}
