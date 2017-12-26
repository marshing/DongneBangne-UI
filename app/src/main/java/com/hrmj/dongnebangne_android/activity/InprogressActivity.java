package com.hrmj.dongnebangne_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.activity.adapter.InprogressAdapter;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.service.BackPressCloseHandler;
import com.hrmj.dongnebangne_android.R;
import com.navdrawer.SimpleSideDrawer;

/**
 * Created by office on 2017-08-05.
 */

public class InprogressActivity extends AppCompatActivity {
    private SimpleSideDrawer slide_menu;
    private ImageButton ib_menu, ib_search;
    private TextView tv_menutitle;
    private Toolbar toolbar;
    private ListView lv_inprogress;
    private BackPressCloseHandler backPressCloseHandler;
    private InprogressAdapter inprogressAdapter;
    private boolean isSearch=false;
    private EditText et_search;
    private InputMethodManager imm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inprogress);


        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        backPressCloseHandler = new BackPressCloseHandler(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_menu = (ImageButton) findViewById(R.id.ib_menu);
        ib_search = (ImageButton) findViewById(R.id.ib_submenu);
        tv_menutitle = (TextView) findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("참여중인 모임");
        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.layout_leftmenu);
        et_search = (EditText)findViewById(R.id.et_inp_search);

        lv_inprogress = (ListView)findViewById(R.id.lv_inprogress);
        inprogressAdapter = new InprogressAdapter(this, SplashActivity.me.getEmail());
        lv_inprogress.setAdapter(inprogressAdapter);

        lv_inprogress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                Chatroom room = (Chatroom) inprogressAdapter.getItem(i);
                intent.putExtra("meetingId", ""+room.getMeetingId());
                intent.putExtra("title", room.getTitle());
                startActivity(intent);
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

                        Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
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

                        slide_menu.toggleLeftDrawer();
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
                    ib_search.setBackground(ContextCompat.getDrawable(InprogressActivity.this,R.drawable.ic_search_black_24dp));
                    tv_menutitle.setVisibility(View.VISIBLE);
                    et_search.setVisibility(View.INVISIBLE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }else{
                    isSearch = true;
                    ib_search.setBackground(ContextCompat.getDrawable(InprogressActivity.this,R.drawable.ic_close_black_24dp));
                    tv_menutitle.setVisibility(View.INVISIBLE);
                    et_search.setVisibility(View.VISIBLE);
                    et_search.requestFocus();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
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
                    lv_inprogress.setFilterText(filterText);
                }else{
                    lv_inprogress.clearTextFilter();
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
