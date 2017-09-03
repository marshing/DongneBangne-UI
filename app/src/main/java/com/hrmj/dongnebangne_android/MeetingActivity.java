package com.hrmj.dongnebangne_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;

import java.util.Date;

/**
 * Created by office on 2017-08-01.
 */

public class MeetingActivity extends AppCompatActivity{
    private SimpleSideDrawer slide_menu;
    private ImageButton ib_menu, ib_search;
    private TextView tv_menutitle;
    private Toolbar toolbar;
    private ListView lv_chatroom;
    private SwipeRefreshLayout refresh;
    ChatroomAdapter chatroomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        chatroomAdapter = new ChatroomAdapter();
        lv_chatroom = (ListView) findViewById(R.id.lv_chatroom);

        lv_chatroom.setAdapter(chatroomAdapter);

        chatroomAdapter.add(new User("송민지", "~", "~", new Date()), 8, "미적 공부 같이하실분 모집합니다!");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "아버지를 아버지라 부르지 못하고...");

        lv_chatroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_menu = (ImageButton) findViewById(R.id.ib_menu);
        ib_search = (ImageButton) findViewById(R.id.ib_submenu);
        tv_menutitle = (TextView) findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("모임");
        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.layout_leftmenu);

        refresh = (SwipeRefreshLayout)findViewById(R.id.l_refreshMeeting);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide_menu.toggleLeftDrawer();
                //여기에 사이드 뷰 내용 적음됨
                Button bt_meeting = (Button) findViewById(R.id.bt_meeting);
                Button bt_board = (Button) findViewById(R.id.bt_board);
                Button bt_inprogress = (Button) findViewById(R.id.bt_inprogress);
                Button bt_setting = (Button) findViewById(R.id.bt_setting);

                bt_meeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slide_menu.toggleLeftDrawer();
                    }
                });
                bt_board.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                    }
                });
                bt_inprogress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), InprogressActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                    }
                });
                bt_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                    }
                });
            }
        });

        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MeetingActivity.this, "검색", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chatroomAdapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });



    }
}
