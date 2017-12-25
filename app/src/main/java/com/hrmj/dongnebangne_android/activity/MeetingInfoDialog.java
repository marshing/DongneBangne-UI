package com.hrmj.dongnebangne_android.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.chatroom.MeetingManager;
import com.hrmj.dongnebangne_android.service.PrettyTime;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by marsh on 2017-12-25.
 */

public class MeetingInfoDialog extends Dialog {

    private Chatroom room;
    private TextView tv_title, tv_date, tv_peoplenum, tv_type, tv_tag, tv_peoplenum2;
    private TextView tv_joinuser[] = new TextView[8];
    private ImageButton ib_join, ib_cancel;

    MeetingInfoDialog(Context context, Chatroom chatroom){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.room = chatroom;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetinginfo);

        tv_title = (TextView)findViewById(R.id.tv_info_roomtitle);
        tv_date = (TextView)findViewById(R.id.tv_info_roomdate);
        tv_peoplenum = (TextView)findViewById(R.id.tv_info_roompeoplenum);
        tv_type = (TextView)findViewById(R.id.tv_info_type);
        tv_tag = (TextView)findViewById(R.id.tv_info_roomtag);
        tv_peoplenum2 = (TextView)findViewById(R.id.tv_info_roompeoplenum2);
        ib_join = (ImageButton)findViewById(R.id.ib_join);
        ib_cancel = (ImageButton)findViewById(R.id.ib_cancel);

        tv_joinuser[0] = (TextView) findViewById(R.id.tv_info_joinuser1);
        tv_joinuser[1] = (TextView) findViewById(R.id.tv_info_joinuser2);
        tv_joinuser[2] = (TextView) findViewById(R.id.tv_info_joinuser3);
        tv_joinuser[3] = (TextView) findViewById(R.id.tv_info_joinuser4);
        tv_joinuser[4] = (TextView) findViewById(R.id.tv_info_joinuser5);
        tv_joinuser[5] = (TextView) findViewById(R.id.tv_info_joinuser6);
        tv_joinuser[6] = (TextView) findViewById(R.id.tv_info_joinuser7);
        tv_joinuser[7] = (TextView) findViewById(R.id.tv_info_joinuser8);

        tv_title.setText(room.getTitle());
        tv_date.setText(PrettyTime.formatTimeString(room.getCreatedDate()));
        tv_peoplenum.setText(""+room.getPeoples().size()+" members");
        tv_tag.setText(room.getHashtags().toString());
        tv_peoplenum2.setText("" + room.getPeoples().size()+"/8 명");


        for(int i=0; i<room.getPeoples().size(); i++){
            tv_joinuser[i].setText(room.getPeoples().get(i));
        }


        switch(room.getType()){
            case 0:
            default:
                tv_type.setText("빠른 / 밥 친구 모임");
            case 1:
                tv_type.setText("일반 / 밥 친구 모임");
            case 2:
                tv_type.setText("빠른 / 대화 모임");
            case 3:
                tv_type.setText("일반 / 대화 모임");
        }

        ib_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ib_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getContext(), ChatActivity.class);
                Retrofit retrofit = new Retrofit
                        .Builder()
                        .baseUrl(ArticleManager.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MeetingManager meetingManager = retrofit.create(MeetingManager.class);
                try{
                    Call<Chatroom> meetings = meetingManager.enterMeeting(""+room.getMeetingId(), SplashActivity.me.getEmail() );
                    Log.d(getClass().getName(), "Retrofit is connecting");
                    meetings.enqueue(new Callback<Chatroom>() {
                        @Override
                        public void onResponse(Response<Chatroom> response, Retrofit retrofit) {
                            Log.d(getClass().getName(), "Retrofit response is success");
                            Log.d(getClass().getName(), ""+response.code());
                            if(response.code()==200){
                                Log.d(getClass().getName(), "ok");
                                intent.putExtra("meetingId", ""+room.getMeetingId());
                                intent.putExtra("title", room.getTitle());
                                getContext().startActivity(intent);
                                dismiss();
                            }else {
                                Toast.makeText(getContext(), "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);

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
        });







    }
}
