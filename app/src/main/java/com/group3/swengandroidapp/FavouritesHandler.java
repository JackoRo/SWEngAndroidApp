package com.group3.swengandroidapp;

import java.util.ArrayList;

/**
 * Created by Marco on 15/03/2018.
 */

public class FavouritesHandler {

    private ArrayList<String> items;

    private static FavouritesHandler handler = new FavouritesHandler();

    private FavouritesHandler(){
        items = new ArrayList<>(0);
    }

    // Get the global handler
    public static FavouritesHandler getInstance(){return handler;}

    public void toggleFavourite(String id){
        boolean found = false;
        for(String s : items){
            if(s.matches(id)){
                found = true;
                items.remove(s);
                break;
            }
        }
        if(!found) items.add(id);
    }

    public ArrayList<String> getFavourites(){
        return items;
    }

    public boolean contains(String id){
        for(String s : items){
            if(s.matches(id)){
                return true;

            }
        }
        return false;
    }

    public void print(){
        System.out.println("FAOURITES:");
        for(String s : items){
            System.out.println(s);
        }
    }


}
