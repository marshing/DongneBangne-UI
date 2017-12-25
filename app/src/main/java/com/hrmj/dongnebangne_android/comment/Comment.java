package com.hrmj.dongnebangne_android.comment;

import com.hrmj.dongnebangne_android.user.User;

//import java.sql.Date;

import java.util.Date;

/**
 * Created by office on 2017-09-01.
 */

public class Comment {
    //익명게시판, 댓글까지 익명으로할건지 -> 그렇다면 user, date 삭제
    String content, email, commentId;
    long createdDate, modifiedDate;
    public Comment(String email, String content){
        this.email = email;
        this.content = content;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
