package com.hrmj.dongnebangne_android;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by office on 2017-09-01.
 */

public class Chatroom {
    //int idx;
    User chiefUser;
    List<Chat> chatList;
    List<User> joinUserList;
    int maxNum;
    String title;
    ArrayList<String> keyword;
    LatLng latLng;    //DB구현시 delete
    Place place;

    Chatroom(User _chiefUser, int _maxNum, String _title, ArrayList<String> keyword){
        this.chiefUser = _chiefUser;
        this.maxNum = _maxNum;
        this.title = _title;
        chatList = new ArrayList<>();
        joinUserList = new ArrayList<>();
        joinUserList.add(_chiefUser);
        this.keyword = keyword;
        this.latLng = new LatLng(37.631916, 127.077516);
        this.place = null;
    }

    Chatroom(User _chiefUser, int _maxNum, String _title, ArrayList<String> keyword, LatLng _latLng){
        this.chiefUser = _chiefUser;
        this.maxNum = _maxNum;
        this.title = _title;
        chatList = new ArrayList<>();
        joinUserList = new ArrayList<>();
        joinUserList.add(_chiefUser);
        this.keyword = keyword;
        this.latLng = _latLng;
        this.place = null;
    }

    Chatroom(User _chiefUser, int _maxNum, String _title, ArrayList<String> keyword, Place _place){
        this.chiefUser = _chiefUser;
        this.maxNum = _maxNum;
        this.title = _title;
        chatList = new ArrayList<>();
        joinUserList = new ArrayList<>();
        joinUserList.add(_chiefUser);
        this.keyword = keyword;
        this.latLng = _place.getLatLng();
        this.place = _place;
    }

    public User getChiefUser() {
        return chiefUser;
    }

    public void setChiefUser(User chiefUser) {
        this.chiefUser = chiefUser;
    }

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public List<User> getJoinUserList() {
        return joinUserList;
    }

    public void setJoinUserList(List<User> joinUserList) {
        this.joinUserList = joinUserList;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(ArrayList<String> keyword) {
        this.keyword = keyword;
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
