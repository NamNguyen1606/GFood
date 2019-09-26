package com.example.gfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    public RestaurantAdapter(Context context, int foodLayout, List<Food> foodList) {
        this.context = context;
        this.foodLayout = foodLayout;
        this.foodList = foodList;
    }

    private Context context;
    private int foodLayout;
    private List<Food> foodList;
    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(foodLayout, null);

        TextView tvResName = (TextView) view.findViewById(R.id.tvResName);
        TextView tvResAdress = (TextView) view.findViewById(R.id.tvResAdress);
        ImageView imgRes = view.findViewById(R.id.imgRes);

        Food food = foodList.get(i);

        tvResName.setText(food.getNameFood());
        tvResAdress.setText(food.getAdress());
        Picasso.with(context).load(food.getFoodPictureUrl()).into(imgRes);

        return view;
    }
}
