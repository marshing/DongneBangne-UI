package com.hrmj.dongnebangne_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.navdrawer.SimpleSideDrawer;

import java.util.Date;

/**
 * Created by office on 2017-08-01.
 */

public class MeetingActivity extends AppCompatActivity implements OnMapReadyCallback{
    private SimpleSideDrawer slide_menu;
    private ImageButton ib_menu, ib_search, ib_maps;
    private TextView tv_menutitle;
    private Toolbar toolbar;
    private ListView lv_chatroom;
    private LinearLayout l_maps;
    private FrameLayout frame;
    private SwipeRefreshLayout refresh;
    private int toggle = 0;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    ChatroomAdapter chatroomAdapter;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        backPressCloseHandler = new BackPressCloseHandler(this);

        chatroomAdapter = new ChatroomAdapter();
        lv_chatroom = (ListView) findViewById(R.id.lv_chatroom);

        lv_chatroom.setAdapter(chatroomAdapter);

        chatroomAdapter.add(new User("송민지", "~", "~", new Date()), 8, "미적 공부 같이하실분 모집합니다!");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "같이 주말마다 토익공부할 스터디원 구해요");
        chatroomAdapter.add(new User("김철수", "~", "~", new Date()), 8, "저녁에 초밥 같이 먹으실분");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 1");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 2");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 3");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 4");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 5");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 6");
        chatroomAdapter.add(new User("홍길동", "~", "~", new Date()), 8, "방 7");

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
        ib_maps = (ImageButton)findViewById(R.id.ib_maps);
        tv_menutitle = (TextView) findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("모임");
        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.layout_leftmenu);
        frame = (FrameLayout)findViewById(R.id.l_frame);
        l_maps = (LinearLayout)findViewById(R.id.l_maps);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        frame.removeView(l_maps);

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
                        overridePendingTransition(0,0);
                        slide_menu.toggleLeftDrawer();
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

        ib_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frame.removeViewAt(0);
                if (toggle == 0) {
                    frame.addView(l_maps);
                    toggle = 1;
                }
                else {
                    frame.addView(refresh);
                    toggle = 0;
                }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SNUT = new LatLng(37.631916, 127.077516);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SNUT);
        markerOptions.title("서울과학기술대학교");
        markerOptions.snippet("국립대학교");
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SNUT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
