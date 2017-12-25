package com.hrmj.dongnebangne_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.User;
import com.hrmj.dongnebangne_android.user.UserManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-07-31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pw;
    private Button bt_signup;
    private ImageButton bt_login;
    String sId, sPw;

    Retrofit retrofit;
    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_login = (ImageButton) findViewById(R.id.bt_login);


        // id enter -> pw로 자동 커서 이동
        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_id.setHint("");
            }
        });
        et_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_pw.setHint("");
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();

                if(sId.length() == 0) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(sPw.length() == 0) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_pw.requestFocus();
                    return;
                }

                // 로그인 구현
                retrofit = new Retrofit
                        .Builder()
                        .baseUrl(UserManager.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                userManager = retrofit.create(UserManager.class);

                try {
                    Call<User> user = userManager.searchUser(sId);
                    Log.d(getClass().getName(), "Retrofit is connecting");

                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Response<User> response, Retrofit retrofit) {

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d(getClass().getName(), "Retrofit connecting is failure");

                        }
                    });
                }catch(Exception e){
                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                }


                Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                intent.putExtra("email", sId);

                startActivity(intent);
                finish();


            }
        });

        // 엔터키로 로그인
        et_pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    bt_login.performClick();
                    return true;
                }

                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            et_id.setText(data.getStringExtra("email"));
        }
    }
}
