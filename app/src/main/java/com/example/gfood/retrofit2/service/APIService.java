package com.example.gfood.retrofit2.service;

import com.example.gfood.retrofit2.model.Account;
import com.example.gfood.retrofit2.model.Password;
import com.example.gfood.retrofit2.model.Token;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @POST("api/user/login/")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String username,
                      @Field("password") String password);

    @POST("api/user/register/")
    @FormUrlEncoded
    Call<Account> register(@Field("username") String username,
                           @Field("password") String password);

    @Headers("Content-Type: application/json")
    @POST("api/user/changepwd/")
    Call<ResponseBody> changePassword(@Header ("Authorization") String token,
                                      @Body Password password);
}

