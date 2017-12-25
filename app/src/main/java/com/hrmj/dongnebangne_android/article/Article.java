package com.hrmj.dongnebangne_android.article;

import com.hrmj.dongnebangne_android.comment.Comment;

//import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by office on 2017-09-01.
 */

public class Article {
    String title, content, email, hit;
    long createdDate,modifiedDate;
    long articleId;
    int thumb;

    public Article(String email, String title, String content){
        this.email = email;
        this.title = title;
        this.content = content;
        this.hit = "0";
        this.thumb = 0;
    }


    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
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


    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public void thumbUp() {
        thumb++;
    }
}
