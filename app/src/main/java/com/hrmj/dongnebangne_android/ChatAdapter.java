package com.hrmj.dongnebangne_android;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by office on 2017-08-01.
 */

public class ChatAdapter extends BaseAdapter {
    public class ListContents{
        String msg;
        int type;
        ListContents(String _msg, int _type)
        {
            this.msg = _msg;
            this.type = _type;
        }
    }

    private ArrayList m_List;

    public ChatAdapter(){
        m_List = new ArrayList();
    }

    public void add(String _msg, int _type){
        m_List.add(new ListContents(_msg, _type));
    }

    public void remove(int _position) {
        m_List.remove(_position);
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
    public int getCount() {
        return m_List.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView    text = null;
        CustomHolder    holder = null;
        LinearLayout    layout = null;
        View    viewRight = null;
        View    viewLeft = null;

        //리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 convertView가 null인상태로 들어옴
        if(convertView == null) {
            //view 가 null일경우 커스텀레이아웃
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);

            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            text = (TextView) convertView.findViewById(R.id.text);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);

            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            convertView.setTag(holder);
        }
        else {
           holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
        }

        text.setText(((ListContents)m_List.get(position)).msg);

        if(((ListContents)m_List.get(position)).type == 0) {
            text.setBackgroundResource(R.drawable.your_bubble);
            text.setTextColor(Color.parseColor("#ffffff"));
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }
        else if(((ListContents)m_List.get(position)).type == 1) {
            text.setBackgroundResource(R.drawable.my_bubble);
            text.setTextColor(Color.parseColor("#000000"));
            layout.setGravity(Gravity.RIGHT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
        }
        if(((ListContents)m_List.get(position)).type == 2) {
            text.setBackgroundColor(Color.parseColor("#ffffffff"));
            layout.setGravity(Gravity.CENTER);
            viewRight.setVisibility(View.VISIBLE);
            viewLeft.setVisibility(View.VISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "리스트 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context,"리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return convertView;
    }

    private  class CustomHolder {
        TextView m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
    }
}
