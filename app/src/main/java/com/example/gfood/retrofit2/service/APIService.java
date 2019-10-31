package com.example.gfood.retrofit2.service;

import com.example.gfood.retrofit2.model.Account;
import com.example.gfood.retrofit2.model.Bill;
import com.example.gfood.retrofit2.model.Card;
import com.example.gfood.retrofit2.model.Cart;
import com.example.gfood.retrofit2.model.MyCard;
import com.example.gfood.retrofit2.model.Password;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.ProductsQuantity;
import com.example.gfood.retrofit2.model.Quantity;
import com.example.gfood.retrofit2.model.Restaurant;
import com.example.gfood.retrofit2.model.Token;
import com.example.gfood.retrofit2.model.UserInfo;
import com.example.gfood.retrofit2.model.UserInfomation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIService {

    //Login
    @POST("api/user/login/")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String username,
                      @Field("password") String password);

    //Register
    @POST("api/user/register/")
    @FormUrlEncoded
    Call<Account> register(@Field("username") String username,
                           @Field("password") String password);

    // Change password
    @Headers("Content-Type: application/json")
    @POST("api/user/changepwd/")
    Call<ResponseBody> changePassword(@Header("Authorization") String token,
                                      @Body Password password);

    // Add Product to cart
    @Headers("Content-Type: application/json")
    @POST("api/item/")
    Call<ResponseBody> addProdToCart(@Header("Authorization") String token,
                                     @Body ProductsQuantity productsQuantity);

    // Add Card
    @Headers("Content-Type: application/json")
    @POST("api/user/card/")
    Call<ResponseBody> addCard(@Header("Authorization") String token,
                               @Body Card card);

    // Payment
    @POST("api/bill/")
    Call<Bill> payment(@Header("Authorization") String token);


    // get list product in cart
    @GET("api/cart/")
    Call<Cart> getListProductInCart(@Header("Authorization") String token);

    // Get list restaurant
    @GET("api/restaurant/")
    Call<Restaurant> getListRestaurant();

    // Get list Product
    @GET
    Call<Product> getlListProduct(@Url String url);

    // Get user info
    @GET("api.v2/user/")
    Call<UserInfo> getUserInfo(@Header("Authorization") String token);

    // Get info card
    @GET("api/user/mycard/")
    Call<MyCard> getInfoCard(@Header("Authorization") String token);


    // Delete product in cart
    @DELETE
    Call<ResponseBody> deleteProductInCart(@Header("Authorization") String token, @Url String url);

    //Delete card
    @DELETE("api/user/mycard/")
    Call<ResponseBody> deleteMyCard(@Header("Authorization") String token);

    // Edit product quantity
    @Headers("Content-Type: application/json")
    @PUT
    Call<ResponseBody> editProductQuantity(@Header("Authorization") String token, @Url String url, @Body Quantity quantity);

    // Edit profile
    @Headers("Content-Type: application/json")
    @PATCH
    Call<UserInfo> editProfile(@Header("Authorization") String token, @Url String url,  @Body UserInfomation userInfomation);
}