package com.hrmj.dongnebangne_android.activity.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.article.Article;
import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.chat.Chat;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.chatroom.MeetingManager;
import com.hrmj.dongnebangne_android.service.PrettyTime;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-08-05.
 */

public class ChatroomAdapter extends BaseAdapter implements Filterable{


    private List<Chatroom> origin_List = new ArrayList<>();
    private List<Chatroom> m_List = new ArrayList<>();
    Activity activity;
    private Retrofit retrofit;
    private MeetingManager meetingManager;
    private  ProgressDialog mProgressDialog;
    private int type;
    private Filter listFilter;

    public ChatroomAdapter(Activity parentactivity){
        this.activity = parentactivity;
        retrofit = new Retrofit
                .Builder()
                .baseUrl(ArticleManager.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        meetingManager = retrofit.create(MeetingManager.class);
        try{
            mProgressDialog = ProgressDialog.show(activity, "", "잠시만 기다려 주세요...", true);
            Call<List<Chatroom>> meetings = meetingManager.allMeeting();
            Log.d(getClass().getName(), "Retrofit is connecting");
            meetings.enqueue(new Callback<List<Chatroom>>() {
                @Override
                public void onResponse(Response<List<Chatroom>> response, Retrofit retrofit) {
                    Log.d(getClass().getName(), "Retrofit response is success");
                    Log.d(getClass().getName(), ""+response.code());
                    mProgressDialog.dismiss();
                    if(response.code()==200){
                        origin_List=response.body();
                        m_List=origin_List;
                        Log.d(getClass().getName(), "MeetingList");
                        notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }else {
                        Toast.makeText(activity, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                        mProgressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(getClass().getName(), "Retrofit resp is fail");
                    mProgressDialog.dismiss();

                }
            });
        }catch (Exception e){
            Log.d(getClass().getName(), "Retrofit is fail");}
    }

    public void refresh(){
        try{
            Call<List<Chatroom>> articles = meetingManager.allMeeting();
            Log.d(getClass().getName(), "Retrofit is connecting");
            articles.enqueue(new Callback<List<Chatroom>>() {
                @Override
                public void onResponse(Response<List<Chatroom>> response, Retrofit retrofit) {
                    Log.d(getClass().getName(), "Retrofit response is success");
                    Log.d(getClass().getName(), ""+response.code());
                    if(response.code()==200){
                        m_List=response.body();
                        Log.d(getClass().getName(), "MeetingList");
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(activity, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(getClass().getName(), "Retrofit resp is fail");

                }
            });
        }catch (Exception e){
            Log.d(getClass().getName(), "Retrofit is fail");}
    }

    public void add(Chatroom chatroom){
        m_List.add(chatroom);
    }

    public void remove(int _position){
        m_List.remove(_position);
    }

    @Override
    public int getCount() {

        int size = 0;

        int fullSize = m_List.size();

        //필터 타입별 보여줄 갯수 정의
        switch (type) {
            case 4:
            default:
                size = fullSize;
                break;
            case 0:
            case 1:
            case 2:
            case 3:
                for(int i=0; i<fullSize; i++){
                    if(m_List.get(i).getType()==type)
                        size++;
                }
                break;

        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        Chatroom temp;

        int itemIndex=0;
        int fullSize=m_List.size();

        switch (type){
            case 4:
                return m_List.get(position);
        }

        for(int i=0;i<fullSize;i++){
            temp = m_List.get(i);
            if(temp.getType()==type){
                if(position==itemIndex)
                    return temp;
                itemIndex++;
            }

        }

        return null;
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

        TextView tv_roomtitle, tv_roomtag, tv_roompeoplenum, tv_date;
        TextView tv_joinuser[] = new TextView[8];

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_chatroomitem, parent, false);
        }

        Chatroom chatroom= (Chatroom) getItem(position);

        String roomtag = new String(chatroom.getHashtags().toString());
        String roompeoplenum = new String(""+chatroom.getPeoples().size()+" members");
        String date = new String(PrettyTime.formatTimeString(chatroom.getCreatedDate()));


        tv_roomtitle = (TextView) convertView.findViewById(R.id.tv_roomtitle);
        tv_date = (TextView) convertView.findViewById(R.id.tv_roomdate);
        tv_roompeoplenum = (TextView) convertView.findViewById(R.id.tv_roompeoplenum);
        tv_roomtag = (TextView) convertView.findViewById(R.id.tv_roomtag);
        tv_joinuser[0] = (TextView) convertView.findViewById(R.id.tv_joinuser1);
        tv_joinuser[1] = (TextView) convertView.findViewById(R.id.tv_joinuser2);
        tv_joinuser[2] = (TextView) convertView.findViewById(R.id.tv_joinuser3);
        tv_joinuser[3] = (TextView) convertView.findViewById(R.id.tv_joinuser4);
        tv_joinuser[4] = (TextView) convertView.findViewById(R.id.tv_joinuser5);
        tv_joinuser[5] = (TextView) convertView.findViewById(R.id.tv_joinuser6);
        tv_joinuser[6] = (TextView) convertView.findViewById(R.id.tv_joinuser7);
        tv_joinuser[7] = (TextView) convertView.findViewById(R.id.tv_joinuser8);

        tv_roomtitle.setText(chatroom.getTitle());
        tv_date.setText(date);
        tv_roompeoplenum.setText(roompeoplenum);
        tv_roomtag.setText(roomtag);
        for(int i=0; i<chatroom.getPeoples().size(); i++){
                tv_joinuser[i].setText(chatroom.getPeoples().get(i));
        }


        return convertView;
    }

    public void setType(int type){
        this.type=type;
    }

    @Override
    public Filter getFilter() {
        if(listFilter==null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if(charSequence==null || charSequence.length()==0){
                results.values = origin_List;
                results.count = origin_List.size();
            }else{
                List<Chatroom> itemList = new ArrayList<>();

                for(Chatroom chatroom : origin_List){
                    if(chatroom.getTitle().toUpperCase().contains(charSequence.toString().toUpperCase()) ||
                            chatroom.getHashtags().toString().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        itemList.add(chatroom);
                    }
                }

                results.values = itemList;
                results.count = itemList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            m_List = (ArrayList<Chatroom>) filterResults.values;

            if(filterResults.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }

        }
    }
}
