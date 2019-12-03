package com.example.huecoscolombia.util;

import android.graphics.Bitmap;

import com.example.huecoscolombia.Model.entity.Publication;

import java.util.*;

public interface Response {

    void responsePublications(LinkedList<Publication> publications);
    void responseImage(Publication pub,Bitmap image);
    void responsePublication(Publication old,Publication newP);
}
