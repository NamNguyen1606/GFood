package com.example.gfood.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private RestaurantAdapter restaurantAdapter;
    private ProductAdapter productAdapter;
    public OnProductClick onProductClick;
    private Fragment fragment;
    private FragmentTransaction fragmentTransaction;
    private ListenerRefresh listenerRefresh;

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

        Context context = getContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        Log.e("Token Access: ", sharedPreferences.getString("Token_Access", ""));

        apiService = APIutils.getAPIService();

        apiService.getlListProduct("api/product/").enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productList = response.body().getResults();
                productAdapter = new ProductAdapter(getActivity(), R.layout.listview_product, productList);
                listView.setAdapter(productAdapter);
                productAdapter.productClick = onProductClick;
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("Run", "Wrong");
            }
        });
        return view;
    }


    // Error: finish fragment when move another fragment '
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fill data to list product
        tvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.getlListProduct("api/product/").enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Log.e("Run", "DO IT");
                        productList = response.body().getResults();
                        productAdapter = new ProductAdapter(getActivity(), R.layout.listview_product, productList);
                        listView.setAdapter(productAdapter);
                        productAdapter.productClick = onProductClick;
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e("Run", "Wrong");
                    }
                });
            }
        });

        // Fill data to restaurant list
        tvRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiService.getListRestaurant().enqueue(new Callback<Restaurant>() {
                    @Override
                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                        restaurantList = response.body().getResults();
                        restaurantAdapter = new RestaurantAdapter(getActivity(),R.layout.listview_restaurant, restaurantList);
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
              Intent intent = new Intent(getActivity(), RestaurantPageActivity.class);
              intent.putExtra("id",restaurantList.get(i).getId().toString());
              intent.putExtra("name", restaurantList.get(i).getName());
              intent.putExtra("detail", restaurantList.get(i).getDetail());
              intent.putExtra("address", restaurantList.get(i).getAddress());
              intent.putExtra("image", restaurantList.get(i).getImage());
              startActivity(intent);
            }
        });


        onProductClick = new OnProductClick() {
            @Override
            public void productItemClick() {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragment = getActivity().getSupportFragmentManager().getFragments().get(1);
                fragmentTransaction.detach(fragment).attach(fragment).commit();

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment = getActivity().getSupportFragmentManager().getFragments().get(1);
        fragmentTransaction.detach(fragment).attach(fragment).commit();
    }

    public interface ListenerRefresh{
        void onClickRefresh();
    }
}
