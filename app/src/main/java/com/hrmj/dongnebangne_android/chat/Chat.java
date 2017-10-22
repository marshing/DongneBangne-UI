package com.hrmj.dongnebangne_android.chat;

import com.hrmj.dongnebangne_android.user.User;

import java.util.Date;

/**
 * Created by office on 2017-08-07.
 */

public class Chat {
    String content;
    Date uptime;
    User user;
    public Chat(String _content,  User _user){
        this.content = _content;
        this.uptime = new Date();       //?
        this.user = _user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
