package com.hrmj.dongnebangne_android.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by office on 2017-11-09.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG="MyFirebaseIDService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
    }


}
