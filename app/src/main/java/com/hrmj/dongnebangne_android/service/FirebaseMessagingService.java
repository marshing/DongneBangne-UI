package com.hrmj.dongnebangne_android.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.activity.MeetingActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by office on 2017-11-09.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMessageService";
    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAmYZJrdU:APA91bE3mD5U2lfPwZJzbop8xfpQnmDV62OsJQzKAn5MrdbvJaeuRfkWtc8YTVcYu-671dk8tL6OWBODi1JphqNuwlehhb1mp2gMeVmtcqhA2j8qZMq5kTinNKjV5__cv1AsNHyILFXs";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendPushNotification(remoteMessage.getNotification().getBody());
    }
    public void sendPostToFCM(List<String> keyword, String roomtitle){
        final String message = "등록한 키워드를 태그한 방이 열렸습니다.\ntitle : " + roomtitle;
        String condition = "";

        if(keyword.size()>=2) {
            for (int i = 0; i < keyword.size() - 1; i++) {
                condition += "\'" + keyword.get(i) + "\' in topics||";
            }
            condition += "\'" + keyword.get(keyword.size() - 1) + "\' in topics";
        }
        else if(keyword.size()==1){
            condition += "\'" + keyword.get(0) + "\' in topics";
        }
        else {}
        final String topic = condition;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject notification = new JSONObject();
                    JSONObject root = new JSONObject();
                    notification.put("body", message);
                    notification.put("title", "title");
                    root.put("notification", notification);
                    root.put("condition", topic);

                    URL Url = new URL(FCM_MESSAGE_URL);
                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestProperty("Content-type", "application/json");
                    OutputStream os = conn.getOutputStream();
                    os.write(root.toString().getBytes("utf-8"));
                    os.flush();
                    conn.getResponseCode();
                    Log.d(getClass().getName(), "response : " +
                            conn.getResponseCode());

                }catch (Exception e){e.printStackTrace();}

            }
        }).start();

    }
    private void sendPushNotification(String message){
        Log.d(getClass().getName(), "received message:" + message);
        Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.dongnebange_ic);
        Bitmap bitmap = drawable.getBitmap();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(bitmap)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255, 500, 2000)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP,"TAG");
        wakeLock.acquire(5000);

        notificationManager.notify(0,notificationBuilder.build());

    }
}
