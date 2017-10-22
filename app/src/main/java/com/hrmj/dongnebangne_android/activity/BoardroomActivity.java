package com.hrmj.dongnebangne_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hrmj.dongnebangne_android.activity.adapter.BoardroomAdapter;
import com.hrmj.dongnebangne_android.comment.Comment;
import com.hrmj.dongnebangne_android.post.Post;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.activity.adapter.RecommendAdapter;

import java.text.SimpleDateFormat;

/**
 * Created by office on 2017-09-02.
 */

public class BoardroomActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageButton ib_back, ib_none, ib_boardroomThumb, ib_recommendsend;
    private TextView tv_menutitle, tv_boardroomTitle, tv_boardroomContent, tv_boardroomDate, tv_boardroomRecommendcount, tv_boardroomThumbcount;
    private ListView lv_recommend;
    private EditText et_recommend;
    RecommendAdapter m_Adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardroom);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ib_back = (ImageButton)findViewById(R.id.ib_menu);
        ib_back.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
        ib_none = (ImageButton)findViewById(R.id.ib_submenu);
        ib_none.setVisibility(View.INVISIBLE);
        tv_menutitle = (TextView)findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("익명게시판");

        ib_boardroomThumb = (ImageButton)findViewById(R.id.ib_boardroomThumb);
        tv_boardroomTitle = (TextView)findViewById(R.id.tv_boardroomTitle);
        tv_boardroomContent = (TextView)findViewById(R.id.tv_boardroomContent);
        tv_boardroomDate = (TextView)findViewById(R.id.tv_boardroomDate);
        tv_boardroomRecommendcount = (TextView)findViewById(R.id.tv_boardroomRecommendcount);
        tv_boardroomThumbcount = (TextView)findViewById(R.id.tv_boardroomThumbcount);

        ib_recommendsend = (ImageButton)findViewById(R.id.ib_recommendSend);
        et_recommend = (EditText)findViewById(R.id.et_recommend);

        Intent intent = getIntent();
        int postNum = intent.getIntExtra("postNum", -1);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd hh:mm");

        if(postNum != -1){
            final Post post = BoardroomAdapter.m_List.get(postNum);
            tv_boardroomTitle.setText(post.getTitle());
            tv_boardroomContent.setText(post.getContent());
            tv_boardroomDate.setText(df.format(post.getCreatedDate()));
            tv_boardroomRecommendcount.setText(""+post.getCommentList().size());
            tv_boardroomThumbcount.setText(""+post.getThumb());

            m_Adapter = new RecommendAdapter(post);
            lv_recommend = (ListView)findViewById(R.id.lv_recommend);

            lv_recommend.setAdapter(m_Adapter);

            ib_boardroomThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post.thumbUp();
                    tv_boardroomThumbcount.setText(""+post.getThumb());
                }
            });

            ib_recommendsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = new Comment(MeetingActivity.me, et_recommend.getText().toString());
                    post.getCommentList().add(comment);
                    et_recommend.setText("");
                    tv_boardroomRecommendcount.setText(""+post.getCommentList().size());
                    m_Adapter.notifyDataSetChanged();
                }
            });

        }

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        m_Adapter.notifyDataSetChanged();
    }
}
