package com.example.gfood.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import com.example.gfood.R;
import com.example.gfood.activity.CartFragment;
import com.example.gfood.activity.OnItemClick;
import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.service.APIService;
import com.example.gfood.retrofit2.service.APIutils;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends BaseAdapter {

    final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private Context context;
    private List<ResultCart> resultCartList;
    private int cartLayout;

    private int total = 0;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    public OnItemClick onItemClick;

    public CartAdapter(Context context, int cartLayout, List<ResultCart> resultCartList) {
        this.context = context;
        this.resultCartList = resultCartList;
        this.cartLayout = cartLayout;
    }
    public boolean getValueCart() {
        return valueCart;
    }

    public void setValueCart(boolean valueCart) {
        this.valueCart = valueCart;
    }

    private boolean valueCart = false;


    @Override
    public int getCount() {
        return resultCartList.size();
    }

    @Override
    public ResultCart getItem(int position) {
        return resultCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public class ViewHolder {
        ImageView imgCartList;
        TextView tvProdName, tvResName, tvProdPrice, tvQuantity;
        Button btnSub, btnPlus, btnDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(cartLayout, null);
            viewHolder.imgCartList = (ImageView) convertView.findViewById(R.id.img_CartList);
            viewHolder.tvProdName = (TextView) convertView.findViewById(R.id.tvProdName_CartList);
            viewHolder.tvProdPrice = (TextView) convertView.findViewById(R.id.tvProdPrice_CartList);
            viewHolder.tvResName = (TextView) convertView.findViewById(R.id.tvResName_CartList);
            viewHolder.tvQuantity = (TextView) convertView.findViewById(R.id.tvQuantity_CartList);
            viewHolder.btnPlus = (Button) convertView.findViewById(R.id.btnPus_CartList);
            viewHolder.btnSub = (Button) convertView.findViewById(R.id.btnSub_CartList);
            viewHolder.btnDelete = (Button) convertView.findViewById(R.id.btnDelProd_CartList);
            convertView.setTag(viewHolder);

            apiService = APIutils.getAPIService();
            sharedPreferences = context.getSharedPreferences("Acount_info", Context.MODE_PRIVATE);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String productName = resultCartList.get(position).getProduct().getName();
        final String restaurant = resultCartList.get(position).getProduct().getRestaurant().getName();
        int price = resultCartList.get(position).getPrice();
        int quantity = resultCartList.get(position).getQuantity();
        String imgUrl = resultCartList.get(position).getProduct().getImage();

        viewHolder.tvProdName.setText(productName);
        viewHolder.tvResName.setText(restaurant);
        viewHolder.tvProdPrice.setText(price + "");
        viewHolder.tvQuantity.setText(quantity + "");
        Picasso.with(context).load(WEBSITE + imgUrl).resize(200, 200).into(viewHolder.imgCartList);

        //Total all item in cart
        int total = 0;
        for(int i = 0; i < resultCartList.size(); i++){
            int price_ = resultCartList.get(i).getPrice();
            int quantity_ = resultCartList.get(i).getQuantity();
            total = total + (quantity_ * price_);
        }
        setTotal(total);

        Log.e("Total ", getTotal() + "");

        // Plus and subtract item quantity

        //Plus item quantity
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = resultCartList.get(position).getQuantity();
                resultCartList.get(position).setQuantity(quantity + 1);
                Log.e("Plus" + position, quantity + 1 +"");
                finalViewHolder.tvQuantity.setText(quantity + 1 +"");

                // Total
                int total = getTotal();
                int price = resultCartList.get(position).getPrice();
                total = total + price;
                setTotal(total);
                onItemClick.itemQuantityClick(total);
            }
        });

        //Subtract item quantity
        viewHolder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = resultCartList.get(position).getQuantity();
                if(quantity > 1){
                    resultCartList.get(position).setQuantity(quantity - 1);
                    Log.e("Sub" + position, quantity - 1 +"");
                    finalViewHolder.tvQuantity.setText(quantity - 1 +"");

                    // Total
                    int total = getTotal();
                    int price = resultCartList.get(position).getPrice();
                    total = total - price;
                    setTotal(total);
                    onItemClick.itemQuantityClick(total);
                }

            }
        });

        // Delete Product from cart
        final String url = "api/item/" + resultCartList.get(position).getId();
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String token = sharedPreferences.getString("Token_Access", "");

                apiService.deleteProductInCart(token, url).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        int price = resultCartList.get(position).getPrice();
                        int quantity = resultCartList.get(position).getQuantity();
                        int total = getTotal();
                        total = total - (price * quantity);
                        setTotal(total);
                        Log.e("Total DELETE " + position, getTotal() + "");
                        onItemClick.itemClick(position, total);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ERROR", t.getMessage());
                    }
                });
            }
        });

        return convertView;
    }

}
