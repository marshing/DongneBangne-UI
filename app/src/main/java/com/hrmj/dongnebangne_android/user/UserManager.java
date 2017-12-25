package com.hrmj.dongnebangne_android.user;



import android.accounts.Account;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by office on 2017-10-22.
 */

public interface UserManager {

    public static final String API_URL = "https://dongnebangne-185304.appspot.com/";

    //1-1
 //   @FormUrlEncoded
  //  @FormUrlEncoded
    @POST("accounts")
    Call<AccountResponse> createUser(@Body User user);
    //1-2
    @GET("accounts")
    Call<List<User>> allUser();
    //1-3
    @GET("accounts/{id}")
    Call<User> searchUser(@Path("id") String email);
    //1-4
    @PUT("accounts/{id}")
    Call<AccountResponse> modifyUser(@Path("id") String email, @Body User user);
    //1-5
    @DELETE("accounts/{id}")
    Call<AccountResponse> deleteUser(@Path("id") String email);
    @POST("accounts/{id}/hastags")
    Call<User> addKeyword(@Path("id") String id, @Body Hastag hastag);
//    @GET("accounts/{id}/meeting")
//    Call<>





}
