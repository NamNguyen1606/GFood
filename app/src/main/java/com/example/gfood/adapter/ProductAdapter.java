package com.example.gfood.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gfood.R;
import com.example.gfood.activity.HomePageActivity;
import com.example.gfood.activity.OnItemClick;
import com.example.gfood.activity.OnProductClick;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.ProductsQuantity;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends BaseAdapter {
    final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private Context context;
    private int ProductLayout;
    private List<ResultProduct> productList;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    public OnProductClick productClick;



    public ProductAdapter(Context context, int layout, List<ResultProduct> productList) {
        this.context = context;
        this.ProductLayout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public class ViewHolder {
        TextView tvProdName, tvProdDetail, tvProdPrice;
        ImageView imgProd;
        EditText edtQuantity;
        Button btnAddProduct;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ProductLayout, null);
            viewHolder.tvProdName = (TextView) view.findViewById(R.id.tvProdName);
            viewHolder.tvProdDetail = (TextView) view.findViewById(R.id.tvProdDetail);
            viewHolder.tvProdPrice = (TextView) view.findViewById(R.id.tvProdPrice);
            viewHolder.imgProd =(ImageView) view.findViewById(R.id.imgProd);
            viewHolder.edtQuantity = (EditText) view.findViewById(R.id.edtProdQuantity);
            viewHolder.btnAddProduct = (Button) view.findViewById(R.id.btnProdAdd);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ResultProduct product = productList.get(i);

        viewHolder.tvProdName.setText(product.getName());
        viewHolder.tvProdDetail.setText(product.getDetail());
        viewHolder.tvProdPrice.setText(product.getPrice() + "");
        Picasso.with(context).load(WEBSITE + product.getImage()).into(viewHolder.imgProd);

        final ViewHolder finalViewHolder = viewHolder;

        //ADD PRODUCT TO CART
        viewHolder.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("Token_Access", null);
                int prodQuantity = Integer.parseInt(finalViewHolder.edtQuantity.getText().toString());
                String prodId = productList.get(i).getId().toString();

                apiService = APIutils.getAPIService();
                ProductsQuantity productsQuantity = new ProductsQuantity(prodId, prodQuantity);


                apiService.addProdToCart(token, productsQuantity).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Product was added", Toast.LENGTH_SHORT).show();
                        productClick.productItemClick();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Error Add Product", t.getMessage());
                    }
                });
            }
        });
        return view;
    }
}
