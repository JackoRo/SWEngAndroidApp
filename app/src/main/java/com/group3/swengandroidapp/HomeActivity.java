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
import android.view.View;

import com.group3.swengandroidapp.XMLRenderer.Recipe;
import com.group3.swengandroidapp.XMLRenderer.RemoteFileManager;

import java.util.HashMap;

/**
 * The home screen of the app.
 * <p>
 *     Displays a history of recently viewed recipes (as contained within HistoryHandler) and a
 *     "recommended" list of recipes.
 * </p>
 * Created by Kevin on 12/03/2018 and edited by mb1510 (Team Leader).
 */

//public class HomeActivity extends MainActivity  {

public class HomeActivity extends MainActivity implements RecipeRecyclerViewAdaper.ItemClickListener{

    private RecipeRecyclerViewAdaper recipeAdapter;
    private RecipeRecyclerViewAdaper historyAdapter;
    private ImageDownloaderListener imageDownloaderListener;
    HashMap<String, Recipe.Icon> icons = new HashMap<>();

    /**
     * Method called when a recipe is clicked from the home screen either in the history or main section
     */
    @Override
    public void onItemClick(View view, int position){
        Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". UPDATED_RECIPE_ID: "+ recipeAdapter.getItem(position).getId());
        Intent intent;
        intent = new Intent();
        intent.setClass(this,RecipeSelectionActivity.class);                 // Set new activity destination
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
        intent.putExtra(PythonClient.ID, recipeAdapter.getItem(position).getId());
        startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateDrawer();

        //Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recipeAdapter = new RecipeRecyclerViewAdaper(this){
            @Override
            public void notifyActivity(String id){
                historyAdapter.notifyIconChanged(id);
            }
        };
        recipeAdapter.setClickListener(new RecipeRecyclerViewAdaper.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". UPDATED_RECIPE_ID: "+ recipeAdapter.getItem(position).getId());
                Intent intent;
                intent = new Intent();
                intent.setClass(getApplicationContext(),RecipeSelectionActivity.class);                 // Set new activity destination
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
                intent.putExtra(PythonClient.ID, recipeAdapter.getItem(position).getId());
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
            }
        });
        recyclerView.setAdapter(recipeAdapter);


        // Setup History
        RecyclerView historyView = (RecyclerView)findViewById(R.id.home_history_bar);
        historyAdapter = new RecipeRecyclerViewAdaper(this){
            @Override
            public void notifyActivity(String id){
                recipeAdapter.notifyIconChanged(id);
            }
        };
        historyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        historyAdapter.setClickListener(new RecipeRecyclerViewAdaper.ItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Log.d("HomeActivity","Clicked on recipe " + position + "!: " + recipeAdapter.getItem(position).getTitle() + ". UPDATED_RECIPE_ID: "+ recipeAdapter.getItem(position).getId());
                Intent intent;
                intent = new Intent();
                intent.setClass(getApplicationContext(),RecipeSelectionActivity.class);                 // Set new activity destination
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Delete previous activities
                intent.putExtra(PythonClient.ID, historyAdapter.getItem(position).getId());
                startActivityForResult(intent, IntentConstants.INTENT_REQUEST_CODE);            // switch activities
            }
        });
        historyView.setAdapter(historyAdapter);

    }

    @Override
    public void onStart(){
        super.onStart();

        Log.d("TEST","Home start - Start");
        setTitle("Home");

        // Draw / Add icons to relevant recyclerviews

        // Get all recipes
        String recipeOfTheDay = RemoteFileManager.getInstance().getRecipeOfTheDay();
        String[] histories = HistoryHandler.getInstance().getHistory();
        String[] suggested = RemoteFileManager.getInstance().getSuggestedRecipes();

        // Process recipe of the day
        Recipe rotd = RemoteFileManager.getInstance().getRecipe(recipeOfTheDay).clone();
        rotd.setTitle("Recipe of the day:\n"+rotd.getTitle());
        icons.put(recipeOfTheDay, Recipe.produceDescriptor(this, rotd));

        // Add recipe of the day in the history view
        historyAdapter.addIcon(icons.get(recipeOfTheDay));

        // Process the rest of the history view (if there are histories to load)
        if(histories != null){
            for(String id : histories){
                if(!icons.containsKey(id)){
                    icons.put(id, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(id)));
                }
                historyAdapter.addIcon(icons.get(id));
            }
        }

        // Process the suggested view
        for(String id : suggested){
            if(!icons.containsKey(id)){
                icons.put(id, Recipe.produceDescriptor(this, RemoteFileManager.getInstance().getRecipe(id)));
            }
            recipeAdapter.addIcon(icons.get(id));
        }

        // Notify the adapters to update themselves.
        historyAdapter.notifyDataSetChanged();
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();

        // Startup imageDownloaderListener
        imageDownloaderListener = new ImageDownloaderListener(this) {
            @Override
            public void onReceive(Context context, Intent intent) {
                String id = intent.getStringExtra(Recipe.ID);
                if(id != null){
                    String action = intent.getAction();
                    if(action != null){
                        switch(action){
                            case ImageDownloaderService.BITMAP_READY:
                                String absolutePath = intent.getStringExtra(ImageDownloaderService.JPG_FILE_PATH);
                                Log.d("ImageDownloaderListener", "Received file path: " + absolutePath);
                                if(absolutePath != null){
                                    icons.get(id).setDrawable(ImageDownloaderService.fetchBitmapDrawable(absolutePath));
                                    recipeAdapter.notifyIconChanged(id);
                                    historyAdapter.notifyIconChanged(id);
                                }
                                break;
                            case Recipe.Icon.ICON_CHANGED:
                                recipeAdapter.notifyIconChanged(id);
                                historyAdapter.notifyIconChanged(id);
                                break;
                        }
                    }
                }

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
        imageDownloaderListener.destroy();
    }

    //TODO: Is this really necessary?
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
        Log.d("TEST", "Destroying...");
    }

}
