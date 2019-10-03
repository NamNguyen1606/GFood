package com.example.gfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gfood.R;
import com.example.gfood.retrofit2.model.Product;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class ProductAdapter extends BaseAdapter {
    final String WEBSITE = "https://softwaredevelopmentproject.azurewebsites.net/";
    private Context context;
    private int ProductLayout;
    List<ResultProduct> productList;

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
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(ProductLayout, null);
            viewHolder.tvProdName = (TextView) view.findViewById(R.id.tvProdName);
            viewHolder.tvProdDetail = (TextView) view.findViewById(R.id.tvProdDetail);
            viewHolder.tvProdPrice = (TextView) view.findViewById(R.id.tvProdPrice);
            viewHolder.imgProd = view.findViewById(R.id.imgProd);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ResultProduct product = productList.get(i);

        viewHolder.tvProdName.setText(product.getName());
        viewHolder.tvProdDetail.setText(product.getDetail());
        viewHolder.tvProdPrice.setText(product.getPrice() + "");
        Picasso.with(context).load(WEBSITE + product.getImage()).into(viewHolder.imgProd);

        return view;
    }
}
