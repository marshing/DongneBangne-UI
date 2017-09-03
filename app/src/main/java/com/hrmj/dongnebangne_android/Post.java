package com.hrmj.dongnebangne_android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by office on 2017-09-01.
 */

public class Post {
    //int idx;
    String title, content;
    Date createdDate, modifiedDate;
    List<Comment> commentList;
    int thumb;
    Post(String _title, String _content){
        this.title = _title;
        this.content = _content;
        this.commentList = new ArrayList<>();
        modifiedDate = createdDate = new Date();
        this.thumb = 0;
    }
}
