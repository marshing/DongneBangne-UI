package com.hrmj.dongnebangne_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by office on 2017-08-01.
 */

public class ChatActivity extends AppCompatActivity {
    private ListView m_ListView;
    private Button bt_send;
    private EditText et_massege;
    ChatAdapter m_Adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        m_Adapter = new ChatAdapter();
        m_ListView = (ListView) findViewById(R.id.listView1);
        m_ListView.setAdapter(m_Adapter);
        bt_send = (Button) findViewById(R.id.bt_send);
        et_massege = (EditText) findViewById(R.id.et_massege);

        m_Adapter.add("이건 뭐지",1);
        m_Adapter.add("쿨쿨",1);
        m_Adapter.add("쿨쿨쿨쿨",0);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("놀자라구나힐힐 감사합니다. 동해물과 백두산이 마르고 닳도록 놀자 놀자 우리 놀자",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",0);
        m_Adapter.add("2015/11/20",2);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);
        m_Adapter.add("재미있게",1);

        //항상 맨 밑으로 스크롤
        m_ListView.setSelection(m_Adapter.getCount() - 1);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Adapter.add(et_massege.getText().toString(),1);
                et_massege.setText("");
                m_Adapter.notifyDataSetChanged();
                return;

            }
        });


    }
}
