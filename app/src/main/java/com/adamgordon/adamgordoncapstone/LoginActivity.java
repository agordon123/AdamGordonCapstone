package com.adamgordon.adamgordoncapstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.ParcelField;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private TextView googleSignInView;
    private TextView loginView;
    private TextView nameView;
    private TextView passwordView;
    private TextView googleUN;
    private TextView googlePWview;

    private EditText userInput;
    private EditText passwordInput;
    private EditText googleInput;
    private EditText googlePW;

    private Button loginButton;
    private ImageButton googleButton;
    private Button existingUser;
    private static final int REQ_ONE_TAP = 0;
    private boolean showOneTapUI = true;

    private String TAG;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private Intent intent;

    private FirebaseApp fbApp;
    private FirebaseFirestore database;
    private FirebaseUser fbUser;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private Map<String,Object> userData = new HashMap<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginView = findViewById(R.id.loginView);
        nameView = findViewById(R.id.userNameView);
        passwordView = findViewById(R.id.passwordView);
        passwordInput = findViewById(R.id.passwordInput);
        userInput = findViewById(R.id.userInput);
        loginButton = findViewById(R.id.loginButton);
        googleSignInView = findViewById(R.id.googleSignInView);
        googleButton = findViewById(R.id.googleButton);
        existingUser = findViewById(R.id.existingUser);


        mAuth = FirebaseAuth.getInstance();
        // Below code handles Sign in requests with either Email and Password Or with Google

        //onclick listener for login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginNewUser(userInput.getText().toString(),passwordInput.getText().toString(),database,intent);
                // Validations for input email and password
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                //create new user and start a firestore doc-ref to the new user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(intent);
                                } else {
                                    Log.d("Error", "Login Failed");
                                }
                            }
                        });
            }
        });
        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userInput.getText().toString();
                String password = passwordInput.getText().toString();
                // Validations for input email and password
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                loginWithExistingAccount(email,password);
            }
        });
    }
       /* googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = userInput.getText().toString();
                password = passwordInput.getText().toString();
                googleSignIn();

            }
        });
    }*/
       @Override
       public void onStart() {
           super.onStart();
           // Check if user is signed in (non-null) and update UI accordingly.
           FirebaseUser currentUser = mAuth.getCurrentUser();
           if(currentUser != null){
               currentUser.reload();
           }
       }

    private void updateUI(FirebaseUser currentUser) {
        intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }


    protected void loginNewUser(String un, String pw,FirebaseFirestore db,Intent i) {
        database = db;

        i = new Intent(LoginActivity.this,HomeActivity.class);
        CollectionReference collRef = db.collection("CapstoneUsers");
        DocumentReference docRef = collRef.document();

        mAuth.createUserWithEmailAndPassword(un, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userData.put(task.getResult().toString(),mAuth.getCurrentUser());

                    intent = new Intent(LoginActivity.this, HomeActivity.class);


                } else {
                    Toast.makeText(getApplicationContext(), "login_failed", Toast.LENGTH_SHORT).show();
                    passwordInput.clearComposingText();
                }
            }
        });

    }
    private void googleSignIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,100);

    }
    private void HomeActivity(){
        finish();
        intent = new Intent(getApplicationContext(),HomeActivity.class);
        fbUser = mAuth.getCurrentUser();
        userData.put("Username",fbUser.getEmail());
        userData.put("UID",fbUser.getUid());
        intent.putExtra("FireBaseUser", (Parcelable) userData);

        startActivity(intent);
    }
    private void loginWithExistingAccount(String email,String pw){
        email = userInput.getText().toString();
        pw = passwordInput.getText().toString();
        mAuth.signInWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    HomeActivity();
                }else{
                    Log.w("ERROR","Sign in not successful");
                }
            }
        });
    }
   /* protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    } */
}
