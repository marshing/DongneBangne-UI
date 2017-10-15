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

    Post(){}

    Post(String _title, String _content){
        this.title = _title;
        this.content = _content;
        this.commentList = new ArrayList<>();
        modifiedDate = createdDate = new Date();
        this.thumb = 0;
    }


    Post(String _title, String _content, Date _date){
        this.title = _title;
        this.content = _content;
        this.commentList = new ArrayList<>();
        modifiedDate = createdDate = _date;
        this.thumb = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public int getThumb() {
        return thumb;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }
}
