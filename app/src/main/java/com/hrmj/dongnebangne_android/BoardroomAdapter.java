package com.hrmj.dongnebangne_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by office on 2017-08-30.
 */

public class BoardroomAdapter extends BaseAdapter {

    static ArrayList<Post> m_List;  //테스트위한 static

    BoardroomAdapter(){
        m_List = new ArrayList<>();
    }

    static public void add(Post post){
        m_List.add(post);
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
        final Context context = parent.getContext();

        TextView tv_boardTitle, tv_boardContent, tv_thumbCount, tv_recommandCount, tv_boardDate;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm");

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_boardroomitem, parent, false);
        }

        tv_boardTitle = (TextView)convertView.findViewById(R.id.tv_boardTitle);
        tv_boardContent = (TextView)convertView.findViewById(R.id.tv_boardContent);
        tv_thumbCount = (TextView)convertView.findViewById(R.id.tv_thumbCount);
        tv_recommandCount = (TextView)convertView.findViewById(R.id.tv_recommandCount);
        tv_boardDate = (TextView)convertView.findViewById(R.id.tv_boardDate);

        Post boardroom = m_List.get(position);

        tv_boardTitle.setText(boardroom.title);
        tv_boardContent.setText(boardroom.content);
        tv_thumbCount.setText(""+boardroom.thumb);
        tv_recommandCount.setText(""+boardroom.commentList.size());
        tv_boardDate.setText(df.format(boardroom.createdDate));


        return convertView;
    }
}


