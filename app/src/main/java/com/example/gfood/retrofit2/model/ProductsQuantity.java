package com.example.gfood.retrofit2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductsQuantity {

    @SerializedName("product")
    @Expose
    private String product;

    @SerializedName("quantity")
    @Expose
    private String quantity;

    public ProductsQuantity(String product, String quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
