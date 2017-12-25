package com.hrmj.dongnebangne_android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.service.DeleteTokenService;
import com.hrmj.dongnebangne_android.user.User;
import com.hrmj.dongnebangne_android.user.UserManager;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-11-07.
 */

// 로딩 및 변수 초기화 액티비티
public class SplashActivity extends AppCompatActivity{
    static User me;
    Retrofit retrofit;
    UserManager userManager;    // 로그인 유저 정보 받아옴

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent extraintent = getIntent();

        Intent service = new Intent(this, DeleteTokenService.class);
        startService(service);


        retrofit = new Retrofit
                .Builder()
                .baseUrl(UserManager.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userManager = retrofit.create(UserManager.class);

        try {
            mProgressDialog = ProgressDialog.show(SplashActivity.this, "", "잠시만 기다려 주세요...", true);
            Call<User> user = userManager.searchUser(extraintent.getStringExtra("email"));
            Log.d(getClass().getName(), "Retrofit is connecting");

            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    me = response.body();
                    Log.d(getClass().getName(), "Retrofit on Resp");
                    Log.d(getClass().getName(), ""+response.code());
                    if(response.code()==200){
                        if(me.getHastags()!=null) {
                            for (int i = 0; i < me.getHastags().size(); i++){
                                FirebaseMessaging.getInstance().subscribeToTopic(me.getHastags().get(i));
                                Log.d(getClass().getName(), me.getHastags().get(i) + "등록");
                            }
                        }
                        mProgressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MeetingActivity.class));
                        finish();
                    }
                    else{
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(getClass().getName(), "Retrofit connecting is failure");

                }
            });
        }catch(Exception e){
            Log.d(getClass().getName(), "Retrofit connecting is fail");
        }




    }
}
