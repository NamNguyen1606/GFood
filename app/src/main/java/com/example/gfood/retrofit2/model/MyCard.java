package com.example.gfood.retrofit2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCard {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("exp_month")
    @Expose
    private Integer expMonth;
    @SerializedName("exp_year")
    @Expose
    private Integer expYear;
    @SerializedName("brand")
    @Expose
    private String brand;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(Integer expMonth) {
        this.expMonth = expMonth;
    }

    public Integer getExpYear() {
        return expYear;
    }

    public void setExpYear(Integer expYear) {
        this.expYear = expYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
