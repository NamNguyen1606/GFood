package com.example.gfood.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.service.APIService;

import java.util.List;

public class CartAdapter extends BaseAdapter {

final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private Context context;
    private List<ResultCart> resultCartList;
    private int cartLayout;
    private APIService apiService;
    private SharedPreferences sharedPreferences;

    public CartAdapter(Context context, int cartLayout, List<ResultCart> resultCartList) {
        this.context = context;
        this.resultCartList = resultCartList;
        this.cartLayout = cartLayout;
    }

    @Override
    public int getCount() {
        return resultCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView imgCartList;
        TextView tvProdName, tvResName, tvProdPrice, tvQuantity;
        Button btnSub, btnPlus, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){

        }
        return null;
    }
}
