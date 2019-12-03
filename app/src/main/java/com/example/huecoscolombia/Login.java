package com.example.huecoscolombia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Person;
import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.Model.entity.Role;
import com.example.huecoscolombia.Model.entity.User;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    public static String emailGoogle;
    private SignInButton signInButton;
    public static boolean LOGIN_GOOGLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        LOGIN_GOOGLE=false;
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
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
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            emailGoogle = account.getEmail();
            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HuecosColombiaApp.getContext());
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personEmail = acct.getEmail();
//                String user = personName;
//
//            }
            LOGIN_GOOGLE = true;
            db.getReference().child("users").child(account.getEmail().replace(".","_"))
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            if(user==null){
                                auth.createUserWithEmailAndPassword(account.getEmail(),""+account.getEmail().hashCode())
                                .addOnCompleteListener((task -> {
                                    String name=account.getDisplayName();
                                    String username=account.getDisplayName();
                                    User us=new User();
                                    us.setPassword(""+account.getEmail().hashCode());
                                    us.setUsername(username);
                                    us.setEmail(account.getEmail());
                                    us.addRole(Role.CLIENT);
                                    db.getReference().child(User.BRANCH).child(account.getEmail().replace(".","_")).setValue(us);
                                    Person person=new Person();
                                    person.setEmail(account.getEmail());
                                    person.setName(name);
                                    person.setUsername(username);
                                    db.getReference().child(Person.BRANCH).child(account.getEmail().replace(".","_")).setValue(person);
                                }));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
            auth.signInWithEmailAndPassword(account.getEmail(),""+account.getEmail().hashCode());
            Intent intent = new Intent(HuecosColombiaApp.getContext(), TakePhoto.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            auth.signInWithEmailAndPassword(account.getEmail(),account.getEmail().hashCode()+"");
            emailGoogle = account.getEmail();
            LOGIN_GOOGLE=true;
            Intent intent = new Intent(HuecosColombiaApp.getContext(), TakePhoto.class);
            startActivity(intent);
        }

    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }



}
