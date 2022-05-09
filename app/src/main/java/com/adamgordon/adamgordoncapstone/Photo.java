package com.adamgordon.adamgordoncapstone;

import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;

import java.io.Serializable;

public class Photo implements Serializable {
    String fileName;
    ImageView image;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public Photo (){
        this.fileName = fileName;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public class MyViewHolder {
    }
}
