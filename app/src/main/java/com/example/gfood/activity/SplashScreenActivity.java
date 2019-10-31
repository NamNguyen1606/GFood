package com.example.gfood.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.gfood.R;
import com.example.gfood.adapter.ProductAdapter;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    private static  int SPLASH_TIME_OUT = 3000;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final HomeFragment homeFragment = new HomeFragment();

        apiService = APIutils.getAPIService();
        try{
            apiService.getlListProduct("api/product/").enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    homeFragment.productList = response.body().getResults();
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.e("Run", "Wrong");
                }
            });
        } catch (Exception e){

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        },SPLASH_TIME_OUT );
    }
}
