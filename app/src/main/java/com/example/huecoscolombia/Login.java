package com.example.huecoscolombia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.Model.entity.Role;
import com.example.huecoscolombia.Model.entity.User;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.example.huecoscolombia.services.NotiService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 10;
    FirebaseAuth auth;
    FirebaseDatabase db;
    private EditText username;
    private EditText password;
    private Button send;
    private TextView signUp;
    public static GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
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

        signInButton.setOnClickListener(
                view -> {
                    switch (view.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                        // ...
                    }
                }
        );

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("error", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            db.getReference().child("persons").child(auth.getCurrentUser().getEmail().replace(".","_"))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Person person = dataSnapshot.getValue(Person.class);
                                            if(person==null){
                                                person = new Person();
                                                person.setEmail(auth.getCurrentUser().getEmail());
                                                person.setName(auth.getCurrentUser().getDisplayName());
                                                person.setUsername(auth.getCurrentUser().getDisplayName());
                                                db.getReference().child("persons")
                                                        .child(auth.getCurrentUser().getEmail().replace(".","_"))
                                                        .setValue(person);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                            Intent intent = new Intent(HuecosColombiaApp.getContext(), TakePhoto.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.


                        }

                        // ...
                    }
                });
    }




}
