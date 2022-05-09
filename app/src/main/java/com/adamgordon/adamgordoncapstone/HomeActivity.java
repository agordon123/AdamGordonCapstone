package com.adamgordon.adamgordoncapstone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private TextView welcomeView;
    private TextView customUserView;
    private ImageView mainPhoto;
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private Button takePhotosButton;
    private Button showPhotosButton;
    private Button editProfileButton;
    private Button signOutButton;


    protected void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }


    // credit to https://www.tutorialspoint.com/android/android_camera.htm for info on the camera
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeView = findViewById(R.id.welcomeView);
        customUserView = findViewById(R.id.customUserView);
        mainPhoto = findViewById(R.id.mainPhoto);
        showPhotosButton = findViewById(R.id.showPhotosButton);
        takePhotosButton = findViewById(R.id.takePhotosButton);
        editProfileButton = findViewById(R.id.editProfileButton);
        customUserView.setText(user.getDisplayName());


        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            // implicit intent to use camera app
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


        }
    }
}


