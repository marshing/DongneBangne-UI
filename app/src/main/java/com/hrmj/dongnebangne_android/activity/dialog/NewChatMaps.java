package com.hrmj.dongnebangne_android.activity.dialog;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.hrmj.dongnebangne_android.R;

/**
 * Created by office on 2017-10-13.
 */

public class NewChatMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.newchatmaps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SNUT = new LatLng(37.631916, 127.077516);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SNUT);
//        markerOptions.title("서울과학기술대학교");
//        markerOptions.snippet("국립대학교");
//        googleMap.addMarker(markerOptions);


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SNUT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


}
