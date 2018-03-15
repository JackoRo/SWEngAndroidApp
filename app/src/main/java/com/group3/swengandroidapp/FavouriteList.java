/*This class will receive arraylist of favourite recipes from home activity class and
 *display the recipes (currently in ListView).*/

package com.group3.swengandroidapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

public class FavouriteList extends MainActivity {

    ArrayList<com.group3.swengandroidapp.XMLRenderer.Recipe> FavouriteRecipes = new ArrayList<Recipe>();
    FavouritesAdapter favouritesAdapter;
    ArrayList<Recipe> recipeFavouritesList = new ArrayList<Recipe>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        super.onCreateDrawer();

        listView=(ListView)findViewById(R.id.recipe_list);

        setTitle("Favourites");

        FavouriteRecipes = (ArrayList<Recipe>)getIntent().getSerializableExtra("FavouriteRecipeExtra");

        favouritesAdapter = new FavouritesAdapter(activity_favourite_list.this, 0, recipeFavouritesList);
        favouritesAdapter.setAdapter(favouritesAdapter);






    }

}
