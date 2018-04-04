package com.group3.swengandroidapp;

import android.content.Context;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

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
        if(!found){
            items.add(id);
        }
    }

    public void toggleFavourite(Context c, String id){
        boolean found = false;
        for(String s : items){
            if(s.matches(id)){
                found = true;
                items.remove(s);
                Toast.makeText(c, RemoteFileManager.getInstance().getRecipe(id).getTitle() + " removed from favourites!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(!found){
            items.add(id);
            Toast.makeText(c, RemoteFileManager.getInstance().getRecipe(id).getTitle() + " added to favourites!", Toast.LENGTH_SHORT).show();
        }
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
}
