package com.example.huecoscolombia.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.huecoscolombia.Model.entity.Message;
import com.example.huecoscolombia.Model.entity.Publication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

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
                Gson gson=new Gson();
                Type type=new TypeToken<LinkedList<Publication>>(){}.getType();
                LinkedList<Publication> list=gson.fromJson(resp,type);
                res.responsePublications(list);
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
            } catch (Exception e) {
                Log.e("exc",e.getMessage());
                return null;
            }
        }
        return null;
    }

    public void getImage(Publication pub, Response res){
        new Thread(()->{
        Bitmap map=getImage(pub.getPathImage());
        res.responseImage(pub,map);
        }).start();

    }
    public void addLike(Publication p, String user,Response response){

        String body="/like?pub="+p.getId()+"&user="+user;
        new Thread(()->{
            try {
                HTTPSWebUtilDomi utilDomi=new HTTPSWebUtilDomi();
                String res=utilDomi.GETrequest(Publication.URL+body);
                Gson gson=new Gson();
                Publication resp=gson.fromJson(res,Publication.class);
                response.responsePublication(p,resp);

            }catch (Exception e){
                Log.e("likes","exception : "+e.getMessage());
            }
        }).start();
    }

    public void changeState(Publication p, String state, Response response) {
        String body = "/state?pub="+p.getId()+"&state="+state;
        new Thread(()->{
            try {
                HTTPSWebUtilDomi utilDomi=new HTTPSWebUtilDomi();
                String res=utilDomi.GETrequest(Publication.URL+body);
                Gson gson=new Gson();
                Publication resp=gson.fromJson(res,Publication.class);
                response.responsePublication(p,resp);

            }catch (Exception e){
                Log.e("likes","exception : "+e.getMessage());
            }
        }).start();
    }

    public void getMessage(String user, MessageResponse messageResponse){
        new Thread(()->{
            try {
                HTTPSWebUtilDomi utilDomi=new HTTPSWebUtilDomi();
                String msms=utilDomi.GETrequest(Message.URL+"?user="+user);
                Gson gson=new Gson();
                Type type=new TypeToken<List<Message>>(){}.getType();
                List<Message> list=gson.fromJson(msms,type);
                messageResponse.changeList(list);
            }catch (Exception e){

            }
        }).start();
    }

}
