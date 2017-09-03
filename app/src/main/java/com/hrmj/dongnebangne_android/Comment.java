package com.hrmj.dongnebangne_android;

import java.util.Date;

/**
 * Created by office on 2017-09-01.
 */

public class Comment {
    User user;
    String content;
    Date createdDate, modifiedDate;
    Comment(User _user, String _content){
        this.user = _user;
        this.content = _content;
        modifiedDate = createdDate = new Date();
    }
}
