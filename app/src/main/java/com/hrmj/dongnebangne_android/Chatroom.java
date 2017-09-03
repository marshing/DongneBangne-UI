package com.hrmj.dongnebangne_android;

import java.util.ArrayList;
import java.util.List;

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
    List<String> keyword;
    Chatroom(User _chiefUser, int _maxNum, String _title){
        this.chiefUser = _chiefUser;
        this.maxNum = _maxNum;
        this.title = _title;
        chatList = new ArrayList<>();
        joinUserList = new ArrayList<>();
        joinUserList.add(_chiefUser);
        keyword = new ArrayList<>();
    }
}
