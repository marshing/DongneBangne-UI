package com.hrmj.dongnebangne_android.article;

import com.hrmj.dongnebangne_android.comment.Comment;

import java.util.List;

/**
 * Created by office on 2017-11-09.
 */

public class ArticleAndComments {
    Article article;
    List<Comment> comments;

    public ArticleAndComments(Article article, List<Comment> comments){
        this.article = article;
        this.comments = comments;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
