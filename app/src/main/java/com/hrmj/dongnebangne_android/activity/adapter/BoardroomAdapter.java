package com.hrmj.dongnebangne_android.activity.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.hrmj.dongnebangne_android.activity.SplashActivity;
import com.hrmj.dongnebangne_android.article.Article;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.service.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-08-30.
 */

public class BoardroomAdapter extends BaseAdapter {
    Retrofit retrofit;
    ArticleManager articleManager;
    Activity activity;
    private ProgressDialog mProgressDialog;


    public static List<Article> m_List = new ArrayList<>();  //테스트위한 static

    public BoardroomAdapter(final Activity parentactivity){
        this.activity = parentactivity;
        retrofit = new Retrofit
                .Builder()
                .baseUrl(ArticleManager.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        articleManager = retrofit.create(ArticleManager.class);
        try{
            mProgressDialog = ProgressDialog.show(activity, "", "잠시만 기다려 주세요...", true);
            Call<List<Article>> articles = articleManager.allArticles();
            Log.d(getClass().getName(), "Retrofit is connecting");
            articles.enqueue(new Callback<List<Article>>() {
                @Override
                public void onResponse(Response<List<Article>> response, Retrofit retrofit) {
                    Log.d(getClass().getName(), "Retrofit response is success");
                    Log.d(getClass().getName(), ""+response.code());
                    mProgressDialog.dismiss();
                    if(response.code()==200){
                        m_List=response.body();
                        Log.d(getClass().getName(), "ArticleList");
                        notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }else {
                        Toast.makeText(activity, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
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
    public void refresh(){
        try{
            Call<List<Article>> articles = articleManager.allArticles();
            Log.d(getClass().getName(), "Retrofit is connecting");
            articles.enqueue(new Callback<List<Article>>() {
                @Override
                public void onResponse(Response<List<Article>> response, Retrofit retrofit) {
                    Log.d(getClass().getName(), "Retrofit response is success");
                    Log.d(getClass().getName(), ""+response.code());
                    if(response.code()==200){
                        m_List=response.body();
                        Log.d(getClass().getName(), "ArticleList");
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(activity, "서버와의 통신에 실패하였습니다.", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d(getClass().getName(), "Retrofit resp is fail");

                }
            });
        }catch (Exception e){
            Log.d(getClass().getName(), "Retrofit is fail");}
    }

    @Override
    public int getCount() {
        return m_List.size();
    }

    @Override
    public Article getItem(int position) {
        return m_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        TextView tv_boardTitle, tv_boardContent, tv_thumbCount, tv_recommandCount, tv_boardDate;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_boardroomitem, parent, false);
        }

        tv_boardTitle = (TextView)convertView.findViewById(R.id.tv_boardTitle);
        tv_boardContent = (TextView)convertView.findViewById(R.id.tv_boardContent);
        tv_thumbCount = (TextView)convertView.findViewById(R.id.tv_thumbCount);
        tv_recommandCount = (TextView)convertView.findViewById(R.id.tv_recommandCount);
        tv_boardDate = (TextView)convertView.findViewById(R.id.tv_boardDate);

        Article boardroom = m_List.get(position);

        tv_boardTitle.setText(boardroom.getTitle());
        tv_boardContent.setText(boardroom.getContent());
        tv_thumbCount.setText(""+boardroom.getThumb());
        tv_recommandCount.setText("" + 0);
        tv_boardDate.setText(""+ PrettyTime.formatTimeString(boardroom.getCreatedDate()));


        return convertView;
    }
}


