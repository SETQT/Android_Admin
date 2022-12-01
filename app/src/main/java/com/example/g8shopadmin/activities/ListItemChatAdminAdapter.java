package com.example.g8shopadmin.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.MyChatAdmin;

import java.util.ArrayList;

public class ListItemChatAdminAdapter extends ArrayAdapter<MyChatAdmin> {
    ArrayList<MyChatAdmin> chat = new ArrayList<MyChatAdmin>();


    public ListItemChatAdminAdapter(Context context, int resource, ArrayList<MyChatAdmin> objects) {
        super(context, resource, objects);
        this.chat = objects;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_list_view_admin_chat, null);

        TextView username = (TextView) v.findViewById(R.id.name_user_admin_list_chat);
        TextView last_message = (TextView) v.findViewById(R.id.last_messange_admin_list_chat);
        TextView last_time = (TextView) v.findViewById(R.id.last_time_admin_list_chat);
        ImageView img = (ImageView) v.findViewById(R.id.custom_picture_admin_list_chat) ;

        username.setText(chat.get(position).getUserName());
        last_message.setText(chat.get(position).getLast_message());
        last_time.setText(chat.get(position).getLast_time());
        img.setImageResource(chat.get(position).getImage());
        return v;
    }
}
