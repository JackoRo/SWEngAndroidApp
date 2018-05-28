package com.group3.swengandroidapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.group3.swengandroidapp.ShoppingList.Intent_Constants;
import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;
import com.group3.swengandroidapp.XMLRenderer.XmlRecipe;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * The home screen of the app.
 * <p>
 *     Displays a history of recently viewed recipes (as contained within {@link HistoryHandler}) and a
 *     "recommended" list of recipes (Produced within {@link RemoteFileManager}).
 * </p>
 * Created by Kevin on 12/03/2018 and edited by mb1510 (Team Leader).
 */
public class MyRecipesActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    private RecipeRecyclerViewAdaper myRecipesAdapter;          // adapter to Suggested Recipes recyclerview
    private ImageDownloaderListener imageDownloaderListener;    // Listens for BITMAP_READY messages from ImageDownloaderService
    HashMap<String, Recipe.Icon> icons = new HashMap<>();       // Contains all icons that are to be deplyed on this page

    private RemoteFileManager remoteFileManager = RemoteFileManager.getInstance();

    /**
     * Method called when a recipe is clicked from the home screen either in the history or main section.
     * Opens an instance of {@link RecipeSelectionActivity}
     */
    @Override
    public void onItemClick(String myRecipeId){
        Log.d("myRecipesActivity","Clicked on recipe " + myRecipeId);
        Intent intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);                   // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                    // Delete previous activities
        intent.putExtra(PythonClient.ID, myRecipeId);       // Set recipe id
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);                // switch activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        super.onCreateDrawer();

        setTitle("My recipes");

        // Setup Recommended Recipes view
        RecyclerView recyclerView = findViewById(R.id.my_recipes_view);                // Get suggested recipe view
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));    // Set as a 2-collumn grid
        myRecipesAdapter = new RecipeRecyclerViewAdaper(this);                     // Initialise the adapter for the view
        myRecipesAdapter.setClickListener(this);                                          // Set the click listener for the adapter
        recyclerView.setAdapter(myRecipesAdapter);                                        // Assign adapter to the view

    }

    @Override
    public void onStart(){
        super.onStart();

        // Get all needed recipe ids
        String[] my_recipes = RemoteFileManager.getInstance().getMyRecipes();

        // Process the my recipes view
        for(String id : my_recipes){
            if(!icons.containsKey(id)){
                icons.put(id, Recipe.produceDescriptor(this, remoteFileManager.getInstance().getMyRecipe(id)));
            }
            myRecipesAdapter.addIcon(icons.get(id));
        }

        //remoteFileManager.setRecipe("0001", new XmlRecipe(fetchRecipeFromHttpServer("0001")));

        /*
        try {
            remoteFileManager.setRecipe("0001", new XmlRecipe("myRecipes/0002"));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        */

        // Notify the adapters to update themselves.
        myRecipesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();

        // Startup imageDownloaderListener
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onBitmapReady(String id, String absolutePath){
                icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                myRecipesAdapter.notifyIconChanged(id);
            }

            @Override
            public void onIconChanged(String id){
                myRecipesAdapter.notifyIconChanged(id);
            }
        };

        // Background load the icon thumbnails
        for(String id : icons.keySet()){
            requestBitmapFile(id);
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        // TODO: Unregistered in onPause, not re-registered in onResume?
        imageDownloaderListener.unRegister();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            if(message != null) Log.d("receiver", "Got message: " + message);

            if (intent.getStringExtra(PythonClient.ACTION).matches(PythonClient.FETCH_RECIPE)) {
                Intent newIntent = new Intent(context, RecipeSelectionActivity.class);
                startActivity(newIntent);
            }else {
                Log.d("ASDLKA", intent.getStringExtra(PythonClient.ACTION));
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

}
