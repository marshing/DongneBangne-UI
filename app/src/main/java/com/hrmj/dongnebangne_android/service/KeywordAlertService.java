package com.hrmj.dongnebangne_android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;

/**
 * Created by office on 2017-10-15.
 */

public class KeywordAlertService  {
//    private MqttClient client;
//    private ArrayList<String> tags;
//    private String roomtitle, roomKeyword;
//
//    KeywordAlertService(ArrayList<String> tags){
//        this.tags = tags;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        try {
//            client = new MqttClient("tcp://m10.cloudmqtt.com:18670", MeetingActivity.me.getEmail()+"TAG", new MemoryPersistence());
//            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//            mqttConnectOptions.setPassword("Zlx8aq_Cm6Ev".toCharArray());
//            mqttConnectOptions.setUserName("stpbqlzr");
//            client.connect(mqttConnectOptions);
//            Log.d(this.getClass().getName(), "MQTT : tcp://192.168.35.91:1883 연결되었습니다.");
//            client.setCallback(new MqttCallback() {
//                @Override
//                public void connectionLost(Throwable cause) {
//
//                }
//
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    roomtitle = message.getPayload().toString();
//                    roomKeyword = topic;
//                    Log.d(getClass().getName(), topic + "방이 새로 추가 : " + roomtitle);
//                    handler.sendEmptyMessage(0);
//
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken token) {
//
//                }
//            });
//
//            for(int i =0; i<MeetingActivity.me.getKeyword().size(); i++) {
//                client.subscribeWithResponse("keyword/" + MeetingActivity.me.getKeyword().get(i), 1);
//            }
//
//        }catch (MqttException e){
//            e.printStackTrace();
//            Log.d(this.getClass().getName() + "_subsclibe", "MQTT : tcp://192.168.35.91:1883 연결끊김 : 에러");
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, @IntDef(value = {Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY}, flag = true) int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Toast.makeText(context, roomKeyword + "방이 새로 추가 : " + roomtitle, Toast.LENGTH_SHORT).show();
//        }
//    };
//    @Override
//    public void run() {
//        super.run();
//
//    }
//
//    public MqttClient getClient() {
//        return client;
//    }
//
//    public void setClient(MqttClient client) {
//        this.client = client;
//    }
}
