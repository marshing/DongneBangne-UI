package com.hrmj.dongnebangne_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by office on 2017-07-31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pw;
    private Button bt_signup, bt_login;
    String sId, sPw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        bt_login = (Button) findViewById(R.id.bt_login);

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
                //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

                // DB에서 회원확인 후 회원 정보 받기


                Intent intent = new Intent(getApplicationContext(), MeetingActivity.class);
                // 일단 email주소만 MainActivity로 전달 (DB구축 이후 모든 회원정보 전달)
               // intent.putExtra("email", sId);

                startActivity(intent);
                finish();


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
