package com.group3.swengandroidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

public class RecipeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);

        final Button button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent newIntent = new Intent(getApplicationContext(), PresentationActivity.class);
                startActivity(newIntent);
            }
        });

        //Recipe recipe = RemoteFileManager.getInstance().getRecipe("0000");

        //ImageView thumbnail = findViewById(R.id.imageView);
        //TextView recipeName = findViewById(R.id.recipeName);

        //recipeName.setText(recipe.getTitle());
       // Glide.with(this)
                //.load(recipe.getThumbnail())
               // .into(thumbnail);
    }
}
