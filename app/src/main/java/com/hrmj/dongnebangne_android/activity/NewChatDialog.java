package com.hrmj.dongnebangne_android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.hrmj.dongnebangne_android.article.ArticleManager;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;
import com.hrmj.dongnebangne_android.chatroom.MeetingManager;
import com.hrmj.dongnebangne_android.service.FirebaseMessagingService;

import java.util.List;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2017-10-12.
 */

public class NewChatDialog extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText et_newchatTitle, et_newchatTag;
    private Button bt_newchat, bt_newchatmaps;
    private RadioGroup radioGroup1, radioGroup2;
    GoogleApiClient mGoogleApiClient;
    private Place place = null;
    private ProgressDialog mProgressDialog;

    int PLACE_PICKER_REQUEST = 1;
    private Chatroom res_chatroom;

    String tags="";
    List<String> hashtags;

    // 영어제한
    protected InputFilter filter= new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9,]+$");
            if (!ps.matcher(source).matches())
                return "";
            return null;
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newchat);

        et_newchatTitle = (EditText) findViewById(R.id.et_newchatTitle);
        et_newchatTag = (EditText) findViewById(R.id.et_newchatTag);
        et_newchatTag.setFilters(new InputFilter[]{filter});
        bt_newchat = (Button) findViewById(R.id.bt_newchat);
        bt_newchatmaps = (Button)findViewById(R.id.bt_newchatmaps) ;
        radioGroup1 = (RadioGroup)findViewById(R.id.radiogroup1);
        radioGroup2 = (RadioGroup)findViewById(R.id.radiogroup2);


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        bt_newchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chatroom chatroom;
                int id1 = radioGroup1.getCheckedRadioButtonId();
                int id2 = radioGroup2.getCheckedRadioButtonId();
                int type=0;

                if(id1==R.id.radio_eat && id2==R.id.radio_busy) type=0;
                else if(id1==R.id.radio_eat && id2==R.id.radio_normal) type=1;
                else if(id1==R.id.radio_talk && id2==R.id.radio_busy) type=2;
                else if(id1==R.id.radio_talk && id2==R.id.radio_normal) type=3;
                else type=0;

                hashtags = Chatroom.tagParser(et_newchatTag.getText().toString());
                chatroom = new Chatroom(type, et_newchatTitle.getText().toString(), SplashActivity.me.getEmail(), hashtags);
//                if ( place == null){
//                    chatroom = new Chatroom(SplashActivity.me, et_newchatTitle.getText().toString(), keywordlist);
//                }else {
//                    chatroom = new Chatroom(SplashActivity.me, et_newchatTitle.getText().toString(), keywordlist, place);
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    markerOptions.position(place.getLatLng());
//                    markerOptions.snippet(place.getName().toString());
//                    markerOptions.title(et_newchatTitle.getText().toString());
//                    MeetingActivity.mMap.addMarker(markerOptions);
//                }
                for(int i=0; i<chatroom.getHashtags().size();i++){
                    tags = tags + "tag " + i + " : " + chatroom.getHashtags().get(i) + ", ";
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ArticleManager.API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                MeetingManager meetingManager = retrofit.create(MeetingManager.class);

                try{
                    mProgressDialog = ProgressDialog.show(NewChatDialog.this, "", "잠시만 기다려 주세요...", true);
                    final Call<Chatroom> meetings = meetingManager.createMeeting(chatroom);
                    Log.d(getClass().getName(), "Retrofit is connecting");
                    meetings.enqueue(new Callback<Chatroom>() {
                        @Override
                        public void onResponse(Response<Chatroom> response, Retrofit retrofit) {
                            Log.d(getClass().getName(), "Retrofit response is success");
                            Log.d(getClass().getName(), ""+response.code());
                            if(response.code()==201){
                                 res_chatroom= response.body();
                                Log.d(getClass().getName(), res_chatroom.toString());
                                mProgressDialog.dismiss();


                                Log.d(getClass().getName(), "태그 파싱 : " + tags);
                                FirebaseMessagingService service = new FirebaseMessagingService();
                                service.sendPostToFCM(hashtags, et_newchatTitle.getText().toString());

                                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                intent.putExtra("meetingId", ""+res_chatroom.getMeetingId());
                                intent.putExtra("title", ""+res_chatroom.getTitle());
                                startActivity(intent);
                                finish();
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

        bt_newchatmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(NewChatDialog.this), PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesNotAvailableException e){ e.printStackTrace();}
                catch (GooglePlayServicesRepairableException e){e.printStackTrace();}

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if(data != null) {
                place = PlacePicker.getPlace(this, data);
                Toast.makeText(this, place.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
