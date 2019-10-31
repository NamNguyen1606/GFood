package com.example.gfood.retrofit2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCard {
    public MyCard(String number, String expMonth, String expYear, String brand) {
        this.number = number;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.brand = brand;
    }

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("exp_month")
    @Expose
    private String expMonth;
    @SerializedName("exp_year")
    @Expose
    private String expYear;
    @SerializedName("brand")
    @Expose
    private String brand;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
