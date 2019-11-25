package com.example.huecoscolombia;

public class New {

    private String picture, numLikes, date;

    public New() {
    }

    public New(String picture, String numLikes, String date) {
        this.picture = picture;
        this.numLikes = numLikes;
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
