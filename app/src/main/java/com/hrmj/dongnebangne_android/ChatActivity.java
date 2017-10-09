package com.hrmj.dongnebangne_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by office on 2017-08-01.
 */

public class ChatActivity extends AppCompatActivity {
    private ListView m_ListView;
    private ImageButton ib_back, ib_info, ib_send;
    private EditText et_message;
    private TextView tv_title;
    private Toolbar toolbar;
    ChatAdapter m_Adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_back = (ImageButton)findViewById(R.id.ib_menu);
        ib_back.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_back));
        ib_info = (ImageButton)findViewById(R.id.ib_submenu);
        ib_info.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_info));
        tv_title = (TextView)findViewById(R.id.tv_menutitle);
        tv_title.setText("임시 채팅방 제목");  //채팅방제목변수 넣기

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "채팅방 정보", Toast.LENGTH_SHORT).show();
            }
        });


        m_Adapter = new ChatAdapter();
        m_ListView = (ListView) findViewById(R.id.listView1);
        m_ListView.setAdapter(m_Adapter);
        ib_send = (ImageButton) findViewById(R.id.ib_send);
        et_message = (EditText) findViewById(R.id.et_message);

        m_Adapter.add("상대방 내용 1",0);
        m_Adapter.add("상대방 내용 2",0);
        m_Adapter.add("나의 내용 1",1);
        m_Adapter.add("상대방 내용 3",0);
        m_Adapter.add("상대방 내용 4\n444444444444444",0);
        m_Adapter.add("상대방 내용 5",0);
        m_Adapter.add("나의 내용 2",1);
        m_Adapter.add("2015/11/20",2);
        m_Adapter.add("나의 내용 3",1);
        m_Adapter.add("나의 내용 4",1);
        m_Adapter.add("나의 내용 5",1);

        //항상 맨 밑으로 스크롤
        m_ListView.setSelection(m_Adapter.getCount() - 1);

        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Adapter.add(et_message.getText().toString(),1);
                et_message.setText("");
                m_Adapter.notifyDataSetChanged();
                return;

            }
        });


    }
}
