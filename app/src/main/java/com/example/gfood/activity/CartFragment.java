package com.example.gfood.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.adapter.CartAdapter;
import com.example.gfood.adapter.ProductAdapter;
import com.example.gfood.retrofit2.model.Cart;
import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private TextView tvTotal;
    private Button btnPayment, btnAddPro;
    private ListView lvCart;
    private SharedPreferences sharedPreferences;
    private APIService apiService;
    private List<ResultCart> resultCartList;
    private CartAdapter cartAdapter;
    private ProductAdapter productAdapter;
    private OnItemClick onItemClick;
    private FragmentTransaction fragmentTransaction;
    private int total = 0;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        tvTotal = (TextView) view.findViewById(R.id.tvFragCartTotal);
        btnPayment = (Button) view.findViewById(R.id.btnFragCartPayment);
        lvCart = (ListView) view.findViewById(R.id.lvFragCart);

        apiService = APIutils.getAPIService();
        Context context = getContext();
        sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("Token_Access", "");

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Show Product in cart
        apiService.getListProductInCart(token).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                resultCartList = response.body().getResults();
                cartAdapter = new CartAdapter(getContext(),R.layout.listview_cart, resultCartList);
                lvCart.setAdapter(cartAdapter);
                cartAdapter.onItemClick = onItemClick;
                // Total all item in cart

                for(int i = 0; i < response.body().getCount(); i++){
                    int quantity = response.body().getResults().get(i).getQuantity();
                    int price = response.body().getResults().get(i).getPrice();
                    total = total + (quantity * price);
                }
                tvTotal.setText(total+"");
                total = 0;

            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        onItemClick = new OnItemClick() {
            @Override
            public void itemClick(int index, int price) {
                resultCartList.remove(index);
                tvTotal.setText(price+"");
                cartAdapter.notifyDataSetChanged();
            }
        };

    }

}
