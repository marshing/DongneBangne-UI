package com.hrmj.dongnebangne_android.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.comment.Comment;
import com.hrmj.dongnebangne_android.post.Post;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.User;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by office on 2017-09-02.
 */

public class RecommendAdapter extends BaseAdapter {
    private List<Comment> m_List;

    public RecommendAdapter(Post post) {
        m_List = post.getCommentList();
    }

    public void add(User _user, String _content){
        m_List.add(new Comment(_user, _content));
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
        TextView tv_recommendUser, tv_recommend, tv_recommendDate;
        SimpleDateFormat df = new SimpleDateFormat("MM/dd hh:mm");

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_recommenditem, parent, false);
        }

        tv_recommendUser = (TextView)convertView.findViewById(R.id.tv_recommendUser);
        tv_recommend = (TextView)convertView.findViewById(R.id.tv_recommend);
        tv_recommendDate = (TextView)convertView.findViewById(R.id.tv_recommendDate);

        Comment recommend = m_List.get(position);

        tv_recommendUser.setText(recommend.getUser().getName());
        tv_recommend.setText(recommend.getContent());
        tv_recommendDate.setText(df.format(recommend.getCreatedDate()));

        return convertView;
    }
}
