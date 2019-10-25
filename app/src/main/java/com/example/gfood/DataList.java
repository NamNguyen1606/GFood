package com.example.gfood;

import com.example.gfood.retrofit2.model.ResultCart;
import com.example.gfood.retrofit2.model.ResultProduct;
import com.example.gfood.retrofit2.model.ResultRestaurant;

import java.util.List;

public class DataList {
    public static List<ResultCart> resultCarts;
    public static List<ResultProduct> resultProducts;
    public static List<ResultRestaurant> resultRestaurants;

    public static List<ResultCart> getResultCarts() {
        return resultCarts;
    }

    public static void setResultCarts(List<ResultCart> resultCarts) {
        DataList.resultCarts = resultCarts;
    }

    public static List<ResultProduct> getResultProducts() {
        return resultProducts;
    }

    public static void setResultProducts(List<ResultProduct> resultProducts) {
        DataList.resultProducts = resultProducts;
    }

    public static List<ResultRestaurant> getResultRestaurants() {
        return resultRestaurants;
    }

    public static void setResultRestaurants(List<ResultRestaurant> resultRestaurants) {
        DataList.resultRestaurants = resultRestaurants;
    }
}
