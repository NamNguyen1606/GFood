package com.example.gfood.activity;


import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.adapter.CartAdapter;
import com.example.gfood.adapter.ProductAdapter;
import com.example.gfood.retrofit2.model.Cart;
import com.example.gfood.retrofit2.model.Quantity;
import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private final String WEBSITE_URL = "https://softwaredevelopmentproject.azurewebsites.net/api/item/";
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
        // Refresh cart when product was deleted
        onItemClick = new OnItemClick() {
            // Delete item
            @Override
            public void itemClick(int index, int price) {
                resultCartList.remove(index);
                tvTotal.setText(price+"");
                cartAdapter.notifyDataSetChanged();
            }
            //Plus or subtract item quantity
            @Override
            public void itemQuantityClick(int price) {
                tvTotal.setText(price+"");
                cartAdapter.notifyDataSetChanged();
            }
        };

        // Payment
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityProduct = cartAdapter.getCount();
                for (int i = 0; i < quantityProduct; i++){
                    int idProduct = cartAdapter.getItem(i).getId();
                    int quantity = cartAdapter.getItem(i).getQuantity();

                    Quantity quantityItem = new Quantity(quantity);
                    String url = WEBSITE_URL + idProduct + "/";
                    // Edit product quantity
                    final String token = sharedPreferences.getString("Token_Access", "");
                    apiService.editProductQuantity(token, url, quantityItem).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.e("update", "DONE");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("update", t.getMessage());
                        }
                    });
                }
                dialogAddCard();
            }
        });

    }

    private void dialogAddCard(){
        Context context;
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_payment);

        EditText edtCardNumber = (EditText) dialog.findViewById(R.id.edtPayDiaCardNumber);
        EditText edtExpMonth = (EditText) dialog.findViewById(R.id.edtPayDiaMonth);
        EditText edtExpYear = (EditText) dialog.findViewById(R.id.edtPayDiaYear);
        EditText edtCVV = (EditText) dialog.findViewById(R.id.edtPayDiaCVV);
        Button btnPayment = (Button) dialog.findViewById(R.id.btnPayDiaPayment);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnPayDiaCancel);
        dialog.show();

        // Payment
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }
}
