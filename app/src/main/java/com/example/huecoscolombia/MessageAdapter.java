package com.example.huecoscolombia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.huecoscolombia.Model.entity.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    FirebaseAuth auth;
    private List<Message> messages;

    public MessageAdapter(){
        messages=new ArrayList<>();
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_message,null);
        Message tmp=messages.get(position);
        TextView ms=null;
        TextView date=null;
        if(auth.getCurrentUser().getEmail().equals(tmp.getWriter())) {
            ms = v.findViewById(R.id.message_show_tv_right);
            date = v.findViewById(R.id.message_date_tv_right);
        }else{
            ms = v.findViewById(R.id.message_show_tv);
            date = v.findViewById(R.id.message_date_tv);
        }
        ms.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yy HH:mm");
        String dateF=format.format(new Date(tmp.getDate()));
        ms.setText(tmp.getMessage());
        date.setText(dateF);




        return v;
    }

    public void addMessage(Message ms){
        messages.add(ms);
        notifyDataSetChanged();
    }

    public void setList(List<Message> mes){
        messages=mes;
        notifyDataSetChanged();
    }
}
