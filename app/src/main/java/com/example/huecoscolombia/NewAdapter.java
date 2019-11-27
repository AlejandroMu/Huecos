package com.example.huecoscolombia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Publication;
import com.example.huecoscolombia.Model.entity.PublicationImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NewAdapter extends BaseAdapter {

    public static int PAGE=1;

    private LinkedList<PublicationImage> news;

    public NewAdapter () {
        news = new LinkedList<>();
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
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
        PublicationImage object=news.get(i);
        picture.setImageBitmap(object.getImage());
        numLike.setText(object.getPublication().getLikes()+"");
        date.setText((new Date(object.getPublication().getDate())).toString());
        description.setText(object.getPublication().getDescription());
        return v;
    }
    public void setList(LinkedList<PublicationImage> images){
        news=images;
    }
    public void addElement(PublicationImage image){
        if(news.size()>30){
            news.pop();
        }
        news.add(image);
    }

}
