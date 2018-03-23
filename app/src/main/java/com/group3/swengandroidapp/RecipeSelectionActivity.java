package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

public class RecipeSelectionActivity extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);
        Intent intent = getIntent();
        id = intent.getStringExtra(PythonClient.ID);

        final Button button = findViewById(R.id.recipe_selection_start_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent newIntent = new Intent(getApplicationContext(), PresentationActivity.class);
                newIntent.putExtra(PythonClient.ID, id);
                startActivity(newIntent);
            }
        });

        final ImageButton favourites = findViewById(R.id.recipe_selection_thumbnail_favourites_button);
        favourites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(id!=null){
                    FavouritesHandler.getInstance().toggleFavourite(id);
                    if (FavouritesHandler.getInstance().contains(id)) {
                        favourites.setImageResource(R.drawable.heart_on);
                    } else {
                        favourites.setImageResource(R.drawable.heart_off);
                    }
                }
            }
        });
        if(id!=null){
            if (FavouritesHandler.getInstance().contains(id)) {
                favourites.setImageResource(R.drawable.heart_on);
            } else {
                favourites.setImageResource(R.drawable.heart_off);
            }
        }else{
            favourites.setImageResource(R.drawable.heart_off);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        ImageView thumbnail = findViewById(R.id.recipe_selection_thumbnail_image);
        TextView time = findViewById(R.id.recipe_selection_thumbnail_time);
        TextView description = findViewById(R.id.recipe_selection_description);
        TextView author = findViewById(R.id.recipe_selection_author);
        ImageView spicy = findViewById(R.id.recipe_selection_thumbnail_filter_spicy);
        ImageView veg = findViewById(R.id.recipe_selection_thumbnail_filter_vegetarian);
        ImageView vegan = findViewById(R.id.recipe_selection_thumbnail_filter_vegan);
        ImageView lactose = findViewById(R.id.recipe_selection_thumbnail_filter_lactose);
        ImageView nuts = findViewById(R.id.recipe_selection_thumbnail_filter_nuts);
        ImageView gluten = findViewById(R.id.recipe_selection_thumbnail_filter_gluten);
        ListView ingredients = findViewById(R.id.recipe_selection_ingredients);

        Recipe recipe;
        recipe = RemoteFileManager.getInstance().getRecipe(id);
        if(recipe==null){
            recipe = new Recipe("Recipe not found!", "n/a", (String)("ID: " + id), "n/a");
        }
        if(recipe.getSpicy()){
            spicy.setImageResource(R.drawable.spicy_filter);
        }
        if(recipe.getVegetarian()){
            spicy.setImageResource(R.drawable.vegetarian_filter);
        }
        if(recipe.getVegan()){
            spicy.setImageResource(R.drawable.vegan_filter);
        }
        if(recipe.getLactose()){
            spicy.setImageResource(R.drawable.lactosefree_filter);
        }
        if(recipe.getNuts()){
            spicy.setImageResource(R.drawable.nutfree_filter);
        }
        if(recipe.getGluten()){
            spicy.setImageResource(R.drawable.glutenfree_filter);
        }
        setTitle(recipe.getTitle());
        thumbnail.setImageResource(R.drawable.thumbnail);
        time.setText(recipe.getTime());
        author.setText(recipe.getAuthor());


        //TODO: Generate and draw recipe icon
        description.setText(recipe.getDescription());

        //TODO: Generate and draw ingredients list
        //ingredients.setText(recipe.getIngredients());

    }
}
