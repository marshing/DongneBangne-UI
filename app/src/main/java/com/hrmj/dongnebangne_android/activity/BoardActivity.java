package com.hrmj.dongnebangne_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.article.Article;
import com.hrmj.dongnebangne_android.service.BackPressCloseHandler;
import com.hrmj.dongnebangne_android.activity.adapter.BoardroomAdapter;
import com.hrmj.dongnebangne_android.R;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by office on 2017-08-05.
 */

public class BoardActivity extends AppCompatActivity {
    private ImageButton ib_menu, ib_editboard;
    private SimpleSideDrawer slide_menu;
    private TextView tv_menutitle;
    private Toolbar toolbar;
    private ListView lv_boardroom;
    private SwipeRefreshLayout refresh;
    BoardroomAdapter boardroomAdapter;

    private BackPressCloseHandler backPressCloseHandler;

    private List<Article> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        backPressCloseHandler = new BackPressCloseHandler(this);

        lv_boardroom = (ListView)findViewById(R.id.lv_boardroom);
        items = new ArrayList<>();
        boardroomAdapter = new BoardroomAdapter(this);
        lv_boardroom.setAdapter(boardroomAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_menu = (ImageButton) findViewById(R.id.ib_menu);
        ib_editboard = (ImageButton) findViewById(R.id.ib_submenu);
        tv_menutitle = (TextView) findViewById(R.id.tv_menutitle);
        ib_editboard.setBackground(ContextCompat.getDrawable(this,R.drawable.ic_mode_edit_black_24dp));
        tv_menutitle.setText("익명 게시판");

        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.layout_leftmenu);

        refresh = (SwipeRefreshLayout)findViewById(R.id.l_refreshBoard);


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
                        slide_menu.toggleLeftDrawer();
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

        ib_editboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditBoardActivity.class);
                startActivity(intent);
            }
        });

        lv_boardroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BoardroomActivity.class);
                intent.putExtra("articleId", boardroomAdapter.getItem(position).getArticleId());
                startActivity(intent);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boardroomAdapter.refresh();
                refresh.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        boardroomAdapter.refresh();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }





}
