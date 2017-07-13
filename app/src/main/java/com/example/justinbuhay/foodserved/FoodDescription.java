package com.example.justinbuhay.foodserved;

/**
 * Created by justinbuhay on 7/13/17.
 */

public class FoodDescription {

    private String foodTitle;
    private int tableNumber;
    private int onCompletion;

    public FoodDescription(String foodTitle, int tableNumber, int onCompletion){
        this.foodTitle = foodTitle;
        this.tableNumber = tableNumber;
        this.onCompletion = onCompletion;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getOnCompletion() {
        return onCompletion;
    }

}
