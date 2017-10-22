package com.hrmj.dongnebangne_android.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.post.Post;
import com.hrmj.dongnebangne_android.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by office on 2017-08-30.
 */

public class BoardroomAdapter extends BaseAdapter {

    public static ArrayList<Post> m_List;  //테스트위한 static

    public BoardroomAdapter(){
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

        tv_boardTitle.setText(boardroom.getTitle());
        tv_boardContent.setText(boardroom.getContent());
        tv_thumbCount.setText(""+boardroom.getThumb());
        tv_recommandCount.setText(""+boardroom.getCommentList().size());
        tv_boardDate.setText(df.format(boardroom.getCreatedDate()));


        return convertView;
    }
}


