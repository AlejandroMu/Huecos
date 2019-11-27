package com.example.huecoscolombia.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ClientRest {

    private void getImage(String urlI,ResponseImage res){
        new Thread(()->{
        if(urlI!=null){
            try {
                URL _url = new URL(urlI);
            URLConnection connection = null;
                connection = _url.openConnection();

            connection.connect();
            InputStream reader = connection.getInputStream();
            BufferedInputStream inputStream = new BufferedInputStream(reader);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            res.response(bitmap);
            } catch (IOException e) {
                res.response(null);
            }
        }}).start();

    }
    public void addLike(){

    }

}
