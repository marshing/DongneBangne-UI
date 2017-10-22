package com.hrmj.dongnebangne_android.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;

import java.util.ArrayList;

/**
 * Created by office on 2017-08-05.
 */

public class ChatroomAdapter extends BaseAdapter {


    private ArrayList<Chatroom> m_List;

    public ChatroomAdapter(){
        m_List = new ArrayList();
    }

    public void add(Chatroom chatroom){
        m_List.add(chatroom);
    }

    public void remove(int _position){
        m_List.remove(_position);
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    public int getSize() {
        return m_List.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        TextView tv_roomtitle;
        TextView tv_joinuser[] = new TextView[8];

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_chatroomitem, parent, false);
        }

        tv_roomtitle = (TextView) convertView.findViewById(R.id.tv_roomtitle);
        tv_joinuser[0] = (TextView) convertView.findViewById(R.id.tv_joinuser1);
        tv_joinuser[1] = (TextView) convertView.findViewById(R.id.tv_joinuser2);
        tv_joinuser[2] = (TextView) convertView.findViewById(R.id.tv_joinuser3);
        tv_joinuser[3] = (TextView) convertView.findViewById(R.id.tv_joinuser4);
        tv_joinuser[4] = (TextView) convertView.findViewById(R.id.tv_joinuser5);
        tv_joinuser[5] = (TextView) convertView.findViewById(R.id.tv_joinuser6);
        tv_joinuser[6] = (TextView) convertView.findViewById(R.id.tv_joinuser7);
        tv_joinuser[7] = (TextView) convertView.findViewById(R.id.tv_joinuser8);

        Chatroom chatroom = m_List.get(position);

        tv_roomtitle.setText(chatroom.getTitle());
        for(int i=0; i<chatroom.getJoinUserList().size(); i++){
                tv_joinuser[i].setText(chatroom.getJoinUserList().get(i).getName());
        }


        return convertView;
    }

}
