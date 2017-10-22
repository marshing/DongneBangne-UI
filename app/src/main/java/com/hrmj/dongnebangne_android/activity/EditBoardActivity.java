package com.hrmj.dongnebangne_android.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.activity.adapter.BoardroomAdapter;
import com.hrmj.dongnebangne_android.post.Post;
import com.hrmj.dongnebangne_android.R;

/**
 * Created by office on 2017-09-01.
 */

public class EditBoardActivity extends AppCompatActivity{
    private EditText et_boardtitle, et_boardcontent;
    private Toolbar toolbar;
    private ImageButton ib_back, ib_check;
    private TextView tv_menutitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editboard);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_back = (ImageButton)findViewById(R.id.ib_menu);
        ib_back.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
        ib_check = (ImageButton)findViewById(R.id.ib_submenu);
        ib_check.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp));
        tv_menutitle = (TextView)findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("익명게시판");

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_boardtitle = (EditText)findViewById(R.id.et_boardtitle);
                et_boardcontent = (EditText)findViewById(R.id.et_boardcontent);
                BoardroomAdapter.add(new Post(et_boardtitle.getText().toString(), et_boardcontent.getText().toString()));
                finish();



            }
        });







    }
}
