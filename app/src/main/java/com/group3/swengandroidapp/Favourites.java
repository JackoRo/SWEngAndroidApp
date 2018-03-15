/*This class will receive arraylist of favourite recipes from home activity class and
 *display the recipes (currently in ListView).*/

package com.group3.swengandroidapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

public class Favourites extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    ArrayList<com.group3.swengandroidapp.XMLRenderer.Recipe> FavouriteRecipes = new ArrayList<Recipe>();
    ArrayList<Recipe.Icon> recipes;
    RecipeRecyclerViewAdaper favouritesAdapter;
    ArrayList<Recipe> recipeFavouritesList = new ArrayList<Recipe>();
    ListView listView;

    RecipeRecyclerViewAdaper displayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        super.onCreateDrawer();

        listView=(ListView)findViewById(R.id.recipe_list);
        setTitle("Favourites");
        FavouriteRecipes = (ArrayList<Recipe>)getIntent().getSerializableExtra("FavouriteRecipeExtra");

        // SETUP RECIPE CONTAINER
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchPage_recipeContainer);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        displayAdapter = new RecipeRecyclerViewAdaper(this);
        displayAdapter.setClickListener(this);
        recyclerView.setAdapter(displayAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + displayAdapter.getItem(position).getTitle() + ". ID: "+ displayAdapter.getItem(position).getId());
    }
}
