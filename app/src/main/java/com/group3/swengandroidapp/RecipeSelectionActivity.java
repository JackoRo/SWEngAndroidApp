package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

public class RecipeSelectionActivity extends AppCompatActivity {

    Recipe recipe;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);

        Intent intent = getIntent();

        if (savedInstanceState != null) {
            id = savedInstanceState.getString("ID");
        } else {
            id = intent.getStringExtra(PythonClient.ID);
        }
        Log.d("RecipeSelectionActivity: ID", id);
        recipe = RemoteFileManager.getInstance().getRecipe(id);

        final Button button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent newIntent = new Intent(getApplicationContext(), PresentationActivity.class);
                newIntent.putExtra(PythonClient.ID, id);
                startActivity(newIntent);
            }
        });

        ImageView thumbnail = findViewById(R.id.imageView);
        TextView recipeName = findViewById(R.id.recipeName);
        TextView description = findViewById(R.id.description);
        TextView descriptionTitle = findViewById(R.id.descriptionTitle);
        TextView ingredientTitle = findViewById(R.id.ingredientTitle);


        String recipeNameText = recipe.getTitle();
        SpannableString recipeNameTextContent = new SpannableString(recipeNameText);
        recipeNameTextContent.setSpan(new UnderlineSpan(), 0, recipeNameText.length(), 0);
        recipeName.setText(recipeNameTextContent);

        String descriptionTitleText = "Description";
        SpannableString descriptionTitleTextContent = new SpannableString(descriptionTitleText);
        descriptionTitleTextContent .setSpan(new UnderlineSpan(), 0, descriptionTitleText.length(), 0);
        descriptionTitle.setText(descriptionTitleTextContent);

        Glide.with(this)
                .load(recipe.getThumbnail())
                .into(thumbnail);
        description.setText(recipe.getDescription());

        LinearLayout layout = findViewById(R.id.ingredientsLayout);

        String ingredientTitleText = "Ingredients";
        SpannableString ingredientTitleTextContent = new SpannableString(ingredientTitleText);
        ingredientTitleTextContent .setSpan(new UnderlineSpan(), 0, ingredientTitleText.length(), 0);
        ingredientTitle.setText(ingredientTitleTextContent);

        for (Ingredient i : recipe.getIngredients()) {

            TextView ingredientText = new TextView(getApplicationContext());
            ingredientText.setText(i.getQuantity() + " - " + i.getName());
            ingredientText.setTextColor(Color.parseColor("#000000"));
            layout.addView(ingredientText);

        }

    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("ID", this.id);

        // etc.
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        this.id = savedInstanceState.getString("ID");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(this).pauseRequestsRecursive();
    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Back Button Pressed", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
