package com.hrmj.dongnebangne_android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hrmj.dongnebangne_android.activity.adapter.BoardroomAdapter;
import com.hrmj.dongnebangne_android.article.ArticleAndComments;
import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.article.PostResponse;
import com.hrmj.dongnebangne_android.comment.Comment;
import com.hrmj.dongnebangne_android.article.Article;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.activity.adapter.RecommendAdapter;
import com.hrmj.dongnebangne_android.service.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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

    private Retrofit retrofit;
    private ArticleManager articleManager;

    private long articleId;
    private ArticleAndComments article;

    private  ProgressDialog mProgressDialog;

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

        final Intent intent = getIntent();
        long def = -1l;
        articleId = intent.getLongExtra("articleId", def);

        if(articleId != -1){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(ArticleManager.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            articleManager = retrofit.create(ArticleManager.class);

            try{
                mProgressDialog = ProgressDialog.show(BoardroomActivity.this, "", "잠시만 기다려 주세요...", true);
                final Call<ArticleAndComments> articles = articleManager.searchArticle(""+articleId);
                Log.d(getClass().getName(), "Retrofit is connecting");
                articles.enqueue(new Callback<ArticleAndComments>() {
                    @Override
                    public void onResponse(Response<ArticleAndComments> response, Retrofit retrofit) {
                        Log.d(getClass().getName(), "Retrofit response is success");
                        Log.d(getClass().getName(), ""+response.code());
                        if(response.code()==200){
                            article=response.body();
                            Log.d(getClass().getName(), article.toString());

                            tv_boardroomTitle.setText(article.getArticle().getTitle());
                            tv_boardroomContent.setText(article.getArticle().getContent());
                            tv_boardroomDate.setText(PrettyTime.formatTimeString(article.getArticle().getCreatedDate()));
                            if(article.getComments()!=null) {
                                tv_boardroomRecommendcount.setText("" + article.getComments().size());
                            }else{tv_boardroomRecommendcount.setText("" + 0);}
                            tv_boardroomThumbcount.setText(""+ article.getArticle().getThumb());

                            m_Adapter = new RecommendAdapter(article);
                            lv_recommend = (ListView)findViewById(R.id.lv_recommend);

                            lv_recommend.setAdapter(m_Adapter);

                            ib_boardroomThumb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    article.getArticle().thumbUp();
                                    tv_boardroomThumbcount.setText(""+ article.getArticle().getThumb());
                                }
                            });
                            mProgressDialog.dismiss();
                        }else {
                            Toast.makeText(getApplicationContext(), "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                            mProgressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(getClass().getName(), "Retrofit resp is fail");
                        mProgressDialog.dismiss();

                    }
            });
            }catch (Exception e){
                Log.d(getClass().getName(), "Retrofit is fail");}


            ib_recommendsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Comment comment = new Comment(SplashActivity.me.getEmail(), et_recommend.getText().toString());
                    try{
                        mProgressDialog = ProgressDialog.show(BoardroomActivity.this, "", "잠시만 기다려 주세요...", true);
                        final Call<PostResponse> articles = articleManager.createComment(""+articleId, comment);
                        Log.d(getClass().getName(), "Retrofit is connecting");
                        articles.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Response<PostResponse> response, Retrofit retrofit) {
                                Log.d(getClass().getName(), "Retrofit response is success");
                                Log.d(getClass().getName(), ""+response.code());
                                if(response.code()==200){
//                                    article.getComments().add(comment);
                                    et_recommend.setText("");
                                    //나중에 메소드화 시켜서 게시글 전체 다시불러오기로 갱신
                                    final Call<ArticleAndComments> articles2 = articleManager.searchArticle(""+articleId);
                                    Log.d(getClass().getName(), "Retrofit is connecting");
                                    articles2.enqueue(new Callback<ArticleAndComments>() {
                                        @Override
                                        public void onResponse(Response<ArticleAndComments> response, Retrofit retrofit) {
                                            Log.d(getClass().getName(), "Retrofit response is success");
                                            Log.d(getClass().getName(), ""+response.code());
                                            if(response.code()==200){
                                                article=response.body();
                                                Log.d(getClass().getName(), article.toString());
                                                if(article.getComments()!=null) {
                                                    tv_boardroomRecommendcount.setText("" + article.getComments().size());
                                                }else{tv_boardroomRecommendcount.setText("" + 0);}

                                                m_Adapter = new RecommendAdapter(article);
                                                lv_recommend = (ListView)findViewById(R.id.lv_recommend);

                                                lv_recommend.setAdapter(m_Adapter);

                                                ib_boardroomThumb.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        article.getArticle().thumbUp();
                                                        tv_boardroomThumbcount.setText(""+ article.getArticle().getThumb());
                                                    }
                                                });
                                                mProgressDialog.dismiss();
                                            }else {
                                                Toast.makeText(getApplicationContext(), "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                                                mProgressDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.d(getClass().getName(), "Retrofit resp is fail");
                                            mProgressDialog.dismiss();

                                        }
                                    });

                                    mProgressDialog.dismiss();
                                }else {
                                    Toast.makeText(getApplicationContext(), "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                                    mProgressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.d(getClass().getName(), "Retrofit resp is fail");
                                mProgressDialog.dismiss();

                            }
                        });
                    }catch (Exception e){
                        Log.d(getClass().getName(), "Retrofit is fail");}
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

}
