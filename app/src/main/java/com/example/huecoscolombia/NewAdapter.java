package com.example.huecoscolombia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.util.ClientRest;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.LinkedList;

public class NewAdapter extends BaseAdapter {

    public static int PAGE=1;
    private ClientRest rest;

    private LinkedList<Publication> news;

    public NewAdapter () {

        news = new LinkedList<>();
        rest=new ClientRest();
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Publication getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_new,null);
        ImageView picture = v.findViewById(R.id.row_new_picture_img);
        ImageView check = v.findViewById(R.id.row_new_check_img);
        ImageButton like = v.findViewById(R.id.row_new_like_btn);
        TextView numLike = v.findViewById(R.id.row_new_num_like_tv);
        TextView date = v.findViewById(R.id.row_new_date_tv);
        TextView description = v.findViewById(R.id.row_new_description_tv);
        Publication object=news.get(i);
        picture.setImageBitmap(object.getImage());
        int likes=object.getLikes()!=null?object.getLikes().size():0;
        numLike.setText(likes+"");
        date.setText((new Date(object.getDate())).toString());
        description.setText(object.getDescription());

        like.setOnClickListener((v1)->{
            Log.e("like","button");
            rest.addLike(object, FirebaseAuth.getInstance().getCurrentUser().getEmail());
        });

        return v;
    }
    public void setList(LinkedList<Publication> images){
        news=images;
    }
    public void addElement(Publication image){
        if(news.size()>30){
            news.pop();
        }
        news.add(image);
    }

}
