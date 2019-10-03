package com.example.gfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.ResultRestaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter {

    final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private Context context;
    private int foodLayout;
    private List<ResultRestaurant> restaurantList;

    public RestaurantAdapter(Context context, int foodLayout, List<ResultRestaurant> foodList) {
        this.context = context;
        this.foodLayout = foodLayout;
        this.restaurantList = foodList;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView tvResName, tvResAdress;
        ImageView imgRes;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(foodLayout, null);
            viewHolder.tvResName = (TextView) view.findViewById(R.id.tvResName);
            viewHolder.tvResAdress = (TextView) view.findViewById(R.id.tvResAdress);
            viewHolder.imgRes = view.findViewById(R.id.imgRes);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ResultRestaurant food = restaurantList.get(i);

        viewHolder.tvResName.setText(food.getName());
        viewHolder.tvResAdress.setText(food.getAddress());
        Picasso.with(context).load(WEBSITE + food.getImage()).into(viewHolder.imgRes);

        return view;
    }
}
