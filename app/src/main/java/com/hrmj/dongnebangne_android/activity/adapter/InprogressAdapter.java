package com.hrmj.dongnebangne_android.activity.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.chatroom.MeetingManager;
import com.hrmj.dongnebangne_android.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-09-04.
 */

public class InprogressAdapter extends BaseAdapter {


    private List<Chatroom> m_List=new ArrayList<>();
    Activity activity;
    private Retrofit retrofit;
    private MeetingManager meetingManager;
    private ProgressDialog mProgressDialog;

    public InprogressAdapter(Activity parentactivity){
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
                        m_List=response.body();
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
        for(int i=0; i<chatroom.getPeoples().size(); i++){
            tv_joinuser[i].setText(chatroom.getPeoples().get(i));
        }


        return convertView;
    }
}
