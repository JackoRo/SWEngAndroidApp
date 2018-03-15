package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

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

        final Button favourites = findViewById(R.id.recipe_selection_favourites_button);
        favourites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(id!=null){
                    FavouritesHandler.getInstance().toggleFavourite(id);
                    if (FavouritesHandler.getInstance().contains(id)) {
                        favourites.setBackgroundResource(R.drawable.favfull);
                    } else {
                        favourites.setBackgroundResource(R.drawable.favempty);
                    }
                }
            }
        });
        if(id!=null){
            if (FavouritesHandler.getInstance().contains(id)) {
                favourites.setBackgroundResource(R.drawable.favfull);
            } else {
                favourites.setBackgroundResource(R.drawable.favempty);
            }
        }else{
            favourites.setBackgroundResource(R.drawable.favempty);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();


        Recipe recipe;
        recipe = RemoteFileManager.getInstance().getRecipe(id);
        if(recipe==null){
            recipe = new Recipe("Recipe not found!", "n/a", (String)("ID: " + id), "n/a");
        }
        ImageView thumbnail = findViewById(R.id.recipe_selection_thumbnail);
        TextView recipeName = findViewById(R.id.recipe_selection_recipe_name);
        TextView description = findViewById(R.id.recipe_selection_description);
        TextView ingredients = findViewById(R.id.recipe_selection_ingredients);

        recipeName.setText(recipe.getTitle());
        //TODO: Generate and draw recipe icon
        description.setText(recipe.getDescription());

        //TODO: Generate and draw ingredients list
        //ingredients.setText(recipe.getIngredients());

    }
}
