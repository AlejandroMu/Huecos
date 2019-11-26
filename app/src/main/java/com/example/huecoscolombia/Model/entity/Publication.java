package com.example.huecoscolombia.Model.entity;

import android.graphics.Bitmap;

import com.example.huecoscolombia.Model.driver.Id;

import java.util.Date;

public class Publication {

    @Id
    private String id;

    private Bitmap image;
    private String pathImage;
    private String location;
    private String description;
    private Date date;
    private State state;
    private int likes;

}
