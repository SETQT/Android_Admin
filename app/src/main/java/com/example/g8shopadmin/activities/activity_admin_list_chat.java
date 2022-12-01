package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.g8shopadmin.activities.MyChatAdmin;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.adapters.ListItemChatAdminAdapter;

import java.util.ArrayList;

public class activity_admin_list_chat extends Activity {

    ListView listChat;
    ArrayList<MyChatAdmin> ListChatArray =new ArrayList<MyChatAdmin>();

    String[] username = {"3T Mãi đỉnh", "3T Mãi đỉnh", "3T Mãi đỉnh", "3T Mãi đỉnh", "3T Mãi đỉnh", "3T Mãi đỉnh"};
    String[] last_message = {"Trầm văn thất","em yêu anh đi","mình chia tay đi","mai anh chở em đi học nhé","mình chia tay đi","mình chia tay đi" };
    String[] last_time = {"13:01","13:02","13:03","13:04","13:05","13:06" };

    Integer[] image = {R.drawable.avatar_profile, R.drawable.avatar_profile, R.drawable.avatar_profile, R.drawable.avatar_profile,R.drawable.avatar_profile,R.drawable.avatar_profile  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_chat);


        listChat = (ListView) findViewById(R.id.listview_admin_list_chat);

        for (int i=0;i<6;i++){
            ListChatArray.add(new MyChatAdmin(i, username[i],last_message[i],last_time[i],image[i]));
        }
        ListItemChatAdminAdapter myAdapter = new ListItemChatAdminAdapter(this,R.layout.custom_list_view_admin_chat, ListChatArray);
        listChat.setAdapter(myAdapter);

    }
}