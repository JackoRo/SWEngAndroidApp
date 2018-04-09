package com.group3.swengandroidapp;

import android.content.Context;
import android.widget.Toast;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;
import java.util.ArrayList;

/**
 * Favourites Handler stores a list of all favourited recipes, and is what each favourites button should
 * interract with to manage the Favourites.
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

    /**
     * Toggle favourite and display message on screen
     * @param context context to draw message to
     * @param id id of recipe
     */
    public void toggleFavourite(Context context, String id){
        toggleFavourite(id);
        // Message
        String recipeName = RemoteFileManager.getInstance().getRecipe(id).getTitle();
        Toast t =  Toast.makeText(context, recipeName + " removed from Favourites!", Toast.LENGTH_SHORT);
        if(this.contains(id)){
            t.setText(recipeName + " added to Favourites!");
        }
        t.show();
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
