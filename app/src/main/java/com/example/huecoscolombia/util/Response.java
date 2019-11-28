package com.example.huecoscolombia.util;

import android.graphics.Bitmap;

import com.example.huecoscolombia.Model.entity.Publication;

import java.util.*;

public interface Response {

    void responseImage(Bitmap bitmap);
    void responsePublication(LinkedList<Publication> publications);
    void responseImage(Publication pub,Bitmap image);
}
