package com.example.huecoscolombia;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.huecoscolombia.util.Response;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class NewAdapter extends BaseAdapter {

    public static int PAGE=1;
    private ClientRest rest;
    private Response res;

    private LinkedList<Publication> news;

    public NewAdapter (Response r) {

        news = new LinkedList<>();
        rest=new ClientRest();
        res=r;
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
        boolean esta = false;
        Publication aux = news.get(i);
        if(aux.getLikes()!=null){
            if(aux.getLikes().contains(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                like.setBackgroundResource(R.drawable.star_for_like);
            }
        }
        if(aux.getState().equals(Publication.TO_DO)){
            check.setBackgroundResource(R.drawable.check_request);
        } else if(aux.getState().equals(Publication.IN_PROGRESS)) {
            check.setBackgroundResource(R.drawable.checked_process);
        } else {
            check.setBackgroundResource(R.drawable.checked_succes);
        }
        TextView numLike = v.findViewById(R.id.row_new_num_like_tv);
        TextView date = v.findViewById(R.id.row_new_date_tv);
        TextView description = v.findViewById(R.id.row_new_description_tv);
        TextView direction = v.findViewById(R.id.row_new_direction_tv);
        Publication object=news.get(i);
        picture.setImageBitmap(object.getImage());
        int likes=object.getLikes()!=null?object.getLikes().size():0;
        numLike.setText(likes+"");
        DateFormat format=new SimpleDateFormat("dd-MM-yyyy");

        date.setText(format.format(new Date(object.getDate())));
        description.setText(object.getDescription());
        direction.setText(object.getLocation());

        like.setOnClickListener((v1)->{

            rest.addLike(object, FirebaseAuth.getInstance().getCurrentUser().getEmail(),res );
            like.setBackgroundResource(R.drawable.star_for_like);
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
        notifyDataSetChanged();
    }

}
