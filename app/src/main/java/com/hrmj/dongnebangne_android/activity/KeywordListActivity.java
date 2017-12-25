package com.hrmj.dongnebangne_android.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.user.Hastag;
import com.hrmj.dongnebangne_android.user.User;
import com.hrmj.dongnebangne_android.user.UserManager;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-11-08.
 */

public class KeywordListActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageButton ib_back, ib_addkeyword;
    private TextView tv_menutitle;
    private ProgressDialog mProgressDialog;
    private ArrayAdapter adapter;
    private ListView lv_keyword;

    // 영어제한
    protected InputFilter filter= new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches())
                return "";
            return null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywordlist);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.layout_keyworditem, SplashActivity.me.getHastags());
        lv_keyword = (ListView)findViewById(R.id.lv_keywordlist);
        lv_keyword.setAdapter(adapter);
        ib_back = (ImageButton)findViewById(R.id.ib_menu);
        ib_back.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
        ib_addkeyword = (ImageButton)findViewById(R.id.ib_submenu);
        ib_addkeyword.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_add_black));
        tv_menutitle = (TextView)findViewById(R.id.tv_menutitle);
        tv_menutitle.setText("키워드 알림");

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ib_addkeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(KeywordListActivity.this);
                final EditText input = new EditText(KeywordListActivity.this);
                input.setFilters(new InputFilter[] {filter});

                builder.setView(input);
                builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mProgressDialog = ProgressDialog.show(KeywordListActivity.this, "", "잠시만 기다려 주세요...", true);
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(UserManager.API_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                UserManager userManager = retrofit.create(UserManager.class);
                                final Hastag hastag = new Hastag(SplashActivity.me.getEmail(), input.getText().toString());

                                try{
                                    Call<User> user = userManager.addKeyword(SplashActivity.me.getEmail(), hastag);
                                    Log.d(getClass().getName(), "Retrofit is connecting");

                                    user.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Response<User> response, Retrofit retrofit) {
                                            Log.d(getClass().getName(), "Retrofit on Resp");
                                            Log.d(getClass().getName(), ""+response.code());
                                            if(response.code()==200){
                                                SplashActivity.me = response.body();
                                                FirebaseMessaging.getInstance().subscribeToTopic(hastag.getHastag());
                                                Log.d(getClass().getName(), hastag.getHastag()+" 등록");
                                                mProgressDialog.dismiss();
                                                adapter.notifyDataSetChanged();
                                                lv_keyword.setAdapter(adapter);
                                            }
                                            else{
                                                mProgressDialog.dismiss();
                                                Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Log.d(getClass().getName(), "Retrofit on Failure");
                                            Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }catch(Exception e){
                                    Log.d(getClass().getName(), "Retrofit connecting is fail");
                                    Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
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


        // 키워드 삭제 서버 기능 구축 이후
//
//        lv_keyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(KeywordListActivity.this);
//                final TextView textView = new TextView(KeywordListActivity.this);
//                textView.setText(SplashActivity.me.getHastags().get(position) + "를 키워드에서 삭제하시겠습니까?");
////
////                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//////                    @Override
//////                    public void onDismiss(DialogInterface dialog) {
//////                        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//////                            @Override
//////                            public void onDismiss(DialogInterface dialog) {
//////                                adapter.notifyDataSetChanged();
//////                                lv_keyword.setAdapter(adapter);
//////                                //갱신이안돼ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ
//////                            }
//////                        });
//////                    }
////                });
//                builder.setView(textView);
//                builder.setPositiveButton(getString(R.string.ok),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                mProgressDialog = ProgressDialog.show(KeywordListActivity.this, "", "잠시만 기다려 주세요...", true);
//                                Retrofit retrofit = new Retrofit.Builder()
//                                        .baseUrl(UserManager.API_URL)
//                                        .addConverterFactory(GsonConverterFactory.create())
//                                        .build();
//                                UserManager userManager = retrofit.create(UserManager.class);
//                                final Hastag hastag = new Hastag(SplashActivity.me.getEmail(), input.getText().toString());
//
//                                try{
//                                    Call<User> user = userManager.addKeyword(SplashActivity.me.getEmail(), hastag);
//                                    Log.d(getClass().getName(), "Retrofit is connecting");
//
//                                    user.enqueue(new Callback<User>() {
//                                        @Override
//                                        public void onResponse(Response<User> response, Retrofit retrofit) {
//                                            Log.d(getClass().getName(), "Retrofit on Resp");
//                                            Log.d(getClass().getName(), ""+response.code());
//                                            if(response.code()==200){
//                                                SplashActivity.me = response.body();
//                                                FirebaseMessaging.getInstance().subscribeToTopic(hastag.getHastag());
//                                                Log.d(getClass().getName(), hastag.getHastag()+" 등록");
//                                                mProgressDialog.dismiss();
//                                            }
//                                            else{
//                                                mProgressDialog.dismiss();
//                                                Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Throwable t) {
//                                            Log.d(getClass().getName(), "Retrofit on Failure");
//                                            Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//
//                                }catch(Exception e){
//                                    Log.d(getClass().getName(), "Retrofit connecting is fail");
//                                    Toast.makeText(KeywordListActivity.this, "서버 전송에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//                builder.setNegativeButton(getString(R.string.cancel),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }).create().show();
//            }
//        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        lv_keyword.setAdapter(adapter);

    }
}
