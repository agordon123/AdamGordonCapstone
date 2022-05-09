package com.adamgordon.adamgordoncapstone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CreateProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser fbUser;
    private FirebaseFirestore database;
    private HashMap userData;
    private CollectionReference collRef;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        database = FirebaseFirestore.getInstance();
        collRef = database.collection("CapstoneUsers");
    }
    public void registerUserWithFirestore(FirebaseUser user, FirebaseFirestore dbInstance,HashMap<String,Object> userData){
        database = dbInstance;
        user = mAuth.getCurrentUser();
        DocumentReference doc = database.collection("CapstoneUsers").document();
        String uid = doc.getId();
        String email = user.getEmail();
        userData.put("Email",email.toString());
        userData.put("UID",uid);

        database.collection("CapstoneUsers").add(userData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "New User Added with ID: " + documentReference.getId() )).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error adding to firestore",e);
            }
        });

    }
}