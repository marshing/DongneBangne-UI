package com.hrmj.dongnebangne_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hrmj.dongnebangne_android.chatroom.Chatroom;
import com.hrmj.dongnebangne_android.R;

import java.util.ArrayList;

/**
 * Created by office on 2017-10-12.
 */

public class NewChatActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText et_newchatTitle, et_newchatTag;
    private Button bt_newchat, bt_newchatmaps;
    GoogleApiClient mGoogleApiClient;
    private Place place = null;

    int PLACE_PICKER_REQUEST = 1;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchat);

        et_newchatTitle = (EditText) findViewById(R.id.et_newchatTitle);
        et_newchatTag = (EditText) findViewById(R.id.et_newchatTag);
        bt_newchat = (Button) findViewById(R.id.bt_newchat);
        bt_newchatmaps = (Button)findViewById(R.id.bt_newchatmaps) ;

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
                ArrayList<String> keywordlist = Chatroom.tagParser(et_newchatTag.getText().toString());
                if ( place == null){
                    chatroom = new Chatroom(MeetingActivity.me, et_newchatTitle.getText().toString(), keywordlist);
                }else {
                    chatroom = new Chatroom(MeetingActivity.me, et_newchatTitle.getText().toString(), keywordlist, place);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(place.getLatLng());
                    markerOptions.snippet(place.getName().toString());
                    markerOptions.title(et_newchatTitle.getText().toString());
                    MeetingActivity.mMap.addMarker(markerOptions);
                }
                String tags="";
                for(int i=0; i<chatroom.getKeyword().size();i++){
                    tags = tags + "tag " + i + " : " + keywordlist.get(i) + ", ";
                }
                Log.d(getClass().getName(), "태그 파싱 : " + tags);
                MeetingActivity.chatroomAdapter.add(chatroom);
                //DB구현하면 지우기
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                Chatroom room = (Chatroom)(MeetingActivity.chatroomAdapter.getItem(MeetingActivity.chatroomAdapter.getSize()-1));
                intent.putExtra("topic", room.getTitle());
                startActivity(intent);
                finish();
            }
        });

        bt_newchatmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(NewChatActivity.this), PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesNotAvailableException e){ e.printStackTrace();}
                catch (GooglePlayServicesRepairableException e){e.printStackTrace();}

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            place = PlacePicker.getPlace(this,data);
            Toast.makeText(this, place.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
