package com.hrmj.dongnebangne_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;

/**
 * Created by office on 2017-08-05.
 */

public class SettingActivity extends AppCompatActivity {
    EditText et_keyword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton ib_back = (ImageButton)findViewById(R.id.ib_prf_back);
        ImageButton ib_editintro = (ImageButton)findViewById(R.id.ib_editintro);
        et_keyword = (EditText)findViewById(R.id.et_keyword);
        Button bt_keywordSubmit = (Button)findViewById(R.id.bt_keywordSubmit);

        bt_keywordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tags = User.tagParser(et_keyword.getText().toString());
                MeetingActivity.me.setKeyword(tags);
            }
        });

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_editintro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
