package com.example.huecoscolombia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Message;
import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.app.HuecosColombiaApp;
import com.example.huecoscolombia.util.ClientRest;
import com.example.huecoscolombia.util.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ContainerPubllicationsAdapter extends BaseAdapter {

    private LinkedList<Publication> publications;
    private ClientRest rest;
    private Response res;

    public ContainerPubllicationsAdapter (Response r)  {
        publications = new LinkedList<>();
        rest = new ClientRest();
        res = r;
    }

    @Override
    public int getCount() {
        return publications.size();
    }

    @Override
    public Object getItem(int i) {
        return publications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.container_admin_publications,null);

        TextView dateTv = v.findViewById(R.id.fr_pictures_admin_date_tv);
        TextView descriptionTv = v.findViewById(R.id.fr_pictures_admin_description_tv);
        TextView directionTv = v.findViewById(R.id.fr_pictures_admin_direction_tv);
        TextView numLikesTv = v.findViewById(R.id.fr_pictures_admin_num_like_tv);
        ImageButton likeBtn = v.findViewById(R.id.fr_pictures_admin_like_btn);
        ImageView checkImg = v.findViewById(R.id.fr_pictures_admin_check_img);
        ImageView pictureImg = v.findViewById(R.id.fr_pictures_admin_picture_img);
        Button inProgressBtn = v.findViewById(R.id.fr_pictures_admin_inprocess_btn);
        Button messagesBtn = v.findViewById(R.id.fr_pictures_admin_messages_btn);
        Button deleteBtn  = v.findViewById(R.id.fr_pictures_admin_delete_btn);
        Publication object=publications.get(i);

        if(object.getState().equals(Publication.DONE)){
            inProgressBtn.setEnabled(false);
            inProgressBtn.setText(R.string.done);
        } else if(object.getState().equals(Publication.IN_PROGRESS)){
            inProgressBtn.setText(R.string.to_finish);
        }
        DateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        dateTv.setText(format.format(new Date(publications.get(i).getDate())));
        descriptionTv.setText(object.getDescription());
        directionTv.setText(object.getLocation());
        int likes=object.getLikes()!=null?publications.get(i).getLikes().size():0;
        numLikesTv.setText(likes+"");
        if(likes>0){
            likeBtn.setBackgroundResource(R.drawable.star_for_like);
        } else {
            likeBtn.setBackgroundResource(R.drawable.star_without_like);
        }
        if(object.getState().equals(Publication.TO_DO)){
            checkImg.setBackgroundResource(R.drawable.check_request);
        } else if(object.getState().equals(Publication.IN_PROGRESS)) {
            checkImg.setBackgroundResource(R.drawable.checked_process);
        } else {
            checkImg.setBackgroundResource(R.drawable.checked_succes);
        }
        if(object.getImage()!=null){
            pictureImg.setImageBitmap(publications.get(i).getImage());
        }

        inProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inProgressBtn.getText().toString().equalsIgnoreCase("en proceso")){
                    rest.changeState(object, Publication.IN_PROGRESS, res);
                    inProgressBtn.setText(R.string.to_finish);
                } else if(inProgressBtn.getText().toString().equalsIgnoreCase("terminar")){
                    rest.changeState(object, Publication.DONE, res);
                    inProgressBtn.setEnabled(false);
                    inProgressBtn.setText(R.string.done);
                }

            }
        });
        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HuecosColombiaApp.getContext(), MessageActivity.class);
                intent.putExtra("dest",object.getUser());
                HuecosColombiaApp.getContext().startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }

    public void setList(LinkedList<Publication> publications){
        this.publications = publications;
    }

    public void addElement(Publication image){
        if(publications.size()>30){
            publications.pop();
        }
        publications.add(image);
        notifyDataSetChanged();
    }
}
