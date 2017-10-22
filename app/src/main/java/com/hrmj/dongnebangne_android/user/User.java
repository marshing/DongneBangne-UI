package com.hrmj.dongnebangne_android.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by office on 2017-08-06.
 */

public class User {
    //int idx;    // 초기화 ??
    String name, email, password, introduction, major;
    Date birth;
    ArrayList<String> keyword;
    int reportedCount;
    //프로필사진



    public User(String _name, String _email, String _password, Date _birth) {
        this.name = _name;
        this.email = _email;
        this.password = _password;
        this.birth = _birth;
        this.introduction = "";
        this.major = "";
        this.keyword = new ArrayList<>();
        this.reportedCount = 0;
    }

    public User(String _name, String _email, String _password) {
        this.name = _name;
        this.email = _email;
        this.password = _password;
        this.birth = new Date(1994, 5, 3);
        this.introduction = "";
        this.major = "";
        this.keyword = new ArrayList<>();
        this.reportedCount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(ArrayList<String> keyword) {
        this.keyword = keyword;
    }


    public int getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(int reportedCount) {
        this.reportedCount = reportedCount;
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
