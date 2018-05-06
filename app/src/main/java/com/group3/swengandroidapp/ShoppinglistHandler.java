package com.group3.swengandroidapp;

import android.util.Log;

import com.group3.swengandroidapp.XMLRenderer.Ingredient;

import java.util.ArrayList;

public class ShoppinglistHandler {

    private ArrayList<Ingredient> items;

    private static ShoppinglistHandler handler = new ShoppinglistHandler();

    public ShoppinglistHandler(){
        items = new ArrayList<>(0);
    }

    // Get the global handler
    public static ShoppinglistHandler getInstance(){return handler;}

    public void addToShoppingList(ArrayList<Ingredient> list) {

        items.addAll(list);
        Log.d("ShoppingListHandler:","Ingredients added");
        for (Ingredient ingredient : list) {
            System.out.println(ingredient);
        }
        Log.d("ShoppingListHandler", items.get(0).getName());
        Log.d("ShoppingListHandler", items.get(0).getQuantity());

    }

    public ArrayList<Ingredient> getItems() {
        return items;
    }

    public void removeFromArrayList(){
        items.clear();
    }
}
