package com.hrmj.dongnebangne_android.activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.AccountResponse;
import com.hrmj.dongnebangne_android.user.User;
import com.hrmj.dongnebangne_android.user.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-07-31.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_pw, et_pwchk, et_name;
    private Button bt_major;
    private ImageButton ib_signup;
    String sId, sPw, sPwchk, sName, sMajor;

    User newuser;

    Retrofit retrofit;
    UserManager userManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText) findViewById(R.id.et_reg_id);
        et_pw = (EditText) findViewById(R.id.et_reg_pw);
        et_pwchk = (EditText) findViewById(R.id.et_reg_pwchk);
        et_name = (EditText) findViewById(R.id.et_reg_name);
        bt_major = (Button)findViewById(R.id.bt_reg_major);
        ib_signup = (ImageButton) findViewById(R.id.ib_rg_signup);

        // 생일 보류
      /*  final DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String birth = String.format("%d / %d / %d", year, month+1, dayOfMonth);
                bt_birth.setText(birth);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        bt_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        */

        bt_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"컴퓨터공학과", "전기정보공학과", "전자IT미디어공학과"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
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
                                bt_major.setText(items[selectedIndex[0]]);
                            }
                        })
                         .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                             }
                         }).create().show();
            }
        });

        ib_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();
                sPwchk = et_pwchk.getText().toString();
                sName = et_name.getText().toString();
                sMajor = bt_major.getText().toString();

                if(sId.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(sPw.length() == 0){
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_pw.requestFocus();
                    return;
                }
                if(sPwchk.length() == 0){
                    Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_pwchk.requestFocus();
                    return;
                }
                if(sName.length() == 0){
                    Toast.makeText(RegisterActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if(sMajor.length() == 0){
                    Toast.makeText(RegisterActivity.this, "전공을 선택하세요!", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                }
                if(!sPw.equals(sPwchk)){
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    et_pw.setText("");
                    et_pwchk.setText("");
                    et_pw.requestFocus();
                    return;
                }

                newuser = new User(sId, sName, sPw, "", sMajor, new ArrayList<String>());


                retrofit = new Retrofit
                        .Builder()
                        .baseUrl(UserManager.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                userManager = retrofit.create(UserManager.class);

                try {
                    Call<AccountResponse> user = userManager.createUser(newuser);
                    Log.d(getClass().getName(), "Retrofit is connecting");

                    user.enqueue(new Callback<AccountResponse>() {
                        @Override
                        public void onResponse(Response<AccountResponse> response, Retrofit retrofit) {
                            AccountResponse accountResponse = response.body();
                            Log.d(getClass().getName(), "Retrofit on Resp");
                            Log.d(getClass().getName(), ""+response.code());
                            if(accountResponse != null)
                                Log.d(getClass().getName(), accountResponse.getStatus() + " ; " + accountResponse.getStatus());
                            if(response.code()==201){
                                Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d(getClass().getName(), "Retrofit on Failure");
                        }
                    });
                }catch(Exception e){
                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                }

                Intent result = new Intent();
                result.putExtra("email", sId);

                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

}
