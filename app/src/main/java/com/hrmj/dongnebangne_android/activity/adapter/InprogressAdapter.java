package com.hrmj.dongnebangne_android.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by office on 2017-09-04.
 */

public class InprogressAdapter extends BaseAdapter {

    List<Chatroom> m_List;

    InprogressAdapter(User user) {
        m_List = new ArrayList<>();
        // DB에서 user 가 속해있는 chatroom m_List 추가
        //m_List.add(~);

    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        TextView tv_roomtitle;
        TextView tv_joinuser[] = new TextView[8];

        if(convertView == null) {
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
