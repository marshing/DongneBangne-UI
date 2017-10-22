package com.hrmj.dongnebangne_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.activity.adapter.ChatAdapter;
import com.hrmj.dongnebangne_android.R;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by office on 2017-08-01.
 */

public class ChatActivity extends AppCompatActivity  {
    private ListView m_ListView;
    private ImageButton ib_back, ib_info, ib_send;
    private EditText et_message;
    private TextView tv_title;
    private Toolbar toolbar;
    private String topicTitle;
    ChatAdapter m_Adapter;
    MqttClient client;
    String chatmsg;
    String mqttMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        topicTitle = intent.getStringExtra("topic");


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_back = (ImageButton)findViewById(R.id.ib_menu);
        ib_back.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_back));
        ib_info = (ImageButton)findViewById(R.id.ib_submenu);
        ib_info.setBackground(ContextCompat.getDrawable(this, R.drawable.chat_info));
        tv_title = (TextView)findViewById(R.id.tv_menutitle);
        tv_title.setText(topicTitle);

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


        //항상 맨 밑으로 스크롤
        m_ListView.setSelection(m_Adapter.getCount() - 1);

        // 엔터키로 메시지 전ㅅㅇ
        et_message.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ib_send.performClick();
                    return true;
                }

                return false;
            }
        });


     //   mqtt = new MqttClass(this);

        ib_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatmsg = et_message.getText().toString();
                try{
                    client.publish(topicTitle+ "/" + MeetingActivity.me.getEmail(), new MqttMessage(chatmsg.getBytes()));
                    et_message.setText("");
                    Log.d(this.getClass().getName() + "_publish", "MQTT : simpletest 토픽으로 " + chatmsg + " 가 전달되었습니다.");
                }catch (MqttException e) {
                    e.printStackTrace();
                    Log.d(this.getClass().getName(), "MQTT : 에러");
                }
                return;

            }
        });

        MQTTServer th = new MQTTServer();
        th.start();





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            client.close();
        }catch (MqttException e ) {e.printStackTrace();
            Log.d(this.getClass().getName(), "MQTT : close() error");
        }
    }

    public class MQTTServer extends Thread {

        Handler myhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                m_Adapter.add(mqttMessage, 1);
                m_Adapter.notifyDataSetChanged();
                m_ListView.setSelection(m_Adapter.getCount() - 1);
                Log.d(this.getClass().getName() , "MQTT : msg handler 작동.");

            }
        };
        Handler otherhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                m_Adapter.add(mqttMessage, 0);
                m_Adapter.notifyDataSetChanged();
                m_ListView.setSelection(m_Adapter.getCount() - 1);
                Log.d(this.getClass().getName() , "MQTT : msg handler 작동.");

            }
        };
        @Override
        public void run() {

            try {
                client = new MqttClient("tcp://m10.cloudmqtt.com:18670", MeetingActivity.me.getEmail(), new MemoryPersistence());
                MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                mqttConnectOptions.setPassword("Zlx8aq_Cm6Ev".toCharArray());
                mqttConnectOptions.setUserName("stpbqlzr");
                client.connect(mqttConnectOptions);
                Log.d(this.getClass().getName(), "MQTT : tcp://192.168.35.91:1883 연결되었습니다.");
                client.subscribeWithResponse(topicTitle + "/#", 1, new IMqttMessageListener() {
                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        if (topic.equals(topicTitle + "/" + MeetingActivity.me.getEmail())){
                            mqttMessage = message.toString();
                            Log.d(this.getClass().getName() , "MQTT : msg를 받았습니다.");
                            myhandler.sendEmptyMessage(0);
                        }
                        else {
                            mqttMessage = message.toString();
                            Log.d(this.getClass().getName(), "MQTT : msg를 받았습니다.");
                            otherhandler.sendEmptyMessage(0);
                        }
                    }
                });

            }catch (MqttException e){
                e.printStackTrace();
                Log.d(this.getClass().getName() + "_subsclibe", "MQTT : tcp://192.168.35.91:1883 연결끊김 : 에러");
            }
        }
    }
}
