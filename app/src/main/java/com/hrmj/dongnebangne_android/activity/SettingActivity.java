package com.hrmj.dongnebangne_android.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.AccountResponse;
import com.hrmj.dongnebangne_android.user.User;
import com.hrmj.dongnebangne_android.user.UserManager;

import java.util.ArrayList;
import java.util.Set;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-08-05.
 */

public class SettingActivity extends AppCompatActivity {
    EditText et_keyword;
    TextView tv_name, tv_major, tv_greeting, tv_deleteaccount, tv_emailaddr;
    ImageButton ib_editmajor, ib_editgreeting, ib_keyword;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton ib_back = (ImageButton)findViewById(R.id.ib_prf_back);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_major = (TextView)findViewById(R.id.tv_major);
        tv_greeting = (TextView)findViewById(R.id.tv_greeting);
        tv_deleteaccount = (TextView)findViewById(R.id.tv_deleteaccount);
        ib_keyword = (ImageButton)findViewById(R.id.ib_keyword);
        tv_emailaddr = (TextView)findViewById(R.id.tv_emailadress);

        tv_name.setText(SplashActivity.me.getName());
        tv_major.setText(SplashActivity.me.getMajor());
        tv_greeting.setText(SplashActivity.me.getGreeting());
        tv_emailaddr.setText(SplashActivity.me.getEmail());

        ib_editmajor = (ImageButton)findViewById(R.id.ib_editmajor);
        ib_editgreeting = (ImageButton)findViewById(R.id.ib_editgreeting);

        ib_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeywordListActivity.class);
                startActivity(intent);
            }
        });

        tv_deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                final TextView alert = new TextView(SettingActivity.this);
                alert.setText("계정을 삭제하시겠습니까?");

                builder.setView(alert);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog = ProgressDialog.show(SettingActivity.this, "", "잠시만 기다려 주세요...", true);

                                Retrofit retrofit = new Retrofit
                                        .Builder()
                                        .baseUrl(UserManager.API_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                UserManager userManager = retrofit.create(UserManager.class);

                                try{
                                    Call<AccountResponse> user = userManager.deleteUser(SplashActivity.me.getEmail());
                                    Log.d(getClass().getName(), "Retrofit is connecting");

                                    user.enqueue(new Callback<AccountResponse>() {
                                        @Override
                                        public void onResponse(Response<AccountResponse> response, Retrofit retrofit) {
                                            AccountResponse accountResponse = response.body();
                                            Log.d(getClass().getName(), "Retrofit on Resp");
                                            Log.d(getClass().getName(), ""+response.code());
                                            if(accountResponse != null)
                                                Log.d(getClass().getName(), accountResponse.getStatus() + " ; " + accountResponse.getStatus());
                                            if(response.code()==200){
                                                mProgressDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                mProgressDialog.dismiss();
                                                Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.d(getClass().getName(), "Retrofit on Failure");
                                            Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }catch(Exception e){
                                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                                    Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();

            }
        });

        ib_editmajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"컴퓨터공학과", "전기정보공학과", "전자IT미디어공학과"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
                dialog.setTitle("자신의 전공을 선택해주세요.")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog = ProgressDialog.show(SettingActivity.this, "", "잠시만 기다려 주세요...", true);
                                final User tempuser = SplashActivity.me;
                                tempuser.setMajor(items[selectedIndex[0]]);
                                Retrofit retrofit = new Retrofit
                                        .Builder()
                                        .baseUrl(UserManager.API_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                UserManager userManager = retrofit.create(UserManager.class);

                                try{
                                    Call<AccountResponse> user = userManager.modifyUser(tempuser.getEmail(), tempuser);
                                    Log.d(getClass().getName(), "Retrofit is connecting");

                                    user.enqueue(new Callback<AccountResponse>() {
                                        @Override
                                        public void onResponse(Response<AccountResponse> response, Retrofit retrofit) {
                                            AccountResponse accountResponse = response.body();
                                            Log.d(getClass().getName(), "Retrofit on Resp");
                                            Log.d(getClass().getName(), ""+response.code());
                                            if(accountResponse != null)
                                                Log.d(getClass().getName(), accountResponse.getStatus() + " ; " + accountResponse.getStatus());
                                            if(response.code()==200){
                                                SplashActivity.me = tempuser;
                                                tv_major.setText(SplashActivity.me.getMajor());
                                                mProgressDialog.dismiss();
                                            }
                                            else{
                                                mProgressDialog.dismiss();
                                                Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            Log.d(getClass().getName(), "Retrofit on Failure");

                                        }
                                    });

                                }catch(Exception e){
                                    Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                        .create().show();
            }
        });

        ib_editgreeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                final EditText input = new EditText(SettingActivity.this);
                input.setText(tv_greeting.getText().toString());

                builder.setView(input);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog = ProgressDialog.show(SettingActivity.this, "", "잠시만 기다려 주세요...", true);
                                final User tempuser = SplashActivity.me;
                                tempuser.setGreeting(input.getText().toString());

                                Retrofit retrofit = new Retrofit
                                        .Builder()
                                        .baseUrl(UserManager.API_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                UserManager userManager = retrofit.create(UserManager.class);

                                try{
                                    Call<AccountResponse> user = userManager.modifyUser(SplashActivity.me.getEmail(), SplashActivity.me);
                                    Log.d(getClass().getName(), "Retrofit is connecting");

                                    user.enqueue(new Callback<AccountResponse>() {
                                        @Override
                                        public void onResponse(Response<AccountResponse> response, Retrofit retrofit) {
                                            AccountResponse accountResponse = response.body();
                                            Log.d(getClass().getName(), "Retrofit on Resp");
                                            Log.d(getClass().getName(), ""+response.code());
                                            if(accountResponse != null)
                                                Log.d(getClass().getName(), accountResponse.getStatus() + " ; " + accountResponse.getStatus());
                                            if(response.code()==200){
                                                SplashActivity.me = tempuser;
                                                tv_greeting.setText(SplashActivity.me.getGreeting());
                                                mProgressDialog.dismiss();
                                            }
                                            else{
                                                mProgressDialog.dismiss();
                                                Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.d(getClass().getName(), "Retrofit on Failure");
                                            Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }catch(Exception e){
                                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                                    Toast.makeText(SettingActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
