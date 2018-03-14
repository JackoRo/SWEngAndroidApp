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

    ArrayList<Button> buttonArrayList = new ArrayList<Button>();
    ArrayList<Recipe> favRecipeBook = new ArrayList<Recipe>();

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

        buttonArrayList.add((Button)findViewById(R.id.add_r1));
        buttonArrayList.add((Button)findViewById(R.id.add_r2));
        buttonArrayList.add((Button)findViewById(R.id.add_r3));
        buttonArrayList.add((Button)findViewById(R.id.add_r4));
        /*Button b_add_r1 = findViewById(R.id.add_r1);
        Button b_add_r2 = findViewById(R.id.add_r2);
        Button b_add_r3 = findViewById(R.id.add_r3);
        Button b_add_r4 = findViewById(R.id.add_r4);*/

        for(int i = 0; i<buttonArrayList.size(); i++){
            final int currentButton = i;
            buttonArrayList.get(i).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    buttonArrayList.get(currentButton);
                    //Toast.makeText(getApplicationContext(), "Recipe " + currentButton + " selected.", Toast.LENGTH_LONG).show();
                    String currentButtonString = Integer.toString(currentButton);
                    com.group3.swengandroidapp.XMLRenderer.Recipe recipeToAdd = RemoteFileManager.getInstance().getRecipe(currentButtonString);
                    favRecipeBook.add(recipeToAdd);
                    Toast.makeText(getApplicationContext(), recipeToAdd.getTitle() + " added.", Toast.LENGTH_LONG).show();

                }
            });
        }

        Button view_favourites = findViewById(R.id.view_favourites);

        view_favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent view_f = new Intent(HomeActivity.this, FavouriteList.class);

                view_f.putExtra("FavouriteRecipeExtra", favRecipeBook);

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
