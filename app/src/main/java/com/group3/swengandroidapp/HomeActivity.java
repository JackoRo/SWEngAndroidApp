package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.group3.swengandroidapp.XMLRenderer.*;
import com.group3.swengandroidapp.XMLRenderer.Recipe;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/03/2018.
 */

public class HomeActivity extends MainActivity {

    ArrayList<Button> favouriteButtons = new ArrayList<Button>();
    ArrayList<Recipe> favouritesRecipeBook = new ArrayList<Recipe>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("XML-event-name"));

        setTitle("Home");

        ImageButton imagebuttonExample = (ImageButton)findViewById(R.id.imageButton6);
        imagebuttonExample.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PythonClient.class);
                intent.putExtra(PythonClient.ACTION,PythonClient.FETCH_RECIPE);
                intent.putExtra(PythonClient.ID,"0000");
                startService(intent);
            }


        });
        /*Listeners for any arraylist of favourite buttons/imageviews/textviews etc, and creating
        * the favourites arraylist from these listeners.*/
        favouriteButtons.add((Button)findViewById(R.id.add_r1));
        favouriteButtons.add((Button)findViewById(R.id.add_r2));
        favouriteButtons.add((Button)findViewById(R.id.add_r3));
        favouriteButtons.add((Button)findViewById(R.id.add_r4));

        for(int i = 0; i<favouriteButtons.size(); i++){
            final int selectedRecipe = i;
            favouriteButtons.get(i).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    favouriteButtons.get(selectedRecipe);
                    String selectedRecipeString = Integer.toString(selectedRecipe);
                    com.group3.swengandroidapp.XMLRenderer.Recipe recipeSelected = RemoteFileManager.getInstance().getRecipe(selectedRecipeString);


                        //Only add the recipe to the favourites recipe book if it is not already there.
                        if (favouritesRecipeBook.contains(recipeSelected)) {
                            favouritesRecipeBook.remove(recipeSelected);
                            Toast.makeText(getBaseContext(), recipeSelected.getTitle() + "has been removed from your favourite recipe book.", Toast.LENGTH_LONG).show();
                        } else {
                            favouritesRecipeBook.add(recipeSelected);
                            Toast.makeText(getBaseContext(), recipeSelected.getTitle() + "has been added from your favourite recipe book.", Toast.LENGTH_LONG).show();
                        }



                }
            });
        }

        //Listener for button/textview (depending on what is chosen) to open the favourites page and pass the favourites arraylist.
        Button view_favourites = findViewById(R.id.view_favourites);

        view_favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent view_f = new Intent(HomeActivity.this, FavouriteList.class);

                view_f.putExtra("FavouriteRecipeExtra", favouritesRecipeBook);

                startActivity(view_f);

            }
        });










    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION) == PythonClient.FETCH_RECIPE) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                startActivity(newIntent);
            }
            else {
                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
