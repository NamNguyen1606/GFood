package com.example.gfood.retrofit2.service;

import com.example.gfood.retrofit2.RetrofitClient;

public class APIutils {

    public static final String BASE_URL = "https://softwaredevelopmentproject.azurewebsites.net/";

    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
