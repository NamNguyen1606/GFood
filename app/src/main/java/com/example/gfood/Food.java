package com.example.gfood;

import java.net.URL;

public class Food {

    private String nameFood;
    private String adress;
    private String foodPictureUrl;

    public Food(String nameFood, String adress, String foodPictureUrl) {
        this.nameFood = nameFood;
        this.adress = adress;
        this.foodPictureUrl = foodPictureUrl;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getFoodPictureUrl() {
        return foodPictureUrl;
    }

    public void setFoodPictureUrl(String foodPictureUrl) {
        this.foodPictureUrl = foodPictureUrl;
    }


}
