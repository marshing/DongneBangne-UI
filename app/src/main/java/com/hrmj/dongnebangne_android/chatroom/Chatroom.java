package com.hrmj.dongnebangne_android.chatroom;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.hrmj.dongnebangne_android.chat.Chat;
import com.hrmj.dongnebangne_android.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by office on 2017-09-01.
 */

public class Chatroom implements Serializable {
    String title, cheifEmail;
    int type;
    Long meetingId;
    long createdDate;
    List<String> hashtags;
    List<String> peoples;
    boolean status;
    LatLng latLng;    //DB구현시 delete
    Place place;

    public Chatroom( int type, String title, String cheifEmail, List<String> hashtags){
        this.type = type;
        this.title = title;
        this.cheifEmail = cheifEmail;
        this.hashtags = hashtags;

        this.latLng = new LatLng(37.631916, 127.077516);
        this.place = null;
    }
//
//    public Chatroom(User _chiefUser, String _title, ArrayList<String> keyword, LatLng _latLng){
//        this.chiefUser = _chiefUser;
//        this.title = _title;
//        chatList = new ArrayList<>();
//        joinUserList = new ArrayList<>();
//        joinUserList.add(_chiefUser);
//        this.keyword = keyword;
//        this.latLng = _latLng;
//        this.place = null;
//    }
//
//    public Chatroom(User _chiefUser, String _title, ArrayList<String> keyword, Place _place){
//        this.chiefUser = _chiefUser;
//        this.title = _title;
//        chatList = new ArrayList<>();
//        joinUserList = new ArrayList<>();
//        joinUserList.add(_chiefUser);
//        this.keyword = keyword;
//        this.latLng = _place.getLatLng();
//        this.place = _place;
//    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCheifEmail() {
        return cheifEmail;
    }

    public void setCheifEmail(String cheifEmail) {
        this.cheifEmail = cheifEmail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<String> getPeoples() {
        return peoples;
    }

    public void setPeoples(List<String> peoples) {
        this.peoples = peoples;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public static ArrayList<String> tagParser(String tag) {
        //공백제거
        tag = tag.replaceAll(" ","");

        ArrayList<String> tagList = new ArrayList<>();
        StringTokenizer str = new StringTokenizer(tag, ",");

        while(str.hasMoreTokens()){
            tagList.add(str.nextToken());
        }
        return tagList;
    }

}
