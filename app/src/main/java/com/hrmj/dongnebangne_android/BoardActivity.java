package com.hrmj.dongnebangne_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

    private List<Post> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        backPressCloseHandler = new BackPressCloseHandler(this);

        lv_boardroom = (ListView)findViewById(R.id.lv_boardroom);
        items = new ArrayList<>();
        boardroomAdapter = new BoardroomAdapter();
        lv_boardroom.setAdapter(boardroomAdapter);


        boardroomAdapter.add(new Post("게시판 제목 1", "내용1", new Date(2017, 10, 11, 11, 58)));
        boardroomAdapter.add(new Post("게시판 제목 2", "내용 12345677890 \nabcdefghijklmn", new Date(2017, 10, 12, 19, 23)));

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
                intent.putExtra("postNum", position);
                startActivity(intent);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boardroomAdapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        boardroomAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        MyThread th = new MyThread();
//        th.start();
    }
//
//    final Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
//
//            boardroomAdapter = new BoardroomAdapter();
////
////            String[]str = new String[items.size()];
////            for(int i=0; i<str.length; i++){
////                Post dto = items.get(i);
////                //str[i] = dto.getTitle();
////
////                boardroomAdapter.add(dto);
////            }
////            boardroomAdapter.add(new Post("test" , "test"));
//
//            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(BoardActivity.this, android.R.layout.simple_list_item_1, str);
//
//            lv_boardroom.setAdapter(boardroomAdapter);
//            boardroomAdapter.notifyDataSetChanged();
//        }
//    };

//
//    private class MyThread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//
//
//            try{
//
//                Log.d("","로그 테스트");
//                HttpClient http = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost("http://192.168.35.91:8181/dongnebangne_server/json.do");
//                HttpResponse response = http.execute(httpPost);
//                String body = EntityUtils.toString(response.getEntity());
//                Log.d("","로그 테스트1*");
//                JSONObject jsonObject = new JSONObject(body);
//                Log.d("","로그 테스트2");
//                JSONArray jArray = (JSONArray)jsonObject.get("sendData");
//
//                Log.d("","로그 테스트3");
//                for(int i=0; i<jArray.length(); i++){
//
//                    Log.d("","for문 i : "+i);
//                    SimpleDateFormat format = new SimpleDateFormat("MM/dd hh:mm");
//                    JSONObject row = jArray.getJSONObject(i);
//                    Post dto = new Post(row.getString("pTitle"), row.getString("pContent"));
////                dto.setTitle(row.getString("pTitle"));
////                dto.setContent(row.getString("pContent"));
////                String tempDate = row.getString("pCreatedDate");
////                dto.setCreatedDate(format.parse(tempDate));
////                dto.setModifiedDate(format.parse(tempDate));
////                dto.setThumb(row.getInt("pThumb"));
////                dto.setCommentList(new ArrayList<Comment>());
//                    items.add(dto);
//
//                }
//                Log.d("","for문 통과");
//                handler.sendEmptyMessage(0);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }




}
