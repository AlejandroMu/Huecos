package com.example.huecoscolombia.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.huecoscolombia.Model.entity.Publication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class ClientRest {

    public void getPubications(int page,int range,int el,Response res){
        new Thread(()->{
            try {

                HTTPSWebUtilDomi util = new HTTPSWebUtilDomi();
                String params="?page="+page;
                if(range!=-1){
                    params+="&range="+range;
                }
                if(el!=-1){
                    params+="&el="+el;
                }

                String resp=util.GETrequest(Publication.URL+params);
                Log.e("publications",resp);
                Gson gson=new Gson();
                Type type=new TypeToken<LinkedList<Publication>>(){}.getType();
                LinkedList<Publication> list=gson.fromJson(resp,type);
                res.responsePublication(list);
                for(Publication p:list){
                    String urlI=p.getPathImage();
                    Bitmap map=getImage(urlI);
                    res.responseImage(p,map);
                }



            }catch (Exception e){

            }
        }).start();

    }
    private Bitmap getImage(String urlI){
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
                return bitmap;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public void getImage(String urlI, Response res){
        new Thread(()->{
        Bitmap map=getImage(urlI);
        res.responseImage(map);
        }).start();

    }
    public void addLike(Publication p, String user){
        Log.e("Like", "addLike");

        String body="/like?pub="+p.getId()+"&user="+user;
        new Thread(()->{
            try {
                Log.e("Like", "sending "+body+" link "+Publication.URL+body);
                HTTPSWebUtilDomi utilDomi=new HTTPSWebUtilDomi();
                String res=utilDomi.GETrequest(Publication.URL+body);
                Log.e("likes add",res);

            }catch (Exception e){
                Log.e("likes","exception : "+e.getMessage());

            }

        }).start();

    }

}
