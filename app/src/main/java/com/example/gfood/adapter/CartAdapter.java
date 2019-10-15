package com.example.gfood.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.service.APIService;
import com.squareup.picasso.Picasso;

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
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String productName = resultCartList.get(position).getProduct().getName();
        String restaurant = resultCartList.get(position).getProduct().getRestaurant().getName();
        int price = resultCartList.get(position).getPrice();
        int quantity = resultCartList.get(position).getQuantity();
        String imgUrl = resultCartList.get(position).getProduct().getImage();

        viewHolder.tvProdName.setText(productName);
        viewHolder.tvResName.setText(restaurant);
        viewHolder.tvProdPrice.setText(price+"");
        viewHolder.tvQuantity.setText(quantity+"");
        Picasso.with(context).load(WEBSITE + imgUrl).into(viewHolder.imgCartList);
        return convertView;
    }
}
