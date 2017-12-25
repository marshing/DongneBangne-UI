package com.hrmj.dongnebangne_android.chatroom;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by office on 2017-11-10.
 */

public interface MeetingManager {
    public static final String API_URL = "https://dongnebangne-185304.appspot.com/";
    @POST("meetings")
    Call<Chatroom> createMeeting(@Body Chatroom chatroom);
    @GET("meetings")
    Call<List<Chatroom>> allMeeting();
    @GET("meetings/{id}")
    Call<Chatroom> searchMeeting(@Path("id") String meetingId);
    @PUT("meetings/{id}/{email}/enter")
    Call<Chatroom> enterMeeting(@Path("id") String meetingId, @Path("email") String email);
    @PUT("meetings/{id}/{email}/exit")
    Call<Chatroom> exitMeeting(@Path("id") String meetingId, @Path("email") String email);
    @PATCH("meetings/{id}")
    Call<JsonObject> completeMeeting(@Path("id") String meetingId, @Field("email") String cheifEmail);
}
