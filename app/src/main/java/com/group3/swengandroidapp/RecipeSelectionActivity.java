package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

public class RecipeSelectionActivity extends AppCompatActivity {
    String id;
    ImageView icon;
    private ImageDownloaderListener imageDownloaderListener;
    private Recipe recipe;
    ArrayList<String> ingredientsList;


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

        final Button listButton = findViewById(R.id.recipe_selection_addToList_button);
        listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                  String toAdd[] = ingredients.setText(recipe.generateIngredientsString());
//                  if (toAdd.length > 0) {
//                         for (int i=0; i < toAdd.length; i++){
//                              ingredientsList.add(toAdd[i]);
//                         }
//                  }
                ShoppinglistHandler.getInstance().addToShoppingList(recipe.getIngredients());
                Toast.makeText(getApplicationContext(),"Recipes added to shopping list!", Toast.LENGTH_SHORT).show();

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

        // Fill the screen with all the information!

        icon = findViewById(R.id.recipe_selection_thumbnail_image);
        TextView time = findViewById(R.id.recipe_selection_thumbnail_time);
        TextView description = findViewById(R.id.recipe_selection_description);
        TextView author = findViewById(R.id.recipe_selection_author);
        ImageView spicy = findViewById(R.id.recipe_selection_thumbnail_filter_spicy);
        ImageView veg = findViewById(R.id.recipe_selection_thumbnail_filter_vegetarian);
        ImageView vegan = findViewById(R.id.recipe_selection_thumbnail_filter_vegan);
        ImageView lactose = findViewById(R.id.recipe_selection_thumbnail_filter_lactose);
        ImageView nuts = findViewById(R.id.recipe_selection_thumbnail_filter_nuts);
        ImageView gluten = findViewById(R.id.recipe_selection_thumbnail_filter_gluten);
        TextView ingredients = findViewById(R.id.recipe_selection_ingredients);

        recipe = RemoteFileManager.getInstance().getRecipe(id);

        if(recipe==null){
            recipe = new Recipe("Recipe not found!", "n/a", ("UPDATED_RECIPE_ID: " + id), "n/a");
        }else{
            // Recipe is found, add to history
            HistoryHandler.getInstance().append(recipe.getID());
        }

        setTitle(recipe.getTitle());        // Set page title to the recipe title

        time.setText(recipe.getTime());
        author.setText(recipe.getAuthor());
        description.setText(recipe.getDescription());
        ingredients.setText(recipe.generateIngredientsString());

        // FILTERS
        // If any filters are true, replace filter icon with the "on" versions (default = off)
        if(recipe.getSpicy()){
            spicy.setImageResource(R.drawable.spicy_filter);
        }
        if(recipe.getVegetarian()){
            veg.setImageResource(R.drawable.vegetarian_filter);
        }
        if(recipe.getVegan()){
            vegan.setImageResource(R.drawable.vegan_filter);
        }
        if(recipe.getLactose()){
            lactose.setImageResource(R.drawable.lactosefree_filter);
        }
        if(recipe.getNuts()){
            nuts.setImageResource(R.drawable.nutfree_filter);
        }
        if(recipe.getGluten()){
            gluten.setImageResource(R.drawable.glutenfree_filter);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onBitmapReady(String id, String filePath) {
                icon.setImageDrawable(ImageDownloaderService.fetchBitmapDrawable(filePath));
            }
        };

        if(icon.getDrawable() == null){ // if there is no icon, fetch
            Intent downloadreq = new Intent(this, ImageDownloaderService.class);
            downloadreq.setAction(ImageDownloaderService.GET_BITMAP_READY);
            downloadreq.putExtra(Recipe.ID, id);
            startService(downloadreq);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        imageDownloaderListener.unRegister();
    }

}
