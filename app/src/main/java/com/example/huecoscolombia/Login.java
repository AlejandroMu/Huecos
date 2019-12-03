package com.example.huecoscolombia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huecoscolombia.services.NotiService;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    private EditText username;
    private EditText password;
    private Button send;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        Intent service=new Intent(this, NotiService.class);
        startService(service);
        if(auth.getCurrentUser()!=null){
            if(auth.getCurrentUser().getEmail().contains("hotmail")){
                startActivity(new Intent(this,AdminActivity.class));
            } else {
                startActivity(new Intent(this,TakePhoto.class));
            }
            finish();
        }
        username=findViewById(R.id.login_email_et);
        password=findViewById(R.id.login_password_et);
        send=findViewById(R.id.login_login_btn);
        signUp=findViewById(R.id.login_register_tv);

        send.setOnClickListener((view)->{
            String username=this.username.getText().toString();
            String password=this.password.getText().toString();
            auth.signInWithEmailAndPassword(username,password).addOnCompleteListener((task)->{
                if(task.isSuccessful()){
                    if(username.contains("hotmail")){
                        startActivity(new Intent(this,AdminActivity.class));
                    } else {
                        startActivity(new Intent(this,TakePhoto.class));
                    }
                    finish();
                }else {
                    Toast.makeText(this,"Error al Iniciar sesion "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            });

        });
        signUp.setOnClickListener((view)->{
            Intent i=new Intent(this, Signup.class);
            startActivity(i);
        });


    }

    @Override
    public void onBackPressed() {

    }
}
