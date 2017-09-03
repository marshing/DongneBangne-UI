package com.hrmj.dongnebangne_android;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by office on 2017-07-31.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_code, et_pw, et_pwchk, et_name;
    private Button bt_signup, bt_cacle, bt_birth;
    String sId, sCode, sPw, sPwchk, sName, sBirth;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText) findViewById(R.id.et_reg_id);
        et_code = (EditText) findViewById(R.id.et_reg_code);
        et_pw = (EditText) findViewById(R.id.et_reg_pw);
        et_pwchk = (EditText) findViewById(R.id.et_reg_pwchk);
        et_name = (EditText) findViewById(R.id.et_reg_name);

        bt_birth = (Button) findViewById(R.id.bt_reg_birth);
        bt_signup = (Button) findViewById(R.id.bt_rg_signup);
        bt_cacle = (Button) findViewById(R.id.bt_rg_cancle);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
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


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sId = et_id.getText().toString();
                sCode = et_code.getText().toString();
                sPw = et_pw.getText().toString();
                sPwchk = et_pwchk.getText().toString();
                sName = et_name.getText().toString();
                sBirth = bt_birth.getText().toString();

                if(sId.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_id.requestFocus();
                    return;
                }
                if(sCode.length() == 0){
                    Toast.makeText(RegisterActivity.this, "Code을 입력하세요!", Toast.LENGTH_SHORT).show();
                    et_code.requestFocus();
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
                if(sBirth.length() == 0){
                    Toast.makeText(RegisterActivity.this, "생일을 입력하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!sPw.equals(sPwchk)){
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    et_pw.setText("");
                    et_pwchk.setText("");
                    et_pw.requestFocus();
                    return;
                }

                // DB전송 코드

                Intent result = new Intent();
                result.putExtra("email", sId);

                setResult(RESULT_OK, result);
                finish();
            }
        });

        bt_cacle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
