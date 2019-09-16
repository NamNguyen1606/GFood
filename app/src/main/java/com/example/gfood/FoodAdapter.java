package com.example.gfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.Food;
import com.example.gfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends BaseAdapter {
    public FoodAdapter(Context context, int foodLayout, List<Food> foodList) {
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

        TextView tv_name = (TextView) view.findViewById(R.id.tv_foodName);
        TextView tv_adress = (TextView) view.findViewById(R.id.tv_adress);
        ImageView imageView = view.findViewById(R.id.img_food);

        Food food = foodList.get(i);

        tv_name.setText(food.getNameFood());
        tv_adress.setText(food.getAdress());
        Picasso.with(context).load(food.getFoodPictureUrl()).into(imageView);

        return view;
    }
}
