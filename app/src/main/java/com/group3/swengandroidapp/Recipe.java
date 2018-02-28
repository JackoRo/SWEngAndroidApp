package com.group3.swengandroidapp;



/**
 * Created by User 1 on 26/02/2018.
 */



public class Recipe {


    private String recipeName;
    private int rating;

    public Recipe(String recipeName, int rating){
        this.recipeName = recipeName;
        this.rating = rating;
    }


    public String getRecipeName(){
        return this.recipeName;
    }

    public void setRecipeName(String recipeName){
        this.recipeName = recipeName;
    }



}
