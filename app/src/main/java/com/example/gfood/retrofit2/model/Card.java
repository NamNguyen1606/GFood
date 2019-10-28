package com.example.gfood.retrofit2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("card_number")
    @Expose
    private long numberCard;

    @SerializedName("exp_month")
    @Expose
    private int expMonth;

    @SerializedName("exp_year")
    @Expose
    private int expYear;

    @SerializedName("cvc")
    @Expose
    private int cvv;


    public Card(long numberCard, int expMonth, int expYear, int cvv) {
        this.numberCard = numberCard;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
    }

    public long getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(long numberCard) {
        this.numberCard = numberCard;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }


}
