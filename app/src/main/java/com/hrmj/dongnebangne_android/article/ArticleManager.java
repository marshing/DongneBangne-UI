package com.hrmj.dongnebangne_android.article;

import com.google.gson.JsonArray;
import com.hrmj.dongnebangne_android.comment.Comment;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by office on 2017-10-22.
 */

public interface ArticleManager {

    public static final String API_URL = "https://dongnebangne-185304.appspot.com/";

    @POST("articles")
    Call<PostResponse>createArticles (@Body Article article);
    @GET("articles")
    Call<List<Article>>allArticles();
    @GET("articles/{articleId}")
    Call<ArticleAndComments>searchArticle(@Path("articleId") String articleId);
    @POST("articles/{articleId}/comments")
    Call<PostResponse>createComment(@Path("articleId") String articleId, @Body Comment comment);


}
