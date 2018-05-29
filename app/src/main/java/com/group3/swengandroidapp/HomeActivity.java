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

import java.util.HashMap;

/**
 * The home screen of the app.
 * <p>
 *     Displays a history of recently viewed recipes (as contained within {@link HistoryHandler}) and a
 *     "recommended" list of recipes (Produced within {@link RemoteFileManager}).
 * </p>
 * Created by Kevin on 12/03/2018 and edited by mb1510 (Team Leader).
 */
public class HomeActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    private RecipeRecyclerViewAdaper suggestedAdapter;          // adapter to Suggested Recipes recyclerview
    private RecipeRecyclerViewAdaper historyAdapter;            // Adapter to History recyclerview
    private ImageDownloaderListener imageDownloaderListener;    // Listens for BITMAP_READY messages from ImageDownloaderService
    HashMap<String, Recipe.Icon> icons = new HashMap<>();       // Contains all icons that are to be deplyed on this page

    /**
     * Method called when a recipe is clicked from the home screen either in the history or main section.
     * Opens an instance of {@link RecipeSelectionActivity}
     */
    @Override
    public void onItemClick(String recipeId){
        AudioPlayer.touchSound();
        Log.d("HomeActivity","Clicked on recipe " + recipeId);
        Intent intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);                   // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                    // Delete previous activities
        intent.putExtra(PythonClient.ID, recipeId);       // Set recipe id
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);                // switch activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();

        setTitle("Home");

        // Setup Recommended Recipes view
        RecyclerView recyclerView = findViewById(R.id.home_suggested_view);                // Get suggested recipe view
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));    // Set as a 2-collumn grid
        recyclerView.setHasFixedSize(false);
        recyclerView.setVerticalScrollBarEnabled(true);
        suggestedAdapter = new RecipeRecyclerViewAdaper(this);                     // Initialise the adapter for the view
        suggestedAdapter.setClickListener(this);                                          // Set the click listener for the adapter
        recyclerView.setAdapter(suggestedAdapter);                                        // Assign adapter to the view

        // Setup History
        RecyclerView historyView = findViewById(R.id.home_history_view);
        historyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Set view layout
        historyAdapter = new RecipeRecyclerViewAdaper(this);
        historyAdapter.setClickListener(this);
        historyView.setAdapter(historyAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();

        // Get all needed recipe ids
        String recipeOfTheDay = RemoteFileManager.getInstance().getRecipeOfTheDay();
        String[] histories = HistoryHandler.getInstance().getHistory();
        String[] suggested;
        //int historySize = histories.length;
        if (histories == null){
            suggested = RemoteFileManager.getInstance().getSuggestedRecipes(0, histories);
            Log.d("history", "nothing in history, loading all recipes instead");
        }else{
            Log.d("history", "entered into else statement");

            int historySize = histories.length;
            Log.d("history", "number of recipes in history:" + historySize);
            suggested = RemoteFileManager.getInstance().getSuggestedRecipes(historySize, histories);
        }


        // Process recipe of the day
        Recipe rotd = RemoteFileManager.getInstance().getRecipe(recipeOfTheDay).clone(); // Copy the recipe
        rotd.setTitle("Recipe of the day:\n"+rotd.getTitle());
        icons.put(recipeOfTheDay, Recipe.produceDescriptor(this, rotd));
        // Add recipe of the day in the history view
        historyAdapter.addIcon(icons.get(recipeOfTheDay));

        // Process the rest of the history view (if there are histories to load)
        if(histories != null){                          // if there are histories to load
            for(String id : histories){                 // for each history recipe
                if(!icons.containsKey(id)){             // if corresponding icon doesn't already exist, create
                    icons.put(id, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(id)));
                }
                historyAdapter.addIcon(icons.get(id));  // Add icon to the adapter
            }
        }

        // Process the suggested view
        for(String id : suggested){
            if(!icons.containsKey(id)){
                icons.put(id, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(id)));
            }
            suggestedAdapter.addIcon(icons.get(id));
        }

        // Notify the adapters to update themselves.
        historyAdapter.notifyDataSetChanged();
        suggestedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();

        // Startup imageDownloaderListener
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onBitmapReady(String id, String absolutePath){
                icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                suggestedAdapter.notifyIconChanged(id);
                historyAdapter.notifyIconChanged(id);
            }

            @Override
            public void onIconChanged(String id){
                suggestedAdapter.notifyIconChanged(id);
                historyAdapter.notifyIconChanged(id);
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
