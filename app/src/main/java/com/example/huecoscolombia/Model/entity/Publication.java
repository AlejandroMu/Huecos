package com.example.huecoscolombia.Model.entity;

import android.graphics.Bitmap;

import com.example.huecoscolombia.Model.driver.Id;

import java.util.Date;

public class Publication {
    public static final String URL="https://us-central1-huecos-colombia-114c0.cloudfunctions.net/functions/publications";
    public static final String BRANCH="publications";

    public static final String IN_PROGRESS="IN_PROGRESS";
    public static final String TO_DO="TO_DO";
    public static final String DONE="DONE";
    public static final String ALL="ALL";


    @Id
    private String id;

    private Bitmap image;
    private String pathImage;
    private String location;
    private String description;
    private String user;
    private long date;
    private String state;
    private int likes;

    public Publication(String id,String pathImage, String location, String description, long date, String state, int likes,String user) {
        this.id = id;
        this.pathImage = pathImage;
        this.location = location;
        this.description = description;
        this.date = date;
        this.state = state;
        this.likes = likes;
        this.user=user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
