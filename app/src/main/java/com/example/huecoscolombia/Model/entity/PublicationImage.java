package com.example.huecoscolombia.Model.entity;

import android.graphics.Bitmap;

public class PublicationImage {

    private Publication publication;
    private Bitmap image;

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
