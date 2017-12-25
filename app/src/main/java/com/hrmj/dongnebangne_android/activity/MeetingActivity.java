package com.hrmj.dongnebangne_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hrmj.dongnebangne_android.service.BackPressCloseHandler;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.activity.adapter.ChatroomAdapter;
import com.hrmj.dongnebangne_android.R;
import com.navdrawer.SimpleSideDrawer;

/**
 * Created by office on 2017-08-01.
 */

public class MeetingActivity extends AppCompatActivity implements OnMapReadyCallback{
    private SimpleSideDrawer slide_menu;
    private ImageButton ib_menu, ib_search, ib_maps, ib_plus;
    private TextView tv_menutitle;
    private Toolbar toolbar;
    private ListView lv_chatroom;
    private EditText et_search;
    private LinearLayout l_maps;
    private FrameLayout frame;
    private SwipeRefreshLayout refresh;
    private int toggle = 0;
    private SupportMapFragment mapFragment;
    private ChatroomAdapter chatroomAdapter;
    private BackPressCloseHandler backPressCloseHandler;
    static GoogleMap mMap;
    private RadioGroup rg_eort, rg_bornor;
    private RadioButton radio_eat, radio_talk, radio_busy, radio_normal;
    private boolean isSearch=false;
    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        backPressCloseHandler = new BackPressCloseHandler(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chatroomAdapter = new ChatroomAdapter(this);
        chatroomAdapter.setType(0);
        lv_chatroom = (ListView) findViewById(R.id.lv_chatroom);
        lv_chatroom.setAdapter(chatroomAdapter);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        ib_menu = (ImageButton) findViewById(R.id.ib_menu);
        ib_search = (ImageButton) findViewById(R.id.ib_submenu);
        ib_maps = (ImageButton)findViewById(R.id.ib_maps);
        ib_plus = (ImageButton)findViewById(R.id.ib_plus) ;
        tv_menutitle = (TextView) findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("모임");
        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.layout_leftmenu);
        frame = (FrameLayout)findViewById(R.id.l_frame);
        l_maps = (LinearLayout)findViewById(R.id.l_maps);
        rg_eort = (RadioGroup)findViewById(R.id.rg_eatortalk);
        rg_bornor = (RadioGroup)findViewById(R.id.rg_busyornor);
        radio_eat = (RadioButton)findViewById(R.id.radio_eat);
        radio_talk = (RadioButton)findViewById(R.id.radio_talk);
        radio_busy = (RadioButton)findViewById(R.id.radio_busy);
        radio_normal = (RadioButton)findViewById(R.id.radio_normal);
        et_search = (EditText)findViewById(R.id.et_search);
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        frame.removeView(l_maps);
        refresh = (SwipeRefreshLayout)findViewById(R.id.l_refreshMeeting);

        lv_chatroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), MeetingInfoDialog.class);
                Chatroom room = (Chatroom)(chatroomAdapter.getItem(position));
                MeetingInfoDialog meetingInfoDialog = new MeetingInfoDialog(MeetingActivity.this, room);
                meetingInfoDialog.show();
            }
        });

        radio_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg_bornor.getCheckedRadioButtonId()==R.id.radio_busy){
                    chatroomAdapter.setType(0);
                    chatroomAdapter.notifyDataSetChanged();
                }else {
                    chatroomAdapter.setType(1);
                    chatroomAdapter.notifyDataSetChanged();
                }
            }
        });
        radio_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg_bornor.getCheckedRadioButtonId()==R.id.radio_busy){
                    chatroomAdapter.setType(2);
                    chatroomAdapter.notifyDataSetChanged();
                }else {
                    chatroomAdapter.setType(3);
                    chatroomAdapter.notifyDataSetChanged();
                }

            }
        });
        radio_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg_eort.getCheckedRadioButtonId()==R.id.radio_eat){
                    chatroomAdapter.setType(0);
                    chatroomAdapter.notifyDataSetChanged();
                }else {
                    chatroomAdapter.setType(2);
                    chatroomAdapter.notifyDataSetChanged();
                }
            }
        });
        radio_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg_eort.getCheckedRadioButtonId()==R.id.radio_eat){
                    chatroomAdapter.setType(1);
                    chatroomAdapter.notifyDataSetChanged();
                }else {
                    chatroomAdapter.setType(3);
                    chatroomAdapter.notifyDataSetChanged();
                }

            }
        });

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide_menu.toggleLeftDrawer();
                //여기에 사이드 뷰 내용 적음됨
                Button bt_meeting = (Button) findViewById(R.id.bt_meeting);
                Button bt_board = (Button) findViewById(R.id.bt_board);
                Button bt_inprogress = (Button) findViewById(R.id.bt_inprogress);
                Button bt_setting = (Button) findViewById(R.id.bt_setting);
                TextView tv_leftname = (TextView)findViewById(R.id.tv_leftname);
                TextView tv_leftinfo = (TextView)findViewById(R.id.tv_leftinfo);

                tv_leftname.setText(SplashActivity.me.getName());
                tv_leftinfo.setText(SplashActivity.me.getMajor());


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
                if(isSearch){
                    isSearch = false;
                    ib_search.setBackground(ContextCompat.getDrawable(MeetingActivity.this,R.drawable.ic_search_black_24dp));
                    tv_menutitle.setVisibility(View.VISIBLE);
                    et_search.setVisibility(View.INVISIBLE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }else{
                    isSearch = true;
                    ib_search.setBackground(ContextCompat.getDrawable(MeetingActivity.this,R.drawable.ic_close_black_24dp));
                    tv_menutitle.setVisibility(View.INVISIBLE);
                    et_search.setVisibility(View.VISIBLE);
                    et_search.requestFocus();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });

        //검색
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterText = editable.toString();
                if(filterText.length()>0){
                    lv_chatroom.setFilterText(filterText);
                }else{
                    lv_chatroom.clearTextFilter();
                }

            }
        });

        //과기맵
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

        ib_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewChatDialog.class);
                startActivity(intent);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chatroomAdapter.refresh();
                chatroomAdapter.notifyDataSetChanged();
                mapFragment.getMapAsync(MeetingActivity.this);
                refresh.setRefreshing(false);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng SNUT = new LatLng(37.631916, 127.077516);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SNUT);
        markerOptions.title("서울과학기술대학교");
        markerOptions.snippet("국립대학교");
        googleMap.addMarker(markerOptions);
//
//        for(int i=0; i<MeetingActivity.chatroomAdapter.getSize()-1;i++){
//            Chatroom chatroom = (Chatroom)MeetingActivity.chatroomAdapter.getItem(i);
//            LatLng temp = chatroom.getLatLng();
//            markerOptions.position(temp);
//            markerOptions.title(chatroom.getTitle());
//            if(chatroom.getPlace() == null)
//                markerOptions.snippet("");
//            else {
//                markerOptions.snippet(chatroom.getPlace().getName().toString());
//            }
//            googleMap.addMarker(markerOptions);
//
//        }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SNUT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
