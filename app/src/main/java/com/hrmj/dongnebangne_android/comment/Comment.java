package com.hrmj.dongnebangne_android.comment;

import com.hrmj.dongnebangne_android.user.User;

import java.util.Date;

/**
 * Created by office on 2017-09-01.
 */

public class Comment {
    User user;                  //익명게시판, 댓글까지 익명으로할건지 -> 그렇다면 user, date 삭제
    String content;
    Date createdDate, modifiedDate;
    public Comment(User _user, String _content){
        this.user = _user;
        this.content = _content;
        modifiedDate = createdDate = new Date();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
