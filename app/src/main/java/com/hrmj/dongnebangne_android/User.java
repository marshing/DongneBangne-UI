package com.hrmj.dongnebangne_android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by office on 2017-08-06.
 */

public class User {
    //int idx;    // 초기화 ??
    String name, email, password, introduction, major;
    Date birth;
    List<String> keyword, blockUser;
    int reportedCount;
    //프로필사진

    User(String _name, String _email, String _password, Date _birth) {
        this.name = _name;
        this.email = _email;
        this.password = _password;
        this.birth = _birth;
        this.introduction = "";
        this.major = "";
        this.keyword = new ArrayList<>();
        this.blockUser = new ArrayList<>();
        this.reportedCount = 0;
    }
}
