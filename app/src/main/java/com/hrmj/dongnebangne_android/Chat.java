package com.hrmj.dongnebangne_android;

import java.util.Date;

/**
 * Created by office on 2017-08-07.
 */

public class Chat {
    String content;
    Date uptime;
    User user;
    Chat(String _content,  User _user){
        this.content = _content;
        this.uptime = new Date();
        this.user = _user;
    }
}
