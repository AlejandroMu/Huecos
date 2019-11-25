package com.example.huecoscolombia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewAdapter extends BaseAdapter {

    private ArrayList<New> news;

    public NewAdapter () {
        news = new ArrayList<>();
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
        return v;
    }
}
