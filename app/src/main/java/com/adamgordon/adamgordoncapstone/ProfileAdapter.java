package com.adamgordon.adamgordoncapstone;

import com.google.firebase.storage.FirebaseStorage;

public class ProfileAdapter {
    protected String uid;
    protected String email;
    protected String username;
    protected String avatarURL;
    protected FirebaseStorage storage;
    public ProfileAdapter(){
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.avatarURL = avatarURL;
        storage.getReference();


    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
