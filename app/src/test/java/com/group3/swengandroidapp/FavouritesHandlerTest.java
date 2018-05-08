package com.group3.swengandroidapp;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by mlowt on 15/03/2018.
 */

public class FavouritesHandlerTest {

    String recipe1 = "0000";
    String recipe2 = "0001";
    String recipe3 = "0002";



    @Test public void testRecipeAddingAndRemoval(){


        FavouritesHandler.getInstance().getFavourites().add(recipe1);

        assertTrue(FavouritesHandler.getInstance().contains(recipe1));

        FavouritesHandler.getInstance().toggleFavourite(recipe1);

        assertFalse(FavouritesHandler.getInstance().contains(recipe1));

        // Clear Favourites for next test
        FavouritesHandler.getInstance().getFavourites().clear();


    }

    @Test public void testToggle(){
        FavouritesHandler.getInstance().toggleFavourite(recipe1);

        assertTrue(FavouritesHandler.getInstance().contains(recipe1));

        FavouritesHandler.getInstance().toggleFavourite(recipe1);

        assertFalse(FavouritesHandler.getInstance().contains(recipe1));
        // Clear Favourites for next test
        FavouritesHandler.getInstance().getFavourites().clear();
    }

    @Test public void testMultipleRecipes(){
        FavouritesHandler.getInstance().toggleFavourite(recipe1);
        FavouritesHandler.getInstance().toggleFavourite(recipe3);

        assertTrue(FavouritesHandler.getInstance().contains(recipe1));
        assertFalse(FavouritesHandler.getInstance().contains(recipe2));
        assertTrue(FavouritesHandler.getInstance().contains(recipe3));
        // Clear Favourites for next test
        FavouritesHandler.getInstance().getFavourites().clear();


    }

}
