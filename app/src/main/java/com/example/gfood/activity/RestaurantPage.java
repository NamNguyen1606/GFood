package com.example.gfood.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.adapter.ProductAdapter;
import com.example.gfood.adapter.RestaurantAdapter;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantPage extends AppCompatActivity {

    final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private TextView tvResName, tvResAddress, tvResDetail;
    private ImageView imgResWallpaper;
    private ListView lvResMenu;
    private APIService apiService;
    private List<ResultProduct> productList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);
        Intent intent = this.getIntent();

        // get data from HomeFragment
        String resId = intent.getStringExtra("id");
        String resName = intent.getStringExtra("name");
        String resAddress = intent.getStringExtra("address");
        String resDetail = intent.getStringExtra("detail");
        String resImage = intent.getStringExtra("image");

        tvResName = (TextView) findViewById(R.id.tvResInName);
        tvResAddress = (TextView) findViewById(R.id.tvResInAddress);
        tvResDetail = (TextView) findViewById(R.id.tvResInDetail);
        imgResWallpaper = (ImageView) findViewById(R.id.imgResInWallpaper);
        lvResMenu = (ListView) findViewById(R.id.lvResInMenu);

        tvResName.setText(resName);
        tvResAddress.setText(resAddress);
        tvResDetail.setText(resDetail);
        Picasso.with(getBaseContext().getApplicationContext()).load(WEBSITE + resImage).into(imgResWallpaper);

        String url = "api/product/?restaurant=" + resId;
        apiService = APIutils.getAPIService();
        apiService.getlListProduct(url).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productList = response.body().getResults();
                productAdapter = new ProductAdapter(getApplicationContext(), R.layout.listview_product, productList);
                lvResMenu.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("RES" , "Wrong");
            }
        });
    }

}
