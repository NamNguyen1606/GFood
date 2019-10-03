package com.example.gfood.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.adapter.ProductAdapter;
import com.example.gfood.adapter.RestaurantAdapter;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.Restaurant;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.model.ResultRestaurant;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView tvRestaurant, tvFood;
    private EditText edtSearch;
    private ListView listView;
    private List<ResultRestaurant> restaurantList;
    private List<ResultProduct> productList;
    APIService apiService;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvRestaurant = (TextView) view.findViewById(R.id.fragHome_search_tvRestaurantTab);
        tvFood = (TextView) view.findViewById(R.id.fragHome_search_tvFoodTab);
        edtSearch = (EditText) view.findViewById(R.id.fragHome_search_edtSearch);
        listView = (ListView) view.findViewById(R.id.fragHome_listview);

        apiService = APIutils.getAPIService();

        apiService.getListRestaurant().enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                restaurantList = response.body().getResults();
                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getActivity(),R.layout.listview_restaurant, restaurantList);
                listView.setAdapter(restaurantAdapter);
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
            }
        });
        return view;
    }


    // Error: finish fragment when move another fragment '
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.getlListProduct("api/product/?page=1").enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        productList = response.body().getResults();
                        ProductAdapter productAdapter = new ProductAdapter(getActivity(), R.layout.listview_product, productList);
                        listView.setAdapter(productAdapter);
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
            }
        });

        tvRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.getListRestaurant().enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        restaurantList = response.body().getResults();
                        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getActivity(),R.layout.listview_restaurant, restaurantList);
                        listView.setAdapter(restaurantAdapter);
                    }

                    @Override
                    public void onFailure(Call<Restaurant> call, Throwable t) {
                    }
                });
            }
        });

        // move to Fragment Restaurant Info
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent = new Intent(getActivity(), RestaurantPage.class);
              intent.putExtra("id",restaurantList.get(i).getId().toString());
              intent.putExtra("name", restaurantList.get(i).getName());
              intent.putExtra("detail", restaurantList.get(i).getDetail());
              intent.putExtra("address", restaurantList.get(i).getAddress());
              intent.putExtra("image", restaurantList.get(i).getImage());
              startActivity(intent);
            }
        });
    }
}