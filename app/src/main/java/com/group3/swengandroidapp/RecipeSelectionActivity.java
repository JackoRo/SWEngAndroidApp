package com.group3.swengandroidapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        recipeName.setText(recipe.getTitle());
        Glide.with(this)
                .load(recipe.getThumbnail())
                .into(thumbnail);
        description.setText(recipe.getDescription());

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
}
