package com.hrmj.dongnebangne_android.user;

/**
 * Created by office on 2017-11-08.
 */

public class Hastag {
    String email, hastag;

    public Hastag(String email, String hastag){
        this.email = email;
        this.hastag = hastag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHastag() {
        return hastag;
    }

    public void setHastag(String hastag) {
        this.hastag = hastag;
    }
}
